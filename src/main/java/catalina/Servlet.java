package catalina;

public interface Servlet {
    void doGet(MyRequest request, MyResponse response);
    void doPost(MyRequest request, MyResponse response);
}
