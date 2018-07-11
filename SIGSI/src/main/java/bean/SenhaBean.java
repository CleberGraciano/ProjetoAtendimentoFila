package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.omnifaces.util.Messages;

import dao.SenhaDAO;
import model.Senha;
import model.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class SenhaBean implements Serializable {
	private List<Senha> senhas;
	private Senha senha;

	SenhaDAO senhaDAO;

	public List<Senha> getSenhas() {
		return senhas;
	}

	public void setSenhas(List<Senha> senhas) {
		this.senhas = senhas;
	}

	public Senha getSenha() {
		return senha;
	}

	public void setSenha(Senha senha) {
		this.senha = senha;
	}

	@PostConstruct
	public void listar() {
		try {
			senhaDAO = new SenhaDAO();
			senhas = senhaDAO.listar("sistema");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao listar os senhas");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			senha = new Senha();
			senha.setDataCadastro(new Date());
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao gerar um novo senha");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			senhaDAO.merge(senha);
			senha = new Senha();
			senhas = senhaDAO.listar("sistema");

			Messages.addGlobalInfo("Senha salva com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao salvar um novo senha");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.hasRole("A")) {
			try {
				senha = (Senha) evento.getComponent().getAttributes().get("senhaSelecionada");

				Subject subject = SecurityUtils.getSubject();
				Usuario usuario = (Usuario) subject.getPrincipal();
				HistoricoBean historicoBean = new HistoricoBean();
				historicoBean.salvar(usuario.getNome(),
						"Exclusão de Senha", "Usuário: " + senha.getUsuario() + "\nSenha: " + senha.getSenha()
								+ "\nSistema: " + senha.getSistema() + "\nDescrição: " + senha.getDescricao(), new Date());

				senhaDAO.excluir(senha);
				senhas = senhaDAO.listar("sistema");

				Messages.addGlobalInfo("Senha removida com sucesso");
			} catch (RuntimeException erro) {
				Messages.addGlobalError("Ocorreu um erro ao remover senha");
				erro.printStackTrace();
			}
		} else
			Messages.addGlobalError("Apenas o usuário Administrador pode fazer exclusão.");
	}

	public void editar(ActionEvent evento) {
		try {
			senha = (Senha) evento.getComponent().getAttributes().get("senhaSelecionada");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao selecionar uma senha");
			erro.printStackTrace();
		}
	}
}