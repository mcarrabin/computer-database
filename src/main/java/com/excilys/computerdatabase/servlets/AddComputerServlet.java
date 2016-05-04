package com.excilys.computerdatabase.servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.mappers.DateMapper;
import com.excilys.computerdatabase.services.CompanyService;
import com.excilys.computerdatabase.services.ComputerService;
import com.excilys.computerdatabase.utils.RequestAnalyzer;
import com.excilys.computerdatabase.validator.ComputerValidator;

public class AddComputerServlet extends HttpServlet {
    private static final String PARAM_COMPUTER_NAME = "computerName";
    private static final String PARAM_INTRODUCED_DATE = "introduced";
    private static final String PARAM_DISCONTINUED_DATE = "discontinued";
    private static final String PARAM_COMPANY_ID = "companyId";

    private static final String ADD_COMPUTER_PAGE = "/WEB-INF/jsp/addComputer.jsp";
    private static final String HOME_PAGE = "/computerdatabase/home";

    private static final String ATT_COMPANIES = "companies";

    private static final RequestAnalyzer REQUEST_ANALYZER = RequestAnalyzer.INSTANCE;
    private static final ComputerValidator COMPUTER_VAL = ComputerValidator.INSTANCE;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<Company> companies = CompanyService.INSTANCE.getCompanies();
        Company emptyCompany = new Company().getBuilder().id(-1).name("").build();
        companies.add(0, emptyCompany);
        req.setAttribute(ATT_COMPANIES, companies);
        req.getRequestDispatcher(ADD_COMPUTER_PAGE).forward(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String computerNameParam = REQUEST_ANALYZER.getStringParameter(PARAM_COMPUTER_NAME, req, "");
        String companyIdParam = REQUEST_ANALYZER.getStringParameter(PARAM_COMPANY_ID, req, "");
        String introducedParam = REQUEST_ANALYZER.getStringParameter(PARAM_INTRODUCED_DATE, req, "");
        String discontinuedParam = REQUEST_ANALYZER.getStringParameter(PARAM_DISCONTINUED_DATE, req, "");

        Company company = null;
        if (companyIdParam.trim().length() > 0 && !companyIdParam.trim().equalsIgnoreCase("-1")) {
            long companyId = Long.parseLong(companyIdParam);
            company = CompanyService.INSTANCE.getCompanyById(companyId);
        }

        LocalDateTime introduced = DateMapper.toLocalDateTime(introducedParam);
        LocalDateTime discontinued = DateMapper.toLocalDateTime(discontinuedParam);

        Computer computer = new Computer().getBuilder().company(company).name(computerNameParam).introduced(introduced)
                .discontinued(discontinued).build();
        COMPUTER_VAL.validateComputer(computer);

        ComputerService.INSTANCE.createComputer(computer);
        resp.sendRedirect(HOME_PAGE);
    }
}
