package com.wapmadrid.data;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class ItemLayoutCapitan extends Item{

	protected int picture;
	protected String name;
	
	public ItemLayoutCapitan(int imageView, String name, long id) {
		super();
		this.picture = imageView;
		this.name = name;
		this.id = id;
	}

	public int getPicture() {
		return picture;
	}

	public void setPicture(int picture) {
		this.picture = picture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
