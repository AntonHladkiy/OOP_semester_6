package servlets;

import entity.Race;
import entity.User;
import entity.request.RaceFinishRequest;
import entity.request.RaceRequest;
import entity.response.RacesAdminResponse;
import entity.response.RacesUserResponse;
import service.RaceService;
import util.RequestUtil;
import util.ResponseUtil;
import util.TokenUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/races"})
public class RaceServlet extends HttpServlet {
    private final RaceService raceService = RaceService.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RaceRequest request = RequestUtil.getRequestObject(req, RaceRequest.class);
        Race race = raceService.save(request);

        ResponseUtil.sendResponse(resp, race);
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RaceFinishRequest request = RequestUtil.getRequestObject(req, RaceFinishRequest.class);
        raceService.finishRace(request);
        ResponseUtil.sendResponse(resp,null);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String token = req.getParameter("token");
        User user= TokenUtil.getUserByToken( token );
        if(user.getRole().equals( "Admin" )){
            RacesAdminResponse races=RaceService.INSTANCE.getAdminResponse();
            ResponseUtil.sendResponse(resp, races);
        }else if(user.getRole().equals( "User" )){
            RacesUserResponse races=RaceService.INSTANCE.getUserResponse(user.getId());
            ResponseUtil.sendResponse(resp, races);
        } else{
            throw new RuntimeException( "not valid role" );
        }


    }
}
