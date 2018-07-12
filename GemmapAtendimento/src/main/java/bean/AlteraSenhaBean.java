package bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.omnifaces.util.Messages;

import dao.UsuarioDAO;
import model.Usuario;
import security.Criptografia;
import util.EmailUtil;

@ManagedBean
@ViewScoped
public class AlteraSenhaBean {
	private Usuario usuario;
	private UsuarioDAO usuarioDAO;

	private String novaSenha;
	private String novaSenha2;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getNovaSenha2() {
		return novaSenha2;
	}

	public void setNovaSenha2(String novaSenha2) {
		this.novaSenha2 = novaSenha2;
	}

	@PostConstruct
	public void iniciar() {
		this.usuario = new Usuario();
		usuarioDAO = new UsuarioDAO();
	}

	public void recuperar() {
		this.usuarioDAO = new UsuarioDAO();

		Usuario usuario = this.usuarioDAO.esqueciSenhaBuscar(this.usuario.getUsuario(), this.usuario.getNome(),
				this.usuario.getEmail());

		if (usuario == null) {
			Messages.addGlobalError("Login e/ou Nome e/ou E-mail inválidos");
			return;
		}

		usuario.setSenha(RandomStringUtils.randomAlphanumeric(8));
		EmailUtil.alterarSenha(usuario);
		this.setUsuario(Criptografia.gerarSenhaCrip(usuario, usuario.getSenha()));
		usuarioDAO.merge(usuario);

		Messages.addGlobalInfo("A nova senha foi enviada para o email informado no cadastro do usuário");
	}

	public void alterarSenha() {
		if (novaSenha.equals(novaSenha2)) {
			Subject subject = SecurityUtils.getSubject();
			Usuario usuarioLogado = (Usuario) subject.getPrincipal();
			
			this.setUsuario(Criptografia.gerarSenhaCrip(usuarioLogado, novaSenha));
			usuarioDAO.merge(usuario);
			Messages.addGlobalInfo("Senha alterada com sucesso");
		} else
			Messages.addGlobalError("As senhas não coincidem");
	}
}
