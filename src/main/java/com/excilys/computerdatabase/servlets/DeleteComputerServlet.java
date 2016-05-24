package com.excilys.computerdatabase.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.computerdatabase.services.ComputerService;
import com.excilys.computerdatabase.utils.RequestAnalyzer;

public class DeleteComputerServlet extends HttpServlet {
    @Autowired
    @Qualifier("requestAnalyzer")
    private RequestAnalyzer requestAnalyzer;

    @Autowired
    @Qualifier("computerService")
    private ComputerService computerService;

    private static final String HOME_URI = "/WEB-INF/jsp/page.jsp";

    private static final String HOME_URL = "/home";

    private static final String PARAM_SELECTION = "selection";

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String selection = requestAnalyzer.getStringParameter(PARAM_SELECTION, req, "");
        String[] idsString = selection.split(",");
        if (idsString.length == 0) {
            req.getRequestDispatcher(HOME_URI).forward(req, res);
        } else {
            for (String s : idsString) {
                Long id = Long.valueOf(s);
                computerService.delete(id);
            }
        }
        res.sendRedirect(req.getContextPath() + HOME_URL);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }
}
