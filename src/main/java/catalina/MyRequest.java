package catalina;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

public class MyRequest {

    private ChannelHandlerContext ctx;
    private HttpRequest request;

    public MyRequest(ChannelHandlerContext ctx, HttpRequest request) {
        this.ctx = ctx;
        this.request = request;
    }

    public String getUri() {
        return request.uri();
    }

    public String getMethod() {
        return request.method().name().toString();
    }

    public Map<String, List<String>> getParameters() {
        QueryStringDecoder decoder = new QueryStringDecoder(this.getUri());
        Map<String, List<String>> parameters = decoder.parameters();
        return parameters;
    }

    public String getParameter(String name) {
        List<String> param = getParameters().get(name);
        if(null == param) {
            return null;
        } else {
            return param.get(0);
        }
    }
}
