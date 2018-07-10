package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import dao.CidadeDao;
import dao.EstadoDao;
import dao.PessoaDao;
import domain.Cidade;
import domain.Estado;
import domain.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class PessoaBean implements Serializable{
	
	private Pessoa pessoa;
	
	private List<Pessoa> pessoas;
	
	private List<Cidade> cidades;
	
	private Estado estado;
	
	
	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	private List<Estado> estados;

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}
	
	
	
	
	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public void novo() {
		
		try {
			pessoa = new Pessoa();
			
			estado = new Estado();
						
			EstadoDao estadoDao = new EstadoDao();
			estados = estadoDao.listar("nome");
			
			cidades = new ArrayList<Cidade>();
			
		}catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar uma nova Pessoa");
		}
	}
	
	@PostConstruct
	public void listar() {
		
		try {
			
			PessoaDao pessoaDao = new PessoaDao();
			pessoas = pessoaDao.listar("nome");
			
			}catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar Pessoas");
		}
				
	}
	public void salvar() {
		
		try {
			
			PessoaDao pessoaDao = new PessoaDao();
			pessoaDao.merge(pessoa);	
			
			pessoas = pessoaDao.listar("nome");		
			
			novo();
			
			Messages.addGlobalInfo("Pessoa Cadastrada com sucesso");
			}catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao Salvar Pessoa");
		}
		
	}
	
	public void editar(ActionEvent evento) {
		
		try{
			pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionada");
			
			estado = pessoa.getCidade().getEstado();
			
			EstadoDao estadoDAO = new EstadoDao();
			estados = estadoDAO.listar("nome");
			
			//CidadeDao cidadeDAO = new CidadeDao();
			//cidades = cidadeDAO.buscarPorEstado(estado.getCodigo());
			
			popular();
		} catch(RuntimeException erro){
			Messages.addGlobalError("Ocorreu um erro ao tentar selecionar uma pessoa");
		}
		
	}
	
	public void excluir(ActionEvent evento) {
		
		try {

			pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionada");

			PessoaDao pessoaDao = new PessoaDao();
			pessoaDao.excluir(pessoa);

			pessoas = pessoaDao.listar("nome");	
			
			novo();

			Messages.addGlobalInfo("Pessoa removida com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar remover a Pessoa");
			erro.printStackTrace();
		}
		
	}
	
	public void popular() {
		
		try {
		
		if(estado != null) {
			CidadeDao cidadeDao = new CidadeDao();
			cidades = cidadeDao.buscarPorEstado(estado.getCodigo());
			
			
		}else {
			cidades = new ArrayList<Cidade>();
		}
		
		}catch (RuntimeException erro) {
			
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar as cidades");
		}
	}
	

}
