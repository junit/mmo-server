package com.game.map.message;

import com.game.message.struct.Message;
import io.netty.buffer.ByteBuf;
import org.apache.log4j.Logger;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 地图单个角色信息消息
 */
public class ResMapRoleMessage extends Message{

	private static Logger log = Logger.getLogger(ResMapRoleMessage.class);
	
	//角色信息
	private RoleInfo role;
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(ByteBuf buf){
	    try {
			//角色信息
			writeBean(buf, this.role);
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
			//角色信息
			this.role = (RoleInfo)readBean(buf, RoleInfo.class);
        } catch (Exception e) {
            log.error(e, e);
            return false;
        }
        return true;
	}
	
	/**
	 * get 角色信息
	 * @return 
	 */
	public RoleInfo getRole(){
		return role;
	}
	
	/**
	 * set 角色信息
	 */
	public void setRole(RoleInfo role){
		this.role = role;
	}
	
	
	@Override
	public int getId() {
		return 103201;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//角色信息
		if(this.role!=null) buf.append("role:" + role.toString() +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}