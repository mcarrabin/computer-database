package com.excilys.computerdatabase.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.services.ComputerService;
import com.excilys.computerdatabase.utils.RequestAnalyzer;

public class DeleteComputerServlet extends HttpServlet {
    private static final RequestAnalyzer REQUEST_ANALYZER = RequestAnalyzer.INSTANCE;
    private static final ComputerService COMPUTER_SERVICE = ComputerService.INSTANCE;

    private static final String ERROR_URL = "/WEB-INF/jsp/404.html";
    private static final String HOME_URI = "/WEB-INF/jsp/page.jsp";

    private static final String HOME_URL = "/home";

    private static final String PARAM_SELECTION = "selection";

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String selection = REQUEST_ANALYZER.getStringParameter(PARAM_SELECTION, req, "");
        String[] idsString = selection.split(",");
        if (idsString.length == 0) {
            req.getRequestDispatcher(HOME_URI).forward(req, res);
        } else {
            for (String s : idsString) {
                Long id = Long.valueOf(s);
                COMPUTER_SERVICE.deleteComputer(id);
            }
        }
        res.sendRedirect(req.getContextPath() + HOME_URL);
    }
}
