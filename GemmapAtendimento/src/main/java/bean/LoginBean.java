package bean;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import model.Usuario;

@ManagedBean
@ViewScoped
public class LoginBean {
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@PostConstruct
	public void iniciar() {
		this.usuario = new Usuario();
	}

	public void entrar() {
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(usuario.getUsuario(), usuario.getSenha(),
					Boolean.FALSE);

			Subject usuarioAtual = SecurityUtils.getSubject();

			usuarioAtual.login(token);

			Faces.redirect("./senhas.xhtml");

		} catch (

		AuthenticationException authenticationException) {
			if (authenticationException instanceof IncorrectCredentialsException) {
				Messages.addGlobalError("Usuário e/ou senha inválidos!");
			} else if (authenticationException instanceof UnknownAccountException) {
				Messages.addGlobalError(
						"Este usuário não existe! Por favor faça o cadastro ou tente " + "novamente mais tarde!");
			} else if (authenticationException instanceof LockedAccountException) {
				try {
					Faces.redirect("./paginas/public/info/acesso_bloqueado.xhtml");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (authenticationException instanceof AuthenticationException) {
				Messages.addGlobalError("Erro ao logar! " + "Por favor tente novamente mais tarde");
				authenticationException.printStackTrace();
			} else {
				Messages.addGlobalError(
						"Ocorreu um erro ao tentar realizar o login. " + "Por favor, tente novamente mais tarde.");
			}
		} catch (Exception ex) {
			Messages.addGlobalError("Erro ao logar!");
		}
	}

	public void sair() {
		try {
			Subject subject = SecurityUtils.getSubject();
			subject.logout();

			Faces.invalidateSession();
			Faces.redirect("./inicial.xhtml");
		} catch (IOException exception) {
			throw new RuntimeException(exception.getMessage(), exception);
		}
	}

	public boolean temPermissoes(List<String> permissoes) {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser == null) {
			return false;
		} else {
			for (String permissao : permissoes) {
				if (currentUser.hasRole(permissao)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean desativado(List<String> permissoes) {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser == null) {
			return true;
		} else {
			for (String permissao : permissoes) {
				if (currentUser.hasRole(permissao)) {
					return false;
				}
			}
		}
		return true;
	}
}
