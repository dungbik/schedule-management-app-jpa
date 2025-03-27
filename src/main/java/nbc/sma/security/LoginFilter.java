package nbc.sma.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import nbc.sma.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

    private static final String[] PUBLIC_PATH_LIST = {"/users/register", "/users/login"};

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String requestURI = req.getRequestURI();

        boolean isPrivatePath = !PatternMatchUtils.simpleMatch(PUBLIC_PATH_LIST, requestURI);
        if (isPrivatePath) {
            HttpSession session = req.getSession();

            if (session == null || session.getAttribute("userId") == null) {
                exceptionHandler(requestURI, res);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void exceptionHandler(String requestURI, HttpServletResponse res) {
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            String json = objectMapper.writeValueAsString(
                    ErrorResponse.of(
                            HttpStatus.UNAUTHORIZED,
                            "Please log in and try again.",
                            requestURI)
            );
            res.getWriter().write(json);
        } catch (Exception e) {
            log.error("LoginFilter - exceptionHandler {}", e.getMessage());
        }
    }
}
