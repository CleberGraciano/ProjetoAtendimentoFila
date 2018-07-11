package util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import model.Usuario;

public class EmailUtil {
	public static final String HOST = "smtplw.com.br";
	public static final Integer PORTA = 587;
	public static final String USUARIO = "micromapweb";
	public static final String SENHA = "01Nx43Java*@";
	public static final String FROM = "educamap@micromap.com.br";

	private static Email buildEmail() {
		Email email = new SimpleEmail();

		email.setHostName(HOST);
		email.setSmtpPort(PORTA);
		email.setAuthenticator(new DefaultAuthenticator(USUARIO, SENHA));
		email.setSSLOnConnect(true);

		try {
			email.setFrom(FROM);
		} catch (EmailException exception) {
			throw new RuntimeException(exception.getMessage(), exception);
		}

		return email;
	}

	public static void alterarSenha(Usuario usuario) {
		Email email = buildEmail();

		try {
			email.setSubject("SIGSI - Alteração de Senha");

			StringBuilder mensagem = new StringBuilder();
			mensagem.append("\nUsuário: " + usuario.getUsuario());
			mensagem.append("\nSenha: " + usuario.getSenha());
			email.setMsg(mensagem.toString());

			email.addTo(usuario.getEmail());

			email.send();
		} catch (EmailException exception) {
			throw new RuntimeException(exception.getMessage(), exception);
		}
	}
}
