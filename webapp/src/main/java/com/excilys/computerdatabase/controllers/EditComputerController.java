package com.excilys.computerdatabase.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    private static final String URL_404 = "redirect:/404";
    private static final String URL_HOME = "redirect:/home";
    private static final String URL_COMPUTER_EDIT = "/computer/edit";
    private static final String JSP_EDIT = "editComputer";

    private static final String ATT_COMPUTER_DTO = "computer";
    private static final String ATT_COMPANIES = "companies";

    // @InitBinder
    // public void initBinder(WebDataBinder webDataBinder) {
    // webDataBinder.setValidator(computerValidator);
    // }

    @RequestMapping(value = URL_COMPUTER_EDIT, method = RequestMethod.GET)
    public String doActionGet(ComputerDto computerDto, ModelMap model) {

        // Parse the Computer id parameter.
        String idParam = computerDto.getComputerId();
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
    public String doActionPost(@Valid @ModelAttribute ComputerDto computerDto, BindingResult result, ModelMap model) {
        this.computerValidator.validate(computerDto, result);
        Map<String, String> errors = new HashMap<String, String>();
        for (ObjectError e : result.getAllErrors()) {
            FieldError temp = (FieldError) e;
            errors.put(temp.getField(), temp.getRejectedValue() + ": " + temp.getDefaultMessage());
        }
        if (result.hasErrors()) {
            model.addAttribute(ATT_COMPUTER_DTO, computerDto);
            model.addAttribute("errors", errors);
            return doActionGet(computerDto, model);
        } else {
            // Build a computer object if the dto is validated.
            Computer computer = dtoMapper.fromDto(computerDto);

            // Call the updateComputer method of the ComputerService if the
            // computer is fine.
            computerService.update(computer);
            return URL_HOME;
        }
    }
}
