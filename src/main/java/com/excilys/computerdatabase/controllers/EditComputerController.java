package com.excilys.computerdatabase.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.mappers.ComputerDtoMapper;
import com.excilys.computerdatabase.services.CompanyService;
import com.excilys.computerdatabase.services.ComputerService;
import com.excilys.computerdatabase.utils.RequestAnalyzer;
import com.excilys.computerdatabase.validator.ComputerValidator;

@Controller
public class EditComputerController {

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

    @Autowired
    @Qualifier("computerDtoMapper")
    private ComputerDtoMapper dtoMapper;

    private static final String PARAM_ID_COMPUTER = "computerId";
    private static final String PARAM_NAME_COMPUTER = "computerName";
    private static final String PARAM_INTRODUCED_COMPUTER = "introduced";
    private static final String PARAM_DISCONTINUED_COMPUTER = "discontinued";
    private static final String PARAM_ID_COMPANY = "companyId";

    private static final String URL_404 = "redirect:/404";
    private static final String URL_HOME = "redirect:/home";
    private static final String URL_COMPUTER_EDIT = "/computer/edit";
    private static final String JSP_EDIT = "editComputer";

    private static final String ATT_COMPUTER_DTO = "computer";
    private static final String ATT_COMPANIES = "companies";
    private static final String ATT_ERRORS = "errors";

    @RequestMapping(value = URL_COMPUTER_EDIT, method = RequestMethod.GET)
    public String doActionGet(@RequestParam Map<String, String> params, ModelMap model) {
        // Parse the Computer id parameter.
        String idParam = params.get(PARAM_ID_COMPUTER);
        long id = Long.parseLong(idParam);

        // Get the computer based on the id (call to ComputerService). If
        // Computer not found, call to page 404.
        Computer computer = computerService.getById(id);
        if (computer == null) {
            return URL_404;
        } else {
            ComputerDto dto = dtoMapper.toDto(computer);

            // get all companies
            List<Company> companies = companyService.getAll();

            // call the edit computer jsp passing the ComputerDto as response
            // parameter.
            model.addAttribute(ATT_COMPUTER_DTO, dto);
            model.addAttribute(ATT_COMPANIES, companies);
            return JSP_EDIT;
        }

    }

    @RequestMapping(value = URL_COMPUTER_EDIT, method = RequestMethod.POST)
    public String doActionPost(@RequestParam Map<String, String> params, ModelMap model) {
        Map<String, String> errorsMessages = new HashMap<>();

        // Parse parameters
        String id = params.get(PARAM_ID_COMPUTER);
        String name = params.get(PARAM_NAME_COMPUTER);
        String introduced = params.get(PARAM_INTRODUCED_COMPUTER);
        String discontinued = params.get(PARAM_DISCONTINUED_COMPUTER);
        String companyId = params.get(PARAM_ID_COMPANY);

        // build dto
        Company company = null;
        if (!companyId.equalsIgnoreCase("-1")) {
            company = companyService.getById(Long.parseLong(companyId));
        }

        ComputerDto dto = requestAnalyzer.getComputerDtoFromParam(id, name, introduced, discontinued, companyId,
                company.getName());

        errorsMessages = computerValidator.validate(name, introduced, discontinued);
        if (errorsMessages.size() > 0) {
            model.addAttribute(ATT_ERRORS, errorsMessages);
            model.addAttribute(ATT_COMPUTER_DTO, dto);
            return doActionGet(params, model);
        } else {

            // Build a computer object if the dto is validated.
            Computer computer = dtoMapper.fromDto(dto);

            // Call the updateComputer method of the ComputerService if the
            // computer is fine.
            computerService.update(computer);
            return URL_HOME;
        }
    }
}
