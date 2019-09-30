package util;

import javax.servlet.http.HttpServletRequest;

/**
 * Define a class to get the root path of server.
 */
public class RootPath {

    /**
     * Method to get the root path of server.
     *
     * @param req The HttpServletRequest object.
     * @return The String value representing the root url.
     */
    public static String getRoot(HttpServletRequest req) {
        String scheme = req.getScheme();
        String host = req.getServerName();
        int port = req.getServerPort();
        String contextPath = req.getContextPath();

        return scheme + "://" + host + (("http".equals(scheme) && port == 80) ? "" : ":" + port) + contextPath;
    }
}
