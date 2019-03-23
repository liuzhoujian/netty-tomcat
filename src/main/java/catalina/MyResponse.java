package catalina;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.io.UnsupportedEncodingException;

public class MyResponse {

    private ChannelHandlerContext ctx;
    private HttpRequest request;

    public MyResponse(ChannelHandlerContext ctx, HttpRequest request) {
        this.ctx = ctx;
        this.request = request;
    }

    public void write(String out) throws UnsupportedEncodingException {
        if(null == out) {
            return;
        }

        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(out.getBytes("UTF-8")));

        //设置Content Length
        HttpHeaderUtil.setContentLength(response, response.content().readableBytes());
        //设置Content Type
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
        //设置过期时间
        response.headers().set(HttpHeaderNames.EXPIRES, HttpHeaderValues.MAX_AGE);
        //如果request中有KEEP ALIVE信息
        if (HttpHeaderUtil.isKeepAlive(request)) {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }

        ctx.writeAndFlush(response);
    }
}
