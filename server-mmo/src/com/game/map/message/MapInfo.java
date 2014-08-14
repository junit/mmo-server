package com.game.map.message;


import com.game.message.struct.Bean;

import io.netty.buffer.ByteBuf;
import org.apache.log4j.Logger;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 地图对象属性
 */
public class MapInfo extends Bean {

	private static Logger log = Logger.getLogger(MapInfo.class);
	
	//地图id
	private int model;
	
	//地图线
	private int line;
	
	//坐标
	private Position position;
	
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(ByteBuf buf){
	    try {
			//地图id
			writeInt(buf, this.model);
			//地图线
			writeInt(buf, this.line);
			//坐标
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
			//地图id
			this.model = readInt(buf);
			//地图线
			this.line = readInt(buf);
			//坐标
			this.position = (Position)readBean(buf, Position.class);
        } catch (Exception e) {
            log.error(e, e);
            return false;
        }
        return true;
	}
	
	/**
	 * get 地图id
	 * @return 
	 */
	public int getModel(){
		return model;
	}
	
	/**
	 * set 地图id
	 */
	public void setModel(int model){
		this.model = model;
	}
	
	/**
	 * get 地图线
	 * @return 
	 */
	public int getLine(){
		return line;
	}
	
	/**
	 * set 地图线
	 */
	public void setLine(int line){
		this.line = line;
	}
	
	/**
	 * get 坐标
	 * @return 
	 */
	public Position getPosition(){
		return position;
	}
	
	/**
	 * set 坐标
	 */
	public void setPosition(Position position){
		this.position = position;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//地图id
		buf.append("model:" + model +",");
		//地图线
		buf.append("line:" + line +",");
		//坐标
		if(this.position!=null) buf.append("position:" + position.toString() +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}