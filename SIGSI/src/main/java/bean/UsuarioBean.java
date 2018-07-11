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

import dao.UsuarioDAO;
import model.Usuario;
import security.Criptografia;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {
	private List<Usuario> usuarios;
	private Usuario usuario;

	private boolean ativo;

	private Usuario usuarioLogado;

	UsuarioDAO usuarioDAO;

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}
	
	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	@PostConstruct
	public void listar() {
		try {
			Subject subject = SecurityUtils.getSubject();
			usuarioLogado = (Usuario) subject.getPrincipal();
			
			usuarioDAO = new UsuarioDAO();
			usuarios = usuarioDAO.listar("nome");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao listar usuários");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			usuario = new Usuario();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao gerar novo usuário");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			this.setUsuario(Criptografia.gerarSenhaCrip(usuario, usuario.getSenha()));
			usuario.setAtivo(isAtivo());

			usuarioDAO.merge(usuario);
			ativo = false;
			usuario = new Usuario();
			usuarios = usuarioDAO.listar("nome");

			Messages.addGlobalInfo("Usuário salvo com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao salvar novo usuário");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");

			Subject subject = SecurityUtils.getSubject();
			Usuario usuarioLogado = (Usuario) subject.getPrincipal();
			HistoricoBean historicoBean = new HistoricoBean();
			historicoBean.salvar(usuarioLogado.getNome(), "Exclusão de Usuário",
					"Usuário: " + usuario.getUsuario() + "\nSenha: " + usuario.getSenha() + "\nNome: "
							+ usuario.getNome() + "\nTipo: " + usuario.getTipoFormatado() + "\nAtivo: "
							+ usuario.getAtivoFormatado(), new Date());

			usuarioDAO.excluir(usuario);
			usuarios = usuarioDAO.listar("nome");

			Messages.addGlobalInfo("Usuário removido com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao remover usuário");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");
			setAtivo(usuario.isAtivo());
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao selecionar um usuário");
			erro.printStackTrace();
		}
	}
}