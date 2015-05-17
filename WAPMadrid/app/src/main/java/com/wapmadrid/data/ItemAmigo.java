package com.wapmadrid.data;

public class ItemAmigo extends Item{
	
	protected String picture;
	protected String name;
	
	public ItemAmigo(String picture, String name, long id) {
		super();
		this.picture = picture;
		this.name = name;
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
	
}