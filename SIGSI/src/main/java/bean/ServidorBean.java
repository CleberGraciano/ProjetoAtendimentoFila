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

import dao.CidadeDAO;
import dao.HistoricoDAO;
import dao.ServidorDAO;
import model.Cidade;
import model.Historico;
import model.Servidor;
import model.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ServidorBean implements Serializable {
	private List<Servidor> servidores;
	private Servidor servidor;
	private Historico historico;

	private List<Cidade> cidades;

	ServidorDAO servidorDAO;
	CidadeDAO cidadeDAO;
	HistoricoDAO historicoDAO;
	
	private Long codigo;

	public List<Servidor> getServidores() {
		return servidores;
	}

	public void setServidores(List<Servidor> servidores) {
		this.servidores = servidores;
	}

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public Historico getHistorico() {
		return historico;
	}

	public void setHistorico(Historico historico) {
		this.historico = historico;
	}
	
	public Long getCodigo() {
		return codigo;
	}
	
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@PostConstruct
	public void listar() {
		try {
			historicoDAO = new HistoricoDAO();
			servidorDAO = new ServidorDAO();
			cidadeDAO = new CidadeDAO();
			servidores = servidorDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao listar os servidores");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			servidor = new Servidor();
			cidades = cidadeDAO.listar("nome");
			servidor.setDataInclusao(new Date());
			servidor.setStatus("Ativo");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao gerar um novo servidor");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			servidorDAO.merge(servidor);
			novo();
			servidores = servidorDAO.listar();

			Messages.addGlobalInfo("Servidor salvo com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao salvar um novo servidor");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			servidor = (Servidor) evento.getComponent().getAttributes().get("servidorSelecionado");

			Subject subject = SecurityUtils.getSubject();
			Usuario usuario = (Usuario) subject.getPrincipal();
			HistoricoBean historicoBean = new HistoricoBean();
			historicoBean.salvar(usuario.getNome(), "Exclusão do Servidor", "Host: " + servidor.getHost() + "\nUsuário: " + servidor.getUsuario() + "\nSenha: "
					+ servidor.getSenha() + "\nStatus: " + servidor.getStatus() + "\nResponsável: " + servidor.getResponsavel(), new Date());

			servidorDAO.excluir(servidor);
			servidores = servidorDAO.listar();

			Messages.addGlobalInfo("Servidor removido com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao remover servidor");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			servidor = (Servidor) evento.getComponent().getAttributes().get("servidorSelecionado");
			cidades = cidadeDAO.listar("nome");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao selecionar uma servidor");
			erro.printStackTrace();
		}
	}

	public void desativarServidor(ActionEvent evento) {
		servidor = (Servidor) evento.getComponent().getAttributes().get("servidorSelecionado");

		if (servidor.getStatus().equals("Ativo")) {
			servidor.setStatus("Inativo");
			servidor.setDataInativo(new Date());
			servidorDAO.merge(servidor);
			
			Subject subject = SecurityUtils.getSubject();
			Usuario usuario = (Usuario) subject.getPrincipal();
			HistoricoBean historicoBean = new HistoricoBean();
			historicoBean.salvar(usuario.getNome(), "Desativação do Servidor", "Host: " + servidor.getHost() + "\nUsuário: " + servidor.getUsuario() + "\nSenha: "
					+ servidor.getSenha() + "\nStatus: " + servidor.getStatus() + "\nResponsável: " + servidor.getResponsavel(), new Date());

			Messages.addGlobalInfo("O servidor agora está Inativo");
		} else
			Messages.addGlobalError("O servidor já está inativo");
	}

	public void ativarServidor(ActionEvent evento) {
		servidor = (Servidor) evento.getComponent().getAttributes().get("servidorSelecionado");

		if (servidor.getStatus().equals("Inativo")) {
			servidor.setStatus("Ativo");
			servidor.setDataInativo(new Date());
			servidorDAO.merge(servidor);
			
			Subject subject = SecurityUtils.getSubject();
			Usuario usuario = (Usuario) subject.getPrincipal();
			HistoricoBean historicoBean = new HistoricoBean();
			historicoBean.salvar(usuario.getNome(), "Ativação do Servidor", "Host: " + servidor.getHost() + "\nUsuário: " + servidor.getUsuario() + "\nSenha: "
					+ servidor.getSenha() + "\nStatus: " + servidor.getStatus() + "\nResponsável: " + servidor.getResponsavel(), new Date());

			Messages.addGlobalInfo("O servidor agora está Ativo");
		} else
			Messages.addGlobalError("O servidor já está Ativo");
	}
	
	public void buscar() {
		servidor = servidorDAO.buscar(codigo);
		cidades = cidadeDAO.listar();
	}
}