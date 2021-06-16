package servlets;

import entity.request.LoginRequest;
import entity.response.TokenResponse;
import service.AuthorizationService;
import util.RequestUtil;
import util.ResponseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/login"})
public class LoginServlet extends HttpServlet {
    private AuthorizationService authorizationService = AuthorizationService.INSTANCE;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        log("Method init =)");
    }

    @Override
    public void destroy() {
        super.destroy();
        log("Method destroy =)");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LoginRequest request = RequestUtil.getRequestObject(req, LoginRequest.class);

        TokenResponse response =
                authorizationService.authorize(request.getUsername(), request.getPassword());

        ResponseUtil.sendResponse(resp, response);
    }
}
