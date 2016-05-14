import org.jetbrains.annotations.NotNull;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author apomosov
 */
public class HelloServlet extends HttpServlet {
  @Override
  protected void doGet(@NotNull HttpServletRequest req, @NotNull HttpServletResponse resp) throws ServletException, IOException {
    //super.doGet(req, resp);
    resp.setContentType("text/html;charset=UTF-8");
    resp.setStatus(HttpServletResponse.SC_OK);
    resp.getWriter().println("hello!");
  }
}
