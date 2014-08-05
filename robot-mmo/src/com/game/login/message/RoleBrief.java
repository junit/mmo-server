package com.game.login.message;


import com.game.message.struct.Bean;

import io.netty.buffer.ByteBuf;
import org.apache.log4j.Logger;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 角色简要信息
 */
public class RoleBrief extends Bean {

	private static Logger log = Logger.getLogger(RoleBrief.class);
	
	//角色id
	private long roleId;
	
	//角色名称
	private String name;
	
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(ByteBuf buf){
	    try {
			//角色id
			writeLong(buf, this.roleId);
			//角色名称
			writeString(buf, this.name);
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
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//角色id
		buf.append("roleId:" + roleId +",");
		//角色名称
		if(this.name!=null) buf.append("name:" + name.toString() +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}