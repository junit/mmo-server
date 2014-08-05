package com.game.login.message;

import com.game.message.struct.Message;
import io.netty.buffer.ByteBuf;
import org.apache.log4j.Logger;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 登录消息
 */
public class ReqLoginMessage extends Message{

	private static Logger log = Logger.getLogger(ReqLoginMessage.class);
	
	//账号
	private String accountName;
	
	//协议
	private String protocol;
	
	//平台
	private String platform;
	
	//服务器
	private int server;
	
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(ByteBuf buf){
	    try {
			//账号
			writeString(buf, this.accountName);
			//协议
			writeString(buf, this.protocol);
			//平台
			writeString(buf, this.platform);
			//服务器
			writeInt(buf, this.server);
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
			//账号
			this.accountName = readString(buf);
			//协议
			this.protocol = readString(buf);
			//平台
			this.platform = readString(buf);
			//服务器
			this.server = readInt(buf);
        } catch (Exception e) {
            log.error(e, e);
            return false;
        }
        return true;
	}
	
	/**
	 * get 账号
	 * @return 
	 */
	public String getAccountName(){
		return accountName;
	}
	
	/**
	 * set 账号
	 */
	public void setAccountName(String accountName){
		this.accountName = accountName;
	}
	
	/**
	 * get 协议
	 * @return 
	 */
	public String getProtocol(){
		return protocol;
	}
	
	/**
	 * set 协议
	 */
	public void setProtocol(String protocol){
		this.protocol = protocol;
	}
	
	/**
	 * get 平台
	 * @return 
	 */
	public String getPlatform(){
		return platform;
	}
	
	/**
	 * set 平台
	 */
	public void setPlatform(String platform){
		this.platform = platform;
	}
	
	/**
	 * get 服务器
	 * @return 
	 */
	public int getServer(){
		return server;
	}
	
	/**
	 * set 服务器
	 */
	public void setServer(int server){
		this.server = server;
	}
	
	
	@Override
	public int getId() {
		return 100100;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//账号
		if(this.accountName!=null) buf.append("accountName:" + accountName.toString() +",");
		//协议
		if(this.protocol!=null) buf.append("protocol:" + protocol.toString() +",");
		//平台
		if(this.platform!=null) buf.append("platform:" + platform.toString() +",");
		//服务器
		buf.append("server:" + server +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}