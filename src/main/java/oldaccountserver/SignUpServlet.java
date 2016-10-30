package oldaccountserver;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author esin88
 */
public class SignUpServlet extends HttpServlet {
    private final AccountServer accountServer;

    public SignUpServlet(AccountServer accountService) {
        this.accountServer = accountService;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        final String name = request.getParameter("name");
        final String password = request.getParameter("password");

        final JsonObject answer = getAnswer(response, name, password);

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(answer.toString());
    }

    @NotNull
    private JsonObject getAnswer(HttpServletResponse response, String name, String password) {
        final JsonObject answer = new JsonObject();

        if (!checkCredential(name)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            answer.addProperty("Status", "Name is empty");
            return  answer;
        } else if (!checkCredential(password)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            answer.addProperty("Status", "Password is empty");
            return  answer;
        }
        UserProfile user = accountServer.getUser(name);
        if (user != null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            answer.addProperty("Status", "User exists");
            return  answer;
        }
        if (accountServer.addUser(new UserProfile(name, password))) {
            response.setStatus(HttpServletResponse.SC_OK);
            answer.addProperty("Status", "Ok");
            return  answer;
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            answer.addProperty("Status", "Error while retrieving user");
            return  answer;
        }
    }

    private boolean checkCredential(@Nullable String credential) {
        return credential != null && !credential.isEmpty();
    }
}
