package com.game.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.apache.log4j.Logger;

public abstract class Server extends Netty {
    private static Logger logger = Logger.getLogger(Server.class);
    
    protected abstract boolean init();
    public boolean init(int port) {
    	if (!init()) {
    		return false;
    	}
    	
    	Runtime.getRuntime().addShutdownHook(new Thread(new StopThread()));
    	
        EventLoopGroup accepterGroup = new NioEventLoopGroup();
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(accepterGroup, clientGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(getChannelInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync();

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error(e, e);
            return false;
        } finally {
            clientGroup.shutdownGracefully();
            accepterGroup.shutdownGracefully();
        }

        return true;
    }

    private class StopThread implements Runnable {
        public void run() {
            stop();
        }
    }
}
