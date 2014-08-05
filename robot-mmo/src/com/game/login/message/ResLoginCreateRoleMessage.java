package com.game.login.message;

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
 * 创建角色消息
 */
public class ResLoginCreateRoleMessage extends Message{

	private static Logger log = Logger.getLogger(ResLoginCreateRoleMessage.class);
	
	//返回值
	private byte ret;
	
	//角色信息
	private List<RoleBrief> roles = new ArrayList<RoleBrief>();
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(ByteBuf buf){
	    try {
			//返回值
			writeByte(buf, this.ret);
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
			//返回值
			this.ret = readByte(buf);
			//角色信息
			int roles_length = readShort(buf);
			for (int i = 0; i < roles_length; i++) {
				roles.add((RoleBrief)readBean(buf, RoleBrief.class));
			}
        } catch (Exception e) {
            log.error(e, e);
            return false;
        }
        return true;
	}
	
	/**
	 * get 返回值
	 * @return 
	 */
	public byte getRet(){
		return ret;
	}
	
	/**
	 * set 返回值
	 */
	public void setRet(byte ret){
		this.ret = ret;
	}
	
	/**
	 * get 角色信息
	 * @return 
	 */
	public List<RoleBrief> getRoles(){
		return roles;
	}
	
	/**
	 * set 角色信息
	 */
	public void setRoles(List<RoleBrief> roles){
		this.roles = roles;
	}
	
	
	@Override
	public int getId() {
		return 100201;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//返回值
		buf.append("ret:" + ret +",");
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