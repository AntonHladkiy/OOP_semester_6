package servlets;

import entity.Bet;
import entity.Race;
import entity.User;
import entity.request.AddBetRequest;
import entity.request.BetRequest;
import entity.request.RaceFinishRequest;
import entity.request.RaceRequest;
import entity.response.RacesAdminResponse;
import entity.response.RacesUserResponse;
import service.BetService;
import service.RaceService;
import util.RequestUtil;
import util.ResponseUtil;
import util.TokenUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/bets"})
public class BetServlet extends HttpServlet {
    private final BetService betService = BetService.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BetRequest request = RequestUtil.getRequestObject(req, BetRequest.class);
        Bet bet = betService.save(request);
        ResponseUtil.sendResponse(resp, bet);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AddBetRequest request = RequestUtil.getRequestObject(req, AddBetRequest.class);
        betService.addBet(request);
        ResponseUtil.sendResponse(resp, null);
    }
}