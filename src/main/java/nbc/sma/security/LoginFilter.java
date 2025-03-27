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

    // 언제든 접근 가능한 PATH 목록
    private static final String[] PUBLIC_PATH_LIST = {"/users/register", "/users/login"};

    /**
     * public/private path 를 구분하여 private path 일 경우 인증 처리
     * 
     * @param request ServletRequest
     * @param response ServletResponse
     * @param filterChain FilterChain
     * @throws IOException
     * @throws ServletException
     */
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

    /**
     * 인증에 실패하여 ErrorResponse 를 응답에 담는다.
     *
     * @param requestURI 요청된 URI
     * @param res HttpServletResponse
     */
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
