package com.excilys.computerdatabase.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Page;
import com.excilys.computerdatabase.mappers.ComputerDtoMapper;
import com.excilys.computerdatabase.services.PageService;
import com.excilys.computerdatabase.utils.RequestAnalyzer;

public class PageServlet extends HttpServlet {

    private static final String PARAM_SEARCH_STRING = "search";
    private static final String PARAM_CURRENT_PAGE = "page";
    private static final String PARAM_ITEMS_PER_PAGE = "elements";

    // parameter which defines which column will be sorted.
    private static final String PARAM_ORDER_BY = "orderby";

    // parameter which defines which way the column needs to be sorted "asc" or
    // "desc"
    private static final String PARAM_CURRENT_SORTING = "sort";

    private static final String ATTR_PAGE = "page";

    private static final int MIN_ELEMENTS_PER_PAGE = 10;
    private static final int MIN_PAGE_NUMBER = 1;

    private static final String PAGE_URL = "/WEB-INF/jsp/page.jsp";

    private static final RequestAnalyzer REQUEST_ANALYZER = RequestAnalyzer.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int itemsPerPage = 10;
        int currentPage = 1;

        // Parse search param
        String searchString = REQUEST_ANALYZER.getStringParameter(PARAM_SEARCH_STRING, req, "");

        // Parse page param
        String pageParam = REQUEST_ANALYZER.getStringParameter(PARAM_CURRENT_PAGE, req, "1");
        int p = Integer.parseInt(pageParam);
        currentPage = p >= MIN_PAGE_NUMBER ? p : MIN_PAGE_NUMBER;

        // Parse itemsPerPage param
        String itemsPerPageParam = REQUEST_ANALYZER.getStringParameter(PARAM_ITEMS_PER_PAGE, req,
                String.valueOf(MIN_ELEMENTS_PER_PAGE));
        int i = Integer.parseInt(itemsPerPageParam);
        itemsPerPage = i >= MIN_ELEMENTS_PER_PAGE ? i : MIN_ELEMENTS_PER_PAGE;

        // Parse orderBy param
        String orderByParam = REQUEST_ANALYZER.getStringParameter(PARAM_ORDER_BY, req, "");

        // Parse currentsorting param
        String sortParam = REQUEST_ANALYZER.getStringParameter(PARAM_CURRENT_SORTING, req, "");

        // Get page from service
        Page<Computer> page = PageService.INSTANCE.getPage(itemsPerPage, currentPage, searchString, orderByParam,
                sortParam);

        List<Computer> computers = page.getElements();
        List<ComputerDto> computerDtos = new ArrayList<>();
        if (computers != null) {
            computerDtos = ComputerDtoMapper.INSTANCE.toDtoList(computers);
        }

        Page<ComputerDto> pageDto = new Page<ComputerDto>().getBuilder().elements(computerDtos)
                .itemsTotalCount(page.getItemsTotalCount()).currentPage(page.getCurrentPage())
                .maxPage(page.getMaxPage()).search(page.getSearch()).itemsPerPage(itemsPerPage)
                .orderBy(page.getOrderBy()).sorting(page.getSorting()).build();

        // Set attribute
        req.setAttribute(ATTR_PAGE, pageDto);

        // Forward
        req.getRequestDispatcher(PAGE_URL).forward(req, resp);

    }

}
