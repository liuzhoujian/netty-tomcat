package netty.server;

import catalina.MyRequest;
import catalina.MyResponse;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import servlets.MyServlet;

public class MyTomcatHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof HttpRequest) {
            HttpRequest r = (HttpRequest)msg;

            /*解析为自定义的request和response*/
            MyRequest myRequest = new MyRequest(ctx, r);
            MyResponse myResponse = new MyResponse(ctx, r);

            /*执行逻辑*/
            new MyServlet().doGet(myRequest, myResponse);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
