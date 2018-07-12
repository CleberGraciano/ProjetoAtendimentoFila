package security;

import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;

import model.Usuario;


public class MySaltedAuthentificationInfo implements SaltedAuthenticationInfo {

	private static final long serialVersionUID = 7129027089995709415L;
	
	private final Usuario user;
	private final String password;
	private final String salt;

	public MySaltedAuthentificationInfo(Usuario user, String password, String salt) {
		super();
		this.user = user;
		this.password = password;
		this.salt = salt;
	}

	@Override
	public PrincipalCollection getPrincipals() {
		return new SimplePrincipalCollection(user, SIGSIRealm.class.getName());
	}

	@Override
	public Object getCredentials() {
		return password;
	}

	@Override
	public ByteSource getCredentialsSalt() {
		return new SimpleByteSource(Base64.decode(salt));
	}
}
