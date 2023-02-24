package br.com.ericksilverio.testeturing.models;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Transferencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String idEmissor;
	
	@Column(nullable = false)
	private String idReceptor;
	
	@Enumerated(EnumType.STRING)
	private TipoTransferencia tipo;

	private Double valor;

	@Column(nullable = false)
	private String data;
	
	public Transferencia() { }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdEmissor() {
		return idEmissor;
	}

	public void setIdEmissor(String idEmissor) {
		this.idEmissor = idEmissor;
	}

	public String getIdReceptor() {
		return idReceptor;
	}

	public void setIdReceptor(String idReceptor) {
		this.idReceptor = idReceptor;
	}

	public TipoTransferencia getTipo() {
		return tipo;
	}

	public void setTipo(TipoTransferencia tipo) {
		this.tipo = tipo;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
}
