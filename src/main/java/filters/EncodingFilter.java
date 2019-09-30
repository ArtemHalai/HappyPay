package filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * Define encoding filter class.
 * This class is implementation of Filter.
 *
 * @see Filter
 */
public class EncodingFilter implements Filter {

    private static final String ENCODING = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * Method to set character encoding to request and response.
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
        servletRequest.setCharacterEncoding(ENCODING);
        servletResponse.setCharacterEncoding(ENCODING);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
