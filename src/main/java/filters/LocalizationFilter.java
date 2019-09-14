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
