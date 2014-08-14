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
 * 移动消息
 */
public class ReqMapMoveMessage extends Message{

	private static Logger log = Logger.getLogger(ReqMapMoveMessage.class);
	
	//当前坐标
	private Position position;
	//路径
	private List<Byte> path = new ArrayList<>();
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(ByteBuf buf){
	    try {
			//当前坐标
			writeBean(buf, this.position);
			//路径
			writeShort(buf, (short)path.size());
			for (int i = 0; i < path.size(); i++) {
				writeByte(buf, path.get(i));
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
			//当前坐标
			this.position = (Position)readBean(buf, Position.class);
			//路径
			int path_length = readShort(buf);
			for (int i = 0; i < path_length; i++) {
				path.add(readByte(buf));
			}
        } catch (Exception e) {
            log.error(e, e);
            return false;
        }
        return true;
	}
	
	/**
	 * get 当前坐标
	 * @return 
	 */
	public Position getPosition(){
		return position;
	}
	
	/**
	 * set 当前坐标
	 */
	public void setPosition(Position position){
		this.position = position;
	}
	
	/**
	 * get 路径
	 * @return 
	 */
	public List<Byte> getPath(){
		return path;
	}
	
	/**
	 * set 路径
	 */
	public void setPath(List<Byte> path){
		this.path = path;
	}
	
	
	@Override
	public int getId() {
		return 103100;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//当前坐标
		if(this.position!=null) buf.append("position:" + position.toString() +",");
		//路径
		buf.append("path:{");
		for (int i = 0; i < path.size(); i++) {
			buf.append(path.get(i) +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}