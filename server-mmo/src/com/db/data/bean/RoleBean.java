package com.db.data.bean;

public class RoleBean {
	private byte[] data;
	private long createTime;
	private String name;
	private long id;
	private long account;
	
	public byte[] getData(){
		return data;
	}
	
	public void setData(byte[] data){
		this.data = data;
	}
	
	public long getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(long createTime){
		this.createTime = createTime;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public long getAccount(){
		return account;
	}
	
	public void setAccount(long account){
		this.account = account;
	}
	
}