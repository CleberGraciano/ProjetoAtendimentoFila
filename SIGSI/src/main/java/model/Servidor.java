package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class Servidor extends GenericDomain {
	@Column(nullable = false, length = 60)
    private String host;

    @Column(nullable = false, length = 60)
    private String usuario;

    @Column(nullable = false, length = 60)
    private String senha;

    @Column(length = 60)
    private String responsavel;

    @Column(length = 20)
    private String telefone;

    @Column(nullable = false, length = 255)
    private String linkServidor;

    @Column(nullable = false, length = 255)
    private String linkGemmap;

    @Column(length = 255)
    private String observacoes;

    @ManyToOne
    @JoinColumn(nullable=false)
    private Cidade cidade;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable=false)
    private Date dataInclusao;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInativo;
    
    @Column(nullable=false, length = 7)
    private String status;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getLinkServidor() {
		return linkServidor;
	}

	public void setLinkServidor(String linkServidor) {
		this.linkServidor = linkServidor;
	}

	public String getLinkGemmap() {
		return linkGemmap;
	}

	public void setLinkGemmap(String linkGemmap) {
		this.linkGemmap = linkGemmap;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Date getDataInativo() {
		return dataInativo;
	}

	public void setDataInativo(Date dataInativo) {
		this.dataInativo = dataInativo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
