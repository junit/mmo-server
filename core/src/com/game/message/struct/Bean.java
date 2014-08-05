package com.game.message.struct;

import io.netty.buffer.ByteBuf;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;

/**
 * Created by game on 4/2/14.
 */
public abstract class Bean {
    private static Logger log = Logger.getLogger(Bean.class);
    public abstract boolean read(ByteBuf buf);
    public abstract boolean write(ByteBuf buf);

    // byte
    public byte readByte(ByteBuf buf) {
        return buf.readByte();
    }
    public void writeByte(ByteBuf buf, byte value) {
        buf.writeByte(value);
    }

    // short
    public short readShort(ByteBuf buf) {
        return buf.readShort();
    }
    public void writeShort(ByteBuf buf, short value) {
        buf.writeShort(value);
    }

    // int
    public int readInt(ByteBuf buf) {
        return buf.readInt();
    }
    public void writeInt(ByteBuf buf, int value) {
        buf.writeInt(value);
    }

    // long
    public long readLong(ByteBuf buf) {
        return buf.readLong();
    }
    public void writeLong(ByteBuf buf, long value) {
        buf.writeLong(value);
    }

    // string
    public String readString(ByteBuf buf) {
        int length = buf.readInt();
        if (length <= 0) {
            return null;
        }
        if (buf.readableBytes() < length) {
            return null;
        }
        byte[] bytes = new byte[length];
        buf.readBytes(bytes);
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error(e, e);
        }
        return null;
    }
    public void writeString(ByteBuf buf, String value) {
        if (value == null) {
            buf.writeInt(0);
            return;
        }

        try {
            byte[] bytes = value.getBytes("UTF-8");
            buf.writeInt(bytes.length);
            buf.writeBytes(bytes);
        } catch (UnsupportedEncodingException e) {
            log.error(e, e);
        }
    }

    // bean
    public Bean readBean(ByteBuf buf, Class<? extends Bean> clazz) {
        try{
            Bean bean = clazz.newInstance();
            bean.read(buf);
            return bean;
        }catch (Exception e) {
            log.error(e, e);
        }
        return null;
    }
    public void writeBean(ByteBuf buf, Bean value) {
        value.write(buf);
    }
}
