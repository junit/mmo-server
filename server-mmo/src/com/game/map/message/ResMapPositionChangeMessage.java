package com.game.map.message;

import com.game.message.struct.Message;
import io.netty.buffer.ByteBuf;
import org.apache.log4j.Logger;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 坐标点改变消息
 */
public class ResMapPositionChangeMessage extends Message{

	private static Logger log = Logger.getLogger(ResMapPositionChangeMessage.class);
	
	//角色id
	private long roleId;
	//坐标点
	private Position position;
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(ByteBuf buf){
	    try {
			//角色id
			writeLong(buf, this.roleId);
			//坐标点
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
			//坐标点
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
	 * get 坐标点
	 * @return 
	 */
	public Position getPosition(){
		return position;
	}
	
	/**
	 * set 坐标点
	 */
	public void setPosition(Position position){
		this.position = position;
	}
	
	
	@Override
	public int getId() {
		return 103203;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//角色id
		buf.append("roleId:" + roleId +",");
		//坐标点
		if(this.position!=null) buf.append("position:" + position.toString() +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}