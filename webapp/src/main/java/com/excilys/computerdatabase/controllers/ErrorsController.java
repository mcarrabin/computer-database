package com.excilys.computerdatabase.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorsController {

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String do404() {
        return "404";
    }

    @RequestMapping(value = "/500", method = RequestMethod.GET)
    public String do500() {
        return "500";
    }
}
