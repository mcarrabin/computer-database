package com.excilys.computerdatabase.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.mappers.ComputerDtoMapper;
import com.excilys.computerdatabase.services.CompanyService;
import com.excilys.computerdatabase.services.ComputerService;
import com.excilys.computerdatabase.utils.RequestAnalyzer;
import com.excilys.computerdatabase.validator.ComputerValidator;

public class EditComputerServlet extends HttpServlet {
    private static final RequestAnalyzer REQUEST_ANALYZER = RequestAnalyzer.INSTANCE;
    private static final ComputerValidator COMPUTER_VALIDATOR = ComputerValidator.INSTANCE;
    private static final ComputerService COMPUTER_SERVICE = ComputerService.INSTANCE;
    private static final CompanyService COMPANY_SERVICE = CompanyService.INSTANCE;
    private static final ComputerDtoMapper DTO_MAPPER = ComputerDtoMapper.INSTANCE;

    private static final String HOME_URL = "/home";
    private static final String EDIT_COMPUTER_URL = "/WEB-INF/jsp/editComputer.jsp";
    private static final String NOT_FOUND_URL = "/WEB-INF/jsp/404.html";

    private static final String PARAM_ID_COMPUTER = "computerId";
    private static final String PARAM_NAME_COMPUTER = "computerName";
    private static final String PARAM_INTRODUCED_COMPUTER = "introduced";
    private static final String PARAM_DISCONTINUED_COMPUTER = "discontinued";
    private static final String PARAM_ID_COMPANY = "companyId";

    private static final String ATT_COMPUTER_DTO = "computer";
    private static final String ATT_COMPANIES = "companies";
    private static final String ATT_ERRORS = "errors";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Parse the Computer id parameter.
        String idParam = REQUEST_ANALYZER.getStringParameter(PARAM_ID_COMPUTER, req, "-1");
        long id = Long.parseLong(idParam);

        // Get the computer based on the id (call to ComputerService). If
        // Computer not found, call to page 404.
        Computer computer = COMPUTER_SERVICE.getById(id);
        if (computer == null) {
            req.getRequestDispatcher(NOT_FOUND_URL).forward(req, res);
        } else {
            ComputerDto dto = DTO_MAPPER.toDto(computer);

            // get all companies and add an empty one.
            List<Company> companies = COMPANY_SERVICE.getAll();

            // call the edit computer jsp passing the ComputerDto as response
            // parameter.
            req.setAttribute(ATT_COMPUTER_DTO, dto);
            req.setAttribute(ATT_COMPANIES, companies);
            req.getRequestDispatcher(EDIT_COMPUTER_URL).forward(req, res);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Map<String, String> errorsMessages = new HashMap<>();

        // Parse parameters
        String id = REQUEST_ANALYZER.getStringParameter(PARAM_ID_COMPUTER, req, "-1");
        String name = REQUEST_ANALYZER.getStringParameter(PARAM_NAME_COMPUTER, req, "");
        String introduced = REQUEST_ANALYZER.getStringParameter(PARAM_INTRODUCED_COMPUTER, req, "");
        String discontinued = REQUEST_ANALYZER.getStringParameter(PARAM_DISCONTINUED_COMPUTER, req, "");
        String companyId = REQUEST_ANALYZER.getStringParameter(PARAM_ID_COMPANY, req, "-1");

        // build dto
        Company company = null;
        if (!companyId.equalsIgnoreCase("-1")) {
            company = COMPANY_SERVICE.getById(Long.parseLong(companyId));
        }

        ComputerDto dto = REQUEST_ANALYZER.getComputerDtoFromParam(id, name, introduced, discontinued, companyId,
                company.getName());

        errorsMessages = COMPUTER_VALIDATOR.validate(name, introduced, discontinued);
        if (errorsMessages.size() > 0) {
            req.setAttribute(ATT_ERRORS, errorsMessages);
            req.setAttribute(ATT_COMPUTER_DTO, dto);
            doGet(req, res);
        } else {

            // Build a computer object if the dto is validated.
            Computer computer = DTO_MAPPER.fromDto(dto);

            // Call the createComputer method of the ComputerService if the
            // computer is fine.
            COMPUTER_SERVICE.update(computer);
            res.sendRedirect(req.getContextPath() + HOME_URL);
        }
    }
}
