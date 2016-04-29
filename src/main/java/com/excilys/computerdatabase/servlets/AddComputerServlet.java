package com.excilys.computerdatabase.servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.entities.Company.CompanyBuilder;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Computer.ComputerBuilder;
import com.excilys.computerdatabase.mappers.DateMapper;
import com.excilys.computerdatabase.services.CompanyService;
import com.excilys.computerdatabase.services.ComputerService;

public class AddComputerServlet extends HttpServlet {
    private static final String PARAM_COMPUTER_NAME = "computerName";
    private static final String PARAM_INTRODUCED_DATE = "introduced";
    private static final String PARAM_DISCONTINUED_DATE = "discontinued";
    private static final String PARAM_COMPANY_ID = "companyId";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<Company> companies = CompanyService.getInstance().getCompanies();
        Company emptyCompany = new CompanyBuilder().id(-1).name("").build();
        companies.add(0, emptyCompany);
        req.setAttribute("companies", companies);
        req.getRequestDispatcher("/WEB-INF/jsp/AddComputer.jsp").forward(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String computerNameParam = req.getParameter(PARAM_COMPUTER_NAME);
        String companyIdParam = req.getParameter(PARAM_COMPANY_ID);
        String introducedParam = req.getParameter(PARAM_INTRODUCED_DATE);
        String discontinuedParam = req.getParameter(PARAM_DISCONTINUED_DATE);

        long companyId = (companyIdParam == null ? -1 : Long.parseLong(companyIdParam));
        LocalDateTime introduced = DateMapper.toLocalDateTime(introducedParam);
        LocalDateTime discontinued = DateMapper.toLocalDateTime(discontinuedParam);

        Company company = CompanyService.getInstance().getCompanyById(companyId);
        Computer computer = new ComputerBuilder().company(company).name(computerNameParam).introduced(introduced)
                .discontinued(discontinued).build();

        ComputerService.getInstance().createComputer(computer);
        resp.sendRedirect("home");
    }
}
