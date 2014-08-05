package com.game.message;
	
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.game.message.struct.Handler;
import com.game.message.struct.Message;

public class MessagePool {
	private static MessagePool instance = new MessagePool();
	private MessagePool() {}
	public static MessagePool getInstance() {
		return instance;
	}
	
	private Logger logger = Logger.getLogger(this.getClass());
	
    private HashMap<Integer, Class<? extends Handler>> id2handler = new HashMap<Integer, Class<? extends Handler>>();
    private HashMap<Integer, Class<? extends Message>> id2message = new HashMap<Integer, Class<? extends Message>>();

    public void register(int id, Class<? extends Handler> handlerClass, Class<? extends Message> messageClass) {
        id2handler.put(id, handlerClass);
        id2message.put(id, messageClass);
    }

    public Handler createHandler(int id) {
        Class<? extends Handler> handlerClass = id2handler.get(id);
        if (handlerClass == null) {
            return null;
        }
        Handler handler = null;
        try {
            handler = handlerClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return handler;
    }

    public Message createMessage(int id) {
        Class<? extends Message> messageClass = id2message.get(id);
        if (messageClass == null) {
            return null;
        }
        Message message = null;
        try {
            message = messageClass.newInstance();
        } catch (InstantiationException e) {
        	logger.error(e, e);
        } catch (IllegalAccessException e) {
        	logger.error(e, e);
        }
        return message;
    }
}
