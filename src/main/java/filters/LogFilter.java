package filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static enums.Fields.ROLE;
import static enums.Role.CLIENT;

/**
 * Define a log filter class.
 * This class is implementation of Filter.
 *
 * @see Filter
 */
public class LogFilter implements Filter {

    private Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * Method to write in log file data about logged in user.
     * Invoke the next entity in the chain
     * using the FilterChain object
     * (<code>filterChain.doFilter()</code>).
     *
     * @param servletRequest  The ServletRequest object.
     * @param servletResponse The ServletResponse object.
     * @param filterChain     The FilterChain object.
     * @throws IOException      If IO exception occurred while processing this request.
     * @throws ServletException If servlet exception occurred while processing this request.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        if (session.getAttribute(ROLE.getName()) != null) {
            int role = (int) session.getAttribute(ROLE.getName());
            if (role == CLIENT.getRoleId()) {
                logger.info("Client is visiting " + ((HttpServletRequest) servletRequest).getServletPath());
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
