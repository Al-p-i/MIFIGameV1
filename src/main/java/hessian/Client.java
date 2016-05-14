package hessian;

import com.caucho.hessian.client.HessianProxyFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.net.MalformedURLException;

/**
 * Created by apomosov on 14.05.16.
 */
public class Client {
    public static void main(String[] args) throws MalformedURLException {
        String url = "http://localhost:8080/hello";

        HessianProxyFactory factory = new HessianProxyFactory();
        HessianAPI hessianAPI = (HessianAPI) factory.create(HessianAPI.class, url);

        System.out.println("hello(): " + hessianAPI.hello());
        System.out.println("bye(): " + hessianAPI.bye());
    }
}
