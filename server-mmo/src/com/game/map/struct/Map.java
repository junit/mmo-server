package com.game.map.struct;

public class Map {
	private int server;
	private int model;
	private int line;
	private long id;
	
	private Area[][] areas;
	
	public int getServer() {
		return server;
	}
	public void setServer(int server) {
		this.server = server;
	}
	public int getModel() {
		return model;
	}
	public void setModel(int model) {
		this.model = model;
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Area[][] getAreas() {
		return areas;
	}
	public void setAreas(Area[][] areas) {
		this.areas = areas;
	}
}
