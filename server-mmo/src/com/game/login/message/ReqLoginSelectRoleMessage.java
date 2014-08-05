package com.game.login.message;

import com.game.message.struct.Message;
import io.netty.buffer.ByteBuf;
import org.apache.log4j.Logger;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 选择角色进入游戏消息
 */
public class ReqLoginSelectRoleMessage extends Message{

	private static Logger log = Logger.getLogger(ReqLoginSelectRoleMessage.class);
	
	//角色id
	private long roleId;
	
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(ByteBuf buf){
	    try {
			//角色id
			writeLong(buf, this.roleId);
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
	
	
	@Override
	public int getId() {
		return 100101;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//角色id
		buf.append("roleId:" + roleId +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}