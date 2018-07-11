package bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import dao.PessoaDao;
import dao.UsuarioDao;
import domain.Pessoa;
import domain.Usuario;


@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {
	
	private Usuario usuario;	
	
	private List<Usuario> usuarios;
	
	private List<Pessoa> pessoas;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	
	
	public void novo() {
		
		try {
		
		usuario = new Usuario();
		
		PessoaDao pessoaDao = new PessoaDao();
		pessoas = pessoaDao.listar("nome");
		
		}catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao criar um novo Usuario");
		}
		
		
	}
	
	public void salvar() {
		
		UsuarioDao usuarioDao = new UsuarioDao();
		usuarioDao.merge(usuario);
		
		usuario = new Usuario();
		
		usuarios = usuarioDao.listar("tipo");
		
		PessoaDao pessoaDao = new PessoaDao();
		pessoas = pessoaDao.listar("nome");
		
		try {
			Messages.addGlobalInfo("Usuário salvo com sucesso");
		}catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao salvar Usuário");
			erro.printStackTrace();
		}
		
	}
	
	@PostConstruct
	public void listar() {
		
		try {
			
			
			
		UsuarioDao usuarioDao = new UsuarioDao();
		
		
		
		usuarios = usuarioDao.listar("tipo");
		
		
		}catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar Listar os usuarios");
			erro.printStackTrace();
		}
		
	}
	
	public void editar(ActionEvent evento) {
		
		try {

			usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");
			
			PessoaDao pessoaDao = new PessoaDao();
			pessoas = pessoaDao.listar("nome");
			
			
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar selecionar um usuario");

			erro.printStackTrace();
		}
		
		
		
	}
	
	public void excluir(ActionEvent evento) {
		
try {
			
			usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");
			
			UsuarioDao usuarioDao =  new UsuarioDao();
			usuarioDao.excluir(usuario);
			
		
			
			usuarios = usuarioDao.listar("tipo");
			
		
			
			Messages.addGlobalInfo("Usuario removido com sucesso");
			}catch (RuntimeException erro) {
				Messages.addGlobalError("Ocorreu um erro ao tentar remover um Usuario");
				erro.printStackTrace();
			}
		
	}

}
