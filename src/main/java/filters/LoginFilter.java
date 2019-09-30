package filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static enums.Fields.ROLE;
import static enums.Mappings.LOGGED_IN;
import static enums.Role.*;

/**
 * Define a login filter class.
 * This class is implementation of Filter.
 *
 * @see Filter
 */
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Method to get session and if session contains role attribute then redirect user to logged view page and ask if
     * user wants to logout.
     * Or invoke the next entity in the chain
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
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession(false);

        if (session.getAttribute(ROLE.getName()) != null) {
            int role = (int) session.getAttribute(ROLE.getName());
            if (role == CLIENT.getRoleId() ||
                    role == ADMIN.getRoleId()) {
                resp.sendRedirect(LOGGED_IN.getName());
            }
        } else {
            filterChain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {

    }
}
