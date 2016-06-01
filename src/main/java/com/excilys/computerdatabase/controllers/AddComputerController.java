package com.excilys.computerdatabase.controllers;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.mappers.DateMapper;
import com.excilys.computerdatabase.services.CompanyService;
import com.excilys.computerdatabase.services.ComputerService;
import com.excilys.computerdatabase.utils.RequestAnalyzer;
import com.excilys.computerdatabase.validator.ComputerValidator;

@Controller
public class AddComputerController {

    private static final String URL_HOME = "redirect:/home";
    private static final String URL_ADD_COMPUTER = "addComputer";

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
    public String doActionGet(@Valid @ModelAttribute ComputerDto computerDto, BindingResult result, ModelMap model) {
        List<Company> companies = companyService.getAll();

        model.addAttribute(ATT_COMPANIES, companies);
        return URL_ADD_COMPUTER;
    }

    @RequestMapping(value = "/computer/add", method = RequestMethod.POST)
    public String doActionPost(@Valid @ModelAttribute ComputerDto computerDto, BindingResult result, ModelMap model) {
        this.computerValidator.validate(computerDto, result);
        String companyIdParam = computerDto.getCompanyId();
        Company company = null;
        if (companyIdParam.trim().length() > 0 && !companyIdParam.trim().equalsIgnoreCase("-1")) {
            long companyId = Long.parseLong(companyIdParam);
            company = companyService.getById(companyId);
        }

        if (result.hasErrors()) {
            model.addAttribute(ATT_COMPUTER, computerDto);
            model.addAttribute(ATT_ERRORS, result.getAllErrors());
            return doActionGet(computerDto, result, model);
        } else {
            LocalDateTime introduced = DateMapper.toLocalDateTime(computerDto.getIntroduced());
            LocalDateTime discontinued = DateMapper.toLocalDateTime(computerDto.getDiscontinued());

            Computer computer = Computer.getBuilder().company(company).name(computerDto.getComputerName())
                    .introduced(introduced).discontinued(discontinued).build();

            computerService.create(computer);
            return URL_HOME;
        }
    }
}
