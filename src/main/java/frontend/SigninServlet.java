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

public class SigninServlet extends HttpServlet {

    private AccountService accountService;

    public SigninServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter(ParameterConsts.NAME);
        String password = request.getParameter(ParameterConsts.PASSWORD);

        Map<String, Object> pageVariables = new HashMap<>();
        UserProfile userProfile = accountService.getUser(name);
        if (userProfile != null && password.equals(userProfile.getPassword())) {
            pageVariables.put("loginStatus", "Login passed");
        } else {
            pageVariables.put("loginStatus", "Wrong login/password");
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.getPage("authstatus.html", pageVariables));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter(ParameterConsts.EMAIL);
        String password = request.getParameter(ParameterConsts.PASSWORD);

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(ParameterConsts.EMAIL, email == null ? "" : email);
        pageVariables.put(ParameterConsts.PASSWORD, password == null ? "" : password);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.getPage("authresponse.txt", pageVariables));
    }
}
