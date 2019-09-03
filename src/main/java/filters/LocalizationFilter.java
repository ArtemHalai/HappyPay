package filters;


import factories.LocaleFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LocalizationFilter implements Filter {
    private static final String LANGUAGE = "language";
    private static final String LOCALE = "locale";

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String locale = req.getParameter(LOCALE);

        String userLocale;
        if (locale != null) {
            userLocale = LocaleFactory.getInstance().getLocale(locale);
        } else {
            userLocale = (String) req.getSession().getAttribute(LANGUAGE);
        }

        req.getSession().setAttribute(LANGUAGE, userLocale);
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
