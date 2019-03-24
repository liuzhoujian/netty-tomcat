package netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class MyTomcat {

    private int port = 8080;

    public void start(int port) throws InterruptedException {
        this.port = port;
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();

            server.channel(NioServerSocketChannel.class)
                    .group(boss, worker)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //解码和编码HTTP请求
                            socketChannel.pipeline().addLast(new HttpServerCodec());
                           /* // 请求消息解码器
                            socketChannel.pipeline().addLast(new HttpRequestDecoder());
                            //响应解码器
                            socketChannel.pipeline().addLast(new HttpResponseEncoder());*/
                            //自定义处理逻辑
                            socketChannel.pipeline().addLast(new MyTomcatHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = server.bind("localhost", this.port).sync();

            System.out.println("服务器已启动：" + port);

            future.channel().closeFuture().sync();
        } finally {
            /*优雅关闭*/
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        try {
            new MyTomcat().start(8080);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
