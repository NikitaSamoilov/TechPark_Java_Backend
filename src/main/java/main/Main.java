package main;

import frontend.Frontend;
import frontend.SigninServlet;
import frontend.SignupServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.Servlet;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String... args) throws Exception {

        int port = getPort(args);
        LOGGER.info(String.format("Starting at port: %s", port));

        AccountService accountService = new AccountService();
        Servlet signupServlet = new SignupServlet(accountService);
        Servlet signinServlet = new SigninServlet(accountService);

        //Frontend frontend = new Frontend();
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(signupServlet), "/authform");

        Server server = new Server(port);
        server.setHandler(context);

        server.start();
        server.join();
    }

    private static int getPort(String... args) {
        if (args.length == 0) {
            return 8080;
        }
        String portString = args[0];
        return Integer.valueOf(portString);
    }
}
