package util;

import javax.servlet.http.HttpServletRequest;

public class RootPath {

    private RootPath(){

    }

    public static String getRoot(HttpServletRequest req) {
        String scheme = req.getScheme();
        String host = req.getServerName();
        int port = req.getServerPort();
        String contextPath = req.getContextPath();

        return scheme + "://" + host + (("http".equals(scheme) && port == 80) ? "" : ":" + port) + contextPath;
    }
}
