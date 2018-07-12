package security;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;

import model.Usuario;

public class Criptografia {
	public static String gerarChaveRandomica() {
		String chave = null;
		try {
			javax.crypto.KeyGenerator keygen = javax.crypto.KeyGenerator.getInstance("AES");
			java.security.SecureRandom random = new java.security.SecureRandom();
			keygen.init(random);
			javax.crypto.SecretKey key = keygen.generateKey();
			byte[] buffer = key.getEncoded();
			byte[] chaveGerada = org.apache.commons.codec.binary.Base64.encodeBase64(buffer);
			chave = new String(chaveGerada, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chave;
	}
	
	public static Usuario gerarSenhaCrip(Usuario usuario, String textoPuro) {
		RandomNumberGenerator rng = new SecureRandomNumberGenerator();
		Object salt = rng.nextBytes();

		String hashedPasswordBase64 = new Sha256Hash(textoPuro, salt, 1024).toBase64();

		usuario.setSenha(hashedPasswordBase64);
		usuario.setSalt(salt.toString());

		return usuario;
	}
	
	public static String criptografaSenha(String senha, String salt) {
		String hashedPasswordBase64 = new Sha256Hash(senha, salt, 1024).toBase64();		
		return hashedPasswordBase64;
	}
}
