package bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import dao.CidadeDao;
import dao.EstadoDao;
import domain.Cidade;
import domain.Estado;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CidadeBean implements Serializable {

	private List<Cidade> cidades;
	private List<Estado> estados;

	private Cidade cidade;

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public void novo() {

		try {

			cidade = new Cidade();
			EstadoDao estadoDao = new EstadoDao();
			estados = estadoDao.listar("nome");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar uma nova cidade");

			erro.printStackTrace();
		}

	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	@PostConstruct
	public void listar() {
		try {

			CidadeDao cidadeDao = new CidadeDao();
			cidades = cidadeDao.listar("nome");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as Cidades");
			erro.printStackTrace();
		}
	}

	public void salvar() {

		try {
			
			CidadeDao cidadeDao = new CidadeDao();
			cidadeDao.merge(cidade);
			
			
			cidade = new Cidade();
			
			EstadoDao estadoDao = new EstadoDao();
			
			estados = estadoDao.listar();
			
			cidades = cidadeDao.listar();
			
			
			Messages.addGlobalInfo("Cidade Salva com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar Slvar as Cidades");
			erro.printStackTrace();
		}

	}
	
	
	public void editar(ActionEvent evento) {
		
		try {

			cidade = (Cidade) evento.getComponent().getAttributes().get("cidadeSelecionado");
			
			EstadoDao estadoDao = new EstadoDao();
			estados = estadoDao.listar();
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar selecionar uma nova cidade");

			erro.printStackTrace();
		}

		
	}
	
	public void excluir(ActionEvent evento) {
		
		try {
			cidade = (Cidade) evento.getComponent().getAttributes().get("cidadeSelecionado");
			
			CidadeDao cidadeDao = new CidadeDao();
			cidadeDao.excluir(cidade);
			
			cidades = cidadeDao.listar();
			
			Messages.addGlobalInfo("Cidade removida com sucesso");
			}catch (RuntimeException erro) {
				Messages.addGlobalError("Ocorreu um erro ao tentar remover a Cidade");
				erro.printStackTrace();
			}
		
	}

}
