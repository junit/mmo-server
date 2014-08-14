package com.game.map.message;


import com.game.message.struct.Bean;

import io.netty.buffer.ByteBuf;
import org.apache.log4j.Logger;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 角色地图展示信息
 */
public class RoleInfo extends Bean {

	private static Logger log = Logger.getLogger(RoleInfo.class);
	
	//角色id
	private long roleId;
	
	//角色名称
	private String name;
	
	//坐标
	private Position position;
	
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(ByteBuf buf){
	    try {
			//角色id
			writeLong(buf, this.roleId);
			//角色名称
			writeString(buf, this.name);
			//坐标
			writeBean(buf, this.position);
        } catch (Exception e) {
            log.error(e, e);
            return false;
        }
        return true;
	}
	
	/**
	 * 读取字节缓存
	 */
	public boolean read(ByteBuf buf){
        try {
			//角色id
			this.roleId = readLong(buf);
			//角色名称
			this.name = readString(buf);
			//坐标
			this.position = (Position)readBean(buf, Position.class);
        } catch (Exception e) {
            log.error(e, e);
            return false;
        }
        return true;
	}
	
	/**
	 * get 角色id
	 * @return 
	 */
	public long getRoleId(){
		return roleId;
	}
	
	/**
	 * set 角色id
	 */
	public void setRoleId(long roleId){
		this.roleId = roleId;
	}
	
	/**
	 * get 角色名称
	 * @return 
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * set 角色名称
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * get 坐标
	 * @return 
	 */
	public Position getPosition(){
		return position;
	}
	
	/**
	 * set 坐标
	 */
	public void setPosition(Position position){
		this.position = position;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//角色id
		buf.append("roleId:" + roleId +",");
		//角色名称
		if(this.name!=null) buf.append("name:" + name.toString() +",");
		//坐标
		if(this.position!=null) buf.append("position:" + position.toString() +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}