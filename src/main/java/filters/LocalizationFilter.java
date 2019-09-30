package filters;

import factories.LocaleFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Define a localization filter class.
 * This class is implementation of Filter.
 *
 * @see Filter
 */
public class LocalizationFilter implements Filter {

    private static final String LANGUAGE = "language";
    private static final String LOCALE = "locale";

    @Override
    public void init(FilterConfig filterConfig) {

    }

    /**
     * Method to get user's locale and set it to session.
     * Invoke the next entity in the chain
     * using the FilterChain object
     * (<code>filterChain.doFilter()</code>).
     *
     * @param request     The ServletRequest object.
     * @param response    The ServletResponse object.
     * @param filterChain The FilterChain object.
     * @throws IOException      If IO exception occurred while processing this request.
     * @throws ServletException If servlet exception occurred while processing this request.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String locale = req.getParameter(LOCALE);

        if (locale != null) {
            locale = LocaleFactory.getInstance().getLocale(locale);
        } else {
            locale = (String) req.getSession().getAttribute(LANGUAGE);
        }

        req.getSession().setAttribute(LANGUAGE, locale);
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
