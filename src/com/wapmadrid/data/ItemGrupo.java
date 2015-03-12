package com.wapmadrid.data;

public class ItemGrupo extends Item{
	
	protected String picture;
	protected String name;
	private String ruta;
	
	public ItemGrupo(String picture, String name,String ruta, long id) {
		super();
		this.picture = picture;
		this.name = name;
		this.ruta = ruta;
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
	
}
