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

    // private static final String PARAM_SEARCH_STRING = "search";
    // private static final String PARAM_CURRENT_PAGE = "page";
    // private static final String PARAM_ITEMS_PER_PAGE = "elements";
    //
    // // parameter which defines which column will be sorted.
    // private static final String PARAM_ORDER_BY = "orderby";
    //
    // // parameter which defines which way the column needs to be sorted "asc"
    // or
    // // "desc"
    // private static final String PARAM_CURRENT_SORTING = "sort";
    //
    // private static final String ATTR_PAGE = "page";

    private static final int MIN_ELEMENTS_PER_PAGE = 10;
    private static final int MIN_PAGE_NUMBER = 1;

    // private static final String PAGE_URL = "/WEB-INF/jsp/page.jsp";

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
