package com.excilys.computerdatabase.tags;

import javax.servlet.jsp.tagext.TagSupport;

public class PaginationTagHandler extends TagSupport {
    private int currentPage;
    private int numPageMax;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getNumPageMax() {
        return numPageMax;
    }

    public void setNumPageMax(int numPageMax) {
        this.numPageMax = numPageMax;
    }

    @Override
    public int doStartTag() {
        int min = Math.max(currentPage - 2, 1);
        int max = Math.min(Math.max(currentPage + 2, 5), numPageMax);
        pageContext.setAttribute("min", min);
        pageContext.setAttribute("max", max);

        return SKIP_BODY;
    }

}
