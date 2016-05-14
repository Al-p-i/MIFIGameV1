package hessian;

import com.caucho.hessian.server.HessianServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by apomosov on 14.05.16.
 */
public class HessianAPIService extends HessianServlet implements HessianAPI {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        resp.getOutputStream().println(hello());
    }

    private String hello = "Hello, World!";

    public void setGreeting(String greeting){
        this.hello = greeting;
    }

    public String hello() {
        return hello;
    }

    public String bye() {
        return "bye!";
    }
}
