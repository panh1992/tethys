package org.athena.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.athena.netty.channel.WebSocketChannelInitializer;

import java.net.InetSocketAddress;

public class Server {

    public static void main(String[] args) throws InterruptedException {

        Server server = new Server(8080);

        server.start();

    }

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
        // 主线程组, 用于接收客户端链接, 不做任何处理
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        // 从线程组, 主线程组下发任务, 进行任务处理
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 创建 netty 服务器启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boosGroup, workerGroup) // 设置主从线程组
                    .channel(NioServerSocketChannel.class) // 设置nio的双向通道
                    .childHandler(new WebSocketChannelInitializer()); // 子处理器, 用于处理 workerGroup

            // 用于启动 server, 设置端口, 启动方式同步
            ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(this.port)).sync();
            // 监听关闭的 channel
            channelFuture.channel().closeFuture().sync();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
