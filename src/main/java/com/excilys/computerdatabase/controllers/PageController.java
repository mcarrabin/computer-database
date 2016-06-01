package com.excilys.computerdatabase.controllers;

import java.util.ArrayList;
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
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Page;
import com.excilys.computerdatabase.mappers.ComputerDtoMapper;
import com.excilys.computerdatabase.services.PageService;
import com.excilys.computerdatabase.utils.RequestAnalyzer;

@Controller
public class PageController {

    private static final int MIN_ELEMENTS_PER_PAGE = 10;
    private static final int MIN_PAGE_NUMBER = 1;

    @Autowired
    @Qualifier("requestAnalyzer")
    private RequestAnalyzer requestAnalyzer;

    @Autowired
    @Qualifier("pageService")
    private PageService pageService;

    @Autowired
    @Qualifier("computerDtoMapper")
    private ComputerDtoMapper computerDtoMapper;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String doAction(@RequestParam Map<String, String> params, ModelMap model) {

        String pageParam = params.get("page");
        String itemsPerPageParam = params.get("itemsPerPage");
        String orderByParam = params.get("orderBy");
        String searchString = params.get("search");
        String sortParam = params.get("sort");
        int currentPage = 1;
        int p = pageParam == null ? MIN_PAGE_NUMBER : Integer.parseInt(pageParam);
        currentPage = p >= MIN_PAGE_NUMBER ? p : MIN_PAGE_NUMBER;

        // Parse itemsPerPage param
        int i = itemsPerPageParam == null ? MIN_ELEMENTS_PER_PAGE : Integer.parseInt(itemsPerPageParam);
        int itemsPerPage = i >= MIN_ELEMENTS_PER_PAGE ? i : MIN_ELEMENTS_PER_PAGE;

        // Get page from service
        Page<Computer> page = pageService.getPage(itemsPerPage, currentPage, searchString, orderByParam, sortParam);

        List<Computer> computers = page.getElements();
        List<ComputerDto> computerDtos = new ArrayList<>();
        if (computers != null) {
            computerDtos = computerDtoMapper.toDtoList(computers);
        }

        Page<ComputerDto> pageDto = new Page<ComputerDto>().getBuilder().elements(computerDtos)
                .itemsTotalCount(page.getItemsTotalCount()).currentPage(page.getCurrentPage())
                .maxPage(page.getMaxPage()).search(page.getSearch()).itemsPerPage(itemsPerPage)
                .orderBy(page.getOrderBy()).sorting(page.getSorting()).build();

        // Set attribute
        model.addAttribute("page", pageDto);

        // Render
        return "page";

    }

}
