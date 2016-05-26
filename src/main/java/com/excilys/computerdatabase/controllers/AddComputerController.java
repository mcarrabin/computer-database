package com.excilys.computerdatabase.controllers;

import java.time.LocalDate;
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
import com.excilys.computerdatabase.dto.ComputerDto.ComputerDtoBuilder;
import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.mappers.DateMapper;
import com.excilys.computerdatabase.services.CompanyService;
import com.excilys.computerdatabase.services.ComputerService;
import com.excilys.computerdatabase.utils.RequestAnalyzer;
import com.excilys.computerdatabase.validator.ComputerValidator;

@Controller
public class AddComputerController {

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

    @RequestMapping(value = "/computer/add", method = RequestMethod.GET)
    public String doActionGet(@RequestParam Map<String, String> params, ModelMap model) {
        List<Company> companies = companyService.getAll();

        model.addAttribute(ATT_COMPANIES, companies);
        return "addComputer";
    }

    @RequestMapping(value = "/computer/add", method = RequestMethod.POST)
    public String doActionPost(@RequestParam Map<String, String> params, ModelMap model) {
        Map<String, String> validatorErrors = new HashMap<>();
        String computerNameParam = params.get("computerName");
        String companyIdParam = params.get("companyId");
        String introducedParam = params.get("introduced");
        String discontinuedParam = params.get("discontinued");

        Company company = null;
        if (companyIdParam.trim().length() > 0 && !companyIdParam.trim().equalsIgnoreCase("-1")) {
            long companyId = Long.parseLong(companyIdParam);
            company = companyService.getById(companyId);
        }

        validatorErrors = computerValidator.validate(computerNameParam, introducedParam, discontinuedParam);
        ComputerDto computerDto = new ComputerDtoBuilder().name(computerNameParam).introduced(introducedParam)
                .discontinued(discontinuedParam).companyId(companyIdParam).build();
        if (validatorErrors.size() > 0) {
            model.addAttribute(ATT_COMPUTER, computerDto);
            model.addAttribute(ATT_ERRORS, validatorErrors);
            return doActionGet(params, model);
        } else {
            LocalDate introduced = DateMapper.toLocalDate(introducedParam);
            LocalDate discontinued = DateMapper.toLocalDate(discontinuedParam);

            Computer computer = new Computer().getBuilder().company(company).name(computerNameParam)
                    .introduced(introduced).discontinued(discontinued).build();

            computerService.create(computer);
            return "redirect:/home";
        }
    }
}
