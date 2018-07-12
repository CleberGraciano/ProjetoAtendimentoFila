package dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import util.HibernateUtil;

import model.Servidor;

public class ServidorDAO extends GenericDAO<Servidor> {
	@SuppressWarnings("unchecked")
	@Override
	public List<Servidor> listar() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Servidor.class);
			consulta.createAlias("cidade", "c");
			consulta.addOrder(Order.asc("c.nome"));
			List<Servidor> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}
