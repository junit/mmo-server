package com.db.config.bean;

public class MapBean {
	private int maxLine;
	private int defaultLine;
	private String name;
	private int width;
	private int id;
	private int height;
	
	public int getMaxLine(){
		return maxLine;
	}
	
	public void setMaxLine(int maxLine){
		this.maxLine = maxLine;
	}
	
	public int getDefaultLine(){
		return defaultLine;
	}
	
	public void setDefaultLine(int defaultLine){
		this.defaultLine = defaultLine;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getWidth(){
		return width;
	}
	
	public void setWidth(int width){
		this.width = width;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void setHeight(int height){
		this.height = height;
	}
	
}