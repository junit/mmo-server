package com.game.login.message;

import com.game.message.struct.Message;
import io.netty.buffer.ByteBuf;
import org.apache.log4j.Logger;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 创建角色消息
 */
public class ReqLoginCreateRoleMessage extends Message{

	private static Logger log = Logger.getLogger(ReqLoginCreateRoleMessage.class);
	
	//角色名称
	private String name;
	
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(ByteBuf buf){
	    try {
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
			//角色名称
			this.name = readString(buf);
        } catch (Exception e) {
            log.error(e, e);
            return false;
        }
        return true;
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
	public int getId() {
		return 100102;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//角色名称
		if(this.name!=null) buf.append("name:" + name.toString() +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}