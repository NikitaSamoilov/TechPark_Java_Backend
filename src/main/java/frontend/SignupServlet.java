package frontend;

import main.AccountService;
import main.UserProfile;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignupServlet extends HttpServlet {

    private AccountService accountService;

    public SignupServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter(ParameterConsts.NAME);
        String password = request.getParameter(ParameterConsts.PASSWORD);

        Map<String, Object> pageVariables = new HashMap<>();
        if (accountService.addUser(name, new UserProfile(name, password, ""))) {
            pageVariables.put(ParameterConsts.SIGNUP_STATUS, "New user created");
        } else {
            pageVariables.put(ParameterConsts.SIGNUP_STATUS, String.format("User %s has already exists", name));
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.getPage("signupstatus.html", pageVariables));
    }
}
