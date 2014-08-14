package com.game.map.message;

import java.util.List;
import java.util.ArrayList;
import com.game.message.struct.Message;
import io.netty.buffer.ByteBuf;
import org.apache.log4j.Logger;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 附近所有信息消息
 */
public class ResMapRoundAllMessage extends Message{

	private static Logger log = Logger.getLogger(ResMapRoundAllMessage.class);
	
	//角色信息
	private List<RoleInfo> roles = new ArrayList<>();
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(ByteBuf buf){
	    try {
			//角色信息
			writeShort(buf, (short)roles.size());
			for (int i = 0; i < roles.size(); i++) {
				writeBean(buf, roles.get(i));
			}
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
			int roles_length = readShort(buf);
			for (int i = 0; i < roles_length; i++) {
				roles.add((RoleInfo)readBean(buf, RoleInfo.class));
			}
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
	public List<RoleInfo> getRoles(){
		return roles;
	}
	
	/**
	 * set 角色信息
	 */
	public void setRoles(List<RoleInfo> roles){
		this.roles = roles;
	}
	
	
	@Override
	public int getId() {
		return 103200;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//角色信息
		buf.append("roles:{");
		for (int i = 0; i < roles.size(); i++) {
			buf.append(roles.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}