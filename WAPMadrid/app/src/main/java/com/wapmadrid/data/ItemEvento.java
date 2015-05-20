package com.wapmadrid.data;

public class ItemEvento extends Item{
	
	String centroMedico;
	String titulo;
	String texto;
	String fecha;
	String hora;
	
	public ItemEvento(String centroMedico, String titulo, String texto, String fecha, String hora, String id) {
		super();
		this.centroMedico = centroMedico;
		this.titulo = titulo;
		this.texto = texto;
		this.fecha = fecha;
		this.hora = hora;
		this.id = id;
	}

	public String getCentroMedico() {
		return centroMedico;
	}

	public void setCentroMedico(String centroMedico) {
		this.centroMedico = centroMedico;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}
	
}
