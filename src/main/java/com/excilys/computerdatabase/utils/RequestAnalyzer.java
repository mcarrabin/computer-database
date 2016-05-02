package com.excilys.computerdatabase.utils;

import javax.servlet.http.HttpServletRequest;

public enum RequestAnalyzer {
    INSTANCE;

    /**
     * This method will get the parameter from the request.
     *
     * @param parameterName
     *            is the name of the parameter
     * @param request
     *            is the request received
     * @param defaultValue
     *            is the default value to assign if the request parameter is
     *            null;
     * @return the parameter value if not null, defaultValue if null.
     */
    public String getStringParameter(String parameterName, HttpServletRequest request, String defaultValue) {
        String result;
        result = request.getParameter(parameterName) == null ? defaultValue : request.getParameter(parameterName);
        return result;
    }
}
