package filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static enums.Mappings.LOGGED_IN;
import static enums.Role.*;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession(false);

        if (session.getAttribute(String.valueOf(CLIENT.getRoleId())) != null ||
                session.getAttribute(String.valueOf(ADMIN.getRoleId())) != null ||
                session.getAttribute(String.valueOf(MANAGER.getRoleId())) != null) {
            resp.sendRedirect(LOGGED_IN.getName());
        } else {
            filterChain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {

    }
}
