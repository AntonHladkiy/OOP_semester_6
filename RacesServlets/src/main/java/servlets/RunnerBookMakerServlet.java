package servlets;

import entity.response.RunnerBookMakerResponse;
import service.RunnerService;
import util.ResponseUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet({"/bookmaker/runners"})
public class RunnerBookMakerServlet extends HttpServlet {
    private final RunnerService runnerService = RunnerService.INSTANCE;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String token = req.getParameter("token");
        RunnerBookMakerResponse response = runnerService.getBookMakerResponse(token);
        ResponseUtil.sendResponse(resp, response);
    }
}
