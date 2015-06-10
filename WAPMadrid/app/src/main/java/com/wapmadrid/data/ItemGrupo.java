package com.wapmadrid.data;

public class ItemGrupo extends Item{
	
	protected String picture;
	protected String name;
	private String ruta;
	private String rutaId;

	public ItemGrupo(String picture, String name, String ruta, String id, String rutaId) {
		super();
		this.picture = picture;
		this.name = name;
		this.ruta = ruta;
		this.rutaId = rutaId;
		this.id = id;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getRutaId() {
		return rutaId;
	}

	public void setRutaId(String rutaId) {
		this.rutaId = rutaId;
	}
}
