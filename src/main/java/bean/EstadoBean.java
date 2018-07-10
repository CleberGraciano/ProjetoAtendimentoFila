package bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import dao.EstadoDao;
import domain.Estado;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EstadoBean implements Serializable {

	private Estado estado;
	private List<Estado> estados;

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}
	
	
	public void novo() {
		estado = new Estado();
		
	}

	@PostConstruct
	public void listar() {
		try {

			EstadoDao estadoDao = new EstadoDao();
			estados = estadoDao.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os estados");
			erro.printStackTrace();
		}
	}



	public void salvar() {

		/*
		 * String texto = "Programação Web com java "; FacesMessage message = new
		 * FacesMessage(FacesMessage.SEVERITY_ERROR, texto, texto);
		 * 
		 * FacesContext contexto = FacesContext.getCurrentInstance();
		 * contexto.addMessage(null, message);
		 */

		try {

			EstadoDao estadoDao = new EstadoDao();
			estadoDao.merge(estado);

			novo();
			estados = estadoDao.listar();

			Messages.addGlobalInfo("Estado Salvo com sucesso");
		} catch (RuntimeException erro) {

			Messages.addGlobalError("Ocorreu um erro ao Tentar Salvar o estado");

			erro.printStackTrace();

		}
	}

	public void excluir(ActionEvent evento) {
		try {
		estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");
		
		EstadoDao estadoDao = new EstadoDao();
		estadoDao.excluir(estado);
		
		estados = estadoDao.listar();
		
		Messages.addGlobalInfo("Estado removido com sucesso");
		}catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar remover o estado");
			erro.printStackTrace();
		}
	}
		
		public void editar (ActionEvent evento) {
			estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");
			
	}
}
