package com.excilys.computerdatabase.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.dto.ComputerDto.ComputerDtoBuilder;
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
    private static final String ATT_ERRORS = "errors";
    private static final String ATT_COMPUTER = "computer";

    @Autowired
    @Qualifier("requestAnalyzer")
    private RequestAnalyzer requestAnalyzer;

    @Autowired
    @Qualifier("computerValidator")
    private ComputerValidator computerValidator;

    @Autowired
    @Qualifier("computerService")
    private ComputerService computerService;

    @Autowired
    @Qualifier("companyService")
    private CompanyService companyService;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<Company> companies = companyService.getAll();

        req.setAttribute(ATT_COMPANIES, companies);
        req.getRequestDispatcher(ADD_COMPUTER_PAGE).forward(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> validatorErrors = new HashMap<>();

        String computerNameParam = requestAnalyzer.getStringParameter(PARAM_COMPUTER_NAME, req, "");
        String companyIdParam = requestAnalyzer.getStringParameter(PARAM_COMPANY_ID, req, "");
        String introducedParam = requestAnalyzer.getStringParameter(PARAM_INTRODUCED_DATE, req, "");
        String discontinuedParam = requestAnalyzer.getStringParameter(PARAM_DISCONTINUED_DATE, req, "");

        Company company = null;
        if (companyIdParam.trim().length() > 0 && !companyIdParam.trim().equalsIgnoreCase("-1")) {
            long companyId = Long.parseLong(companyIdParam);
            company = companyService.getById(companyId);
        }

        validatorErrors = computerValidator.validate(computerNameParam, introducedParam, discontinuedParam);
        ComputerDto computerDto = new ComputerDtoBuilder().name(computerNameParam).introduced(introducedParam)
                .discontinued(discontinuedParam).companyId(companyIdParam).build();
        if (validatorErrors.size() > 0) {
            req.setAttribute(ATT_COMPUTER, computerDto);
            req.setAttribute(ATT_ERRORS, validatorErrors);
            doGet(req, resp);
        } else {

            LocalDate introduced = DateMapper.toLocalDate(introducedParam);
            LocalDate discontinued = DateMapper.toLocalDate(discontinuedParam);

            Computer computer = new Computer().getBuilder().company(company).name(computerNameParam)
                    .introduced(introduced).discontinued(discontinued).build();
            // COMPUTER_VAL.validateComputer(computer);

            computerService.create(computer);
            resp.sendRedirect(HOME_PAGE);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }
}
