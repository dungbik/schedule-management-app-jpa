package nbc.sma.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nbc.sma.exception.AuthenticationException;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

public class LoginFilter implements Filter {

    private static final String[] PUBLIC_PATH_LIST = {"/users/register", "/users/login"};

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String requestURI = req.getRequestURI();

        boolean isPrivatePath = !PatternMatchUtils.simpleMatch(PUBLIC_PATH_LIST, requestURI);
        if (isPrivatePath) {
            HttpSession session = req.getSession();

            if (session == null || session.getAttribute("userId") == null) {
                throw new AuthenticationException();
            }
        }

        filterChain.doFilter(request, response);
    }
}
