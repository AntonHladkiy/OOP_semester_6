package servlets;

import entity.User;
import entity.request.UserCreateRequest;
import entity.response.UserResponse;
import mappers.UserMapper;
import service.UserService;
import util.RequestUtil;
import util.ResponseUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/signup"})
public class SignupServlet extends HttpServlet {

    private final UserService userService = UserService.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        UserCreateRequest request = RequestUtil.getRequestObject(req, UserCreateRequest.class);

        User user = userService.create(request);

        UserResponse userResponse = UserMapper.INSTANCE.toUserResponse(user);
        ResponseUtil.sendResponse(resp, userResponse);
    }


}
