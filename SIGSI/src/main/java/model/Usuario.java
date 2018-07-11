package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class Usuario extends GenericDomain {
	@Column(nullable = false, length = 100)
	private String nome;
	
	@Column(nullable = false, length = 63)
	private String usuario;

	@Column(nullable = false)
	private String senha;
	
	@Column(nullable = false, length = 63)
	private String email;
	
	@Column(nullable = false)
	private Character tipo;

	@Column(nullable = false)
	private boolean ativo;
	
	@Transient
	private String senhaSemCriptografia;
	
	@Column(nullable = false)
	private String salt;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public Character getTipo() {
		return tipo;
	}

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	@Transient
	public String getTipoFormatado() {
		String tipoFormatado = null;
		
		if(tipo == 'A')
			tipoFormatado = "Administrador";
				
		else if(tipo == 'U')
			tipoFormatado = "Usuário";
		
		return tipoFormatado;
	}
	
	@Transient
	public String getAtivoFormatado() {
		String ativoFormatado = null;
		
		if(ativo)
			ativoFormatado = "Sim";
		
		else
			ativoFormatado = "Não";
		
		return ativoFormatado;
	}
	
	public String getSenhaSemCriptografia() {
		return senhaSemCriptografia;
	}
	
	public void setSenhaSemCriptografia(String senhaSemCriptografia) {
		this.senhaSemCriptografia = senhaSemCriptografia;
	}
	
	public String getSalt() {
		return salt;
	}
	
	public void setSalt(String salt) {
		this.salt = salt;
	}
}
