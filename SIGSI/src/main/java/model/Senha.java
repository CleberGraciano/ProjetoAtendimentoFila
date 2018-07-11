package model;

import javax.persistence.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class Senha extends GenericDomain {
	@Column(nullable = false, length = 63)
    private String usuario;

    @Column(nullable = false, length = 63)
    private String senha;
    
	@Column(nullable = false, length = 63)
    private String sistema;

    @Column(length = 255)
    private String descricao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    private Date dataCadastro;
    
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

	public String getSistema() {
		return sistema;
	}

	public void setSistema(String sistema) {
		this.sistema = sistema;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
}
