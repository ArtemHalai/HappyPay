package filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static enums.Role.CLIENT;

public class LogFilter implements Filter {

    private Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        String user = (String) session.getAttribute(String.valueOf(CLIENT.getRoleId()));
        if (user != null && ((HttpServletRequest) servletRequest).getMethod().equalsIgnoreCase("GET")) {
            logger.info("Client with username: " + user+" is visiting "+((HttpServletRequest) servletRequest).getServletPath());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
