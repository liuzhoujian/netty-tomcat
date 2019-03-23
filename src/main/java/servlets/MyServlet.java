package servlets;

import catalina.MyRequest;
import catalina.MyResponse;
import catalina.Servlet;

import java.io.UnsupportedEncodingException;

public class MyServlet implements Servlet {

    public void doGet(MyRequest request, MyResponse response) {
        try {
            System.out.println("请求路径：" + request.getUri());
            System.out.println("请求方法：" + request.getMethod());
            //向前端直接响应参数
            response.write(request.getParameter("name"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void doPost(MyRequest request, MyResponse response) {
        doGet(request, response);
    }
}
