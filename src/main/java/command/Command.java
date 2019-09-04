package command;

import enums.Mappings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    Mappings execute(HttpServletRequest request, HttpServletResponse response);
}
