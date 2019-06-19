package org.athena.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.athena.netty.channel.WebSocketChannelInitializer;

import java.net.InetSocketAddress;

public class Server {

    private int port;

    /**
     * 构建netty服务
     *
     * @param port 监听端口
     */
    public Server(int port) {
        this.port = port;
    }

    /**
     * netty 服务启动方法
     */
    public void start() throws InterruptedException {
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 使用服务端初始化自定义类WebSocketChannelInitializer
            serverBootstrap.group(boosGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new WebSocketChannelInitializer());

            // 使用了不同的端口绑定方式
            ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(this.port)).sync();
            // 关闭连接
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 优雅关闭
            boosGroup.shutdownGracefully();
        }
    }

}
