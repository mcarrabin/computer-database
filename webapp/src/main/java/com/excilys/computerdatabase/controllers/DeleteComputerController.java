package com.excilys.computerdatabase.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computerdatabase.services.ComputerService;
import com.excilys.computerdatabase.utils.RequestAnalyzer;

@Controller
public class DeleteComputerController {
    @Autowired
    @Qualifier("requestAnalyzer")
    private RequestAnalyzer requestAnalyzer;

    @Autowired
    @Qualifier("computerService")
    private ComputerService computerService;

    private static final String HOME_URI = "page";

    private static final String HOME_URL = "redirect:/home";

    private static final String PARAM_SELECTION = "selection";

    @RequestMapping(value = "/computer/delete", method = RequestMethod.POST)
    public String doActionPost(@RequestParam Map<String, String> params, ModelMap model) {
        String selection = params.get(PARAM_SELECTION);
        String[] idsString = selection.split(",");
        if (idsString.length == 0) {
            return HOME_URI;
        } else {
            for (String s : idsString) {
                Long id = Long.valueOf(s);
                computerService.delete(id);
            }
            return HOME_URL;
        }
    }
}
