package dao;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import domain.Usuario;
import util.HibernateUtil;

public class UsuarioDao extends GenericDao<Usuario> {
	
	public Usuario autenticar(String cpf, String senha) {
		
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		
	try {
		
		Criteria consulta = sessao.createCriteria(Usuario.class);
		consulta.createAlias("pessoa", "p");
	
		consulta.add(Restrictions.eq("p.cpf", cpf));
		
		SimpleHash hash = new SimpleHash("md5", senha);
		
		consulta.add(Restrictions.eq("senha", hash.toHex()));
		
		Usuario resultado = (Usuario) consulta.uniqueResult();
		
		return resultado;
		
	
	} catch (RuntimeException erro) {

		throw erro;

	} finally {
		sessao.close();
	}

		
	}

}
