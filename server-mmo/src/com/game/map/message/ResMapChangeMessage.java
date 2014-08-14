package com.game.map.message;

import com.game.message.struct.Message;
import io.netty.buffer.ByteBuf;
import org.apache.log4j.Logger;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 切换地图消息
 */
public class ResMapChangeMessage extends Message{

	private static Logger log = Logger.getLogger(ResMapChangeMessage.class);
	
	//地图信息
	private MapInfo role;
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(ByteBuf buf){
	    try {
			//地图信息
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
			//地图信息
			this.role = (MapInfo)readBean(buf, MapInfo.class);
        } catch (Exception e) {
            log.error(e, e);
            return false;
        }
        return true;
	}
	
	/**
	 * get 地图信息
	 * @return 
	 */
	public MapInfo getRole(){
		return role;
	}
	
	/**
	 * set 地图信息
	 */
	public void setRole(MapInfo role){
		this.role = role;
	}
	
	
	@Override
	public int getId() {
		return 103202;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//地图信息
		if(this.role!=null) buf.append("role:" + role.toString() +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}