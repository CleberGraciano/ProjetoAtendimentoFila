package model;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class Cidade extends GenericDomain {
    @Column(nullable = false, unique = true)
    private Integer codigoMicromap;
    
	@Column(length = 60, nullable = false)
	private String nome;

    public Integer getCodigoMicromap() {
        return codigoMicromap;
    }

    public void setCodigoMicromap(Integer codigoMicromap) {
        this.codigoMicromap = codigoMicromap;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}