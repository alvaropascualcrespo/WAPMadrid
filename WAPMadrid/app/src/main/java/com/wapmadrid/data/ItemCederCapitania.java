package com.wapmadrid.data;

public class ItemCederCapitania extends Item{

	protected String nombre;
	protected String picture;
	protected boolean checked;
	
    public ItemCederCapitania(String nombre, String picture, boolean checked, String id) {
		super();
		this.nombre = nombre;
		this.picture = picture;
		this.checked = checked;
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public boolean isChecked()
    {
        return checked;
    }
 
    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }
	
}
