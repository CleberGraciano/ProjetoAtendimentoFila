package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import dao.HistoricoDAO;
import model.Historico;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class HistoricoBean implements Serializable {
	private List<Historico> listaHistorico;
	private Historico historico;

	HistoricoDAO historicoDAO;

	public List<Historico> getListaHistorico() {
		return listaHistorico;
	}

	public void setListaHistorico(List<Historico> listaHistorico) {
		this.listaHistorico = listaHistorico;
	}

	public Historico getHistorico() {
		return historico;
	}

	public void setHistorico(Historico historico) {
		this.historico = historico;
	}

	@PostConstruct
	public void listar() {
		try {
			historicoDAO = new HistoricoDAO();
			listaHistorico = historicoDAO.listar("atividade");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao listar histórico");
			erro.printStackTrace();
		}
	}

	public void carregar(ActionEvent evento) {
		try {
			historico = (Historico) evento.getComponent().getAttributes().get("historicoSelecionado");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao selecionar histórico");
			erro.printStackTrace();
		}
	}
	
	public void salvar(String nomeUsuario, String atividade, String conteudo, Date dataAtividade) {
		historicoDAO = new HistoricoDAO();
		historico = new Historico();
		historico.setNomeUsuario(nomeUsuario);
		historico.setAtividade(atividade);
		historico.setConteudo(conteudo);
		historico.setDataAtividade(dataAtividade);
		historicoDAO.salvar(historico);
		historico = new Historico();
	}
}