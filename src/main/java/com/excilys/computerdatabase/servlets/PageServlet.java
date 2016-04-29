package com.excilys.computerdatabase.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Page;
import com.excilys.computerdatabase.entities.Page.PageBuilder;
import com.excilys.computerdatabase.mappers.ComputerDtoMapper;
import com.excilys.computerdatabase.services.PageService;

public class PageServlet extends HttpServlet {

    private static final String PARAM_SEARCH_STRING = "search";
    private static final String PARAM_CURRENT_PAGE = "numPage";
    private static final String PARAM_ITEMS_PER_PAGE = "elements";

    private static final int MIN_ELEMENTS_PER_PAGE = 10;
    private static final int MIN_PAGE_NUMBER = 1;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int itemsPerPage = 10;
        int currentPage = 1;

        // Parse search param
        String searchString = req.getParameter(PARAM_SEARCH_STRING) == null ? ""
                : req.getParameter(PARAM_SEARCH_STRING);

        // Parse page param
        String pageParam = req.getParameter(PARAM_CURRENT_PAGE);
        if (pageParam != null) {
            int p = Integer.parseInt(pageParam);
            currentPage = p >= MIN_PAGE_NUMBER ? p : MIN_PAGE_NUMBER;
        }

        // Parse itemsPerPage param
        String itemsPerPageParam = req.getParameter(PARAM_ITEMS_PER_PAGE);
        if (itemsPerPageParam != null) {
            int p = Integer.parseInt(itemsPerPageParam);
            itemsPerPage = p >= MIN_ELEMENTS_PER_PAGE ? p : MIN_ELEMENTS_PER_PAGE;
        }

        // Get page from service
        Page<Computer> page = PageService.getInstance().getPage(itemsPerPage, currentPage, searchString);

        List<ComputerDto> computerDtos = ComputerDtoMapper.getInstance().toDtoList(page.getElements());
        Page<ComputerDto> pageDto = new PageBuilder<ComputerDto>().elements(computerDtos)
                .numElementTotal(page.getNumElementTotal()).numPage(page.getNumPage()).numPageMax(page.getNumPageMax())
                .searchFilter(page.getSearchFilter()).build();
        // Set attribute
        req.setAttribute("page", pageDto);

        // Forward
        req.getRequestDispatcher("/WEB-INF/jsp/Page.jsp").forward(req, resp);

    }

}
