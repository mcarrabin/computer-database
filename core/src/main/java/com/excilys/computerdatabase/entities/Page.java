package com.excilys.computerdatabase.entities;

import java.util.List;

public class Page<T> {
    private int currentPage;
    private List<T> elements;
    private long itemsTotalCount;
    private int maxPage;
    private long itemsPerPage;

    private String search;
    private String orderBy;

    private String sorting;

    public static class PageBuilder<T> {
        private Page<T> page;

        /**
         * Constructor of the PageBuilder.
         */
        private PageBuilder() {
            this.page = new Page<T>();
        }

        /**
         * Method which will update the numPage attribute of the current Page
         * object.
         *
         * @param numPage
         *            is the new value to update.
         * @return the PageBuilder object with the numPage attribute updated.
         */
        public PageBuilder<T> currentPage(int currentPage) {
            this.page.setCurrentPage(currentPage);
            return this;
        }

        /**
         * Method which will set the elements attribute of the current page
         * object.
         *
         * @param elements
         *            is a list of elements
         * @return the current PageBuilder with the elements attribute updated.
         */
        public PageBuilder<T> elements(List<T> elements) {
            this.page.setElements(elements);
            return this;
        }

        /**
         * Method that will set the total number of entries in the element table
         * in the DB.
         *
         * @param numElementTotal
         *            is the new total number of entries in the element.
         * @return the current PageBuilder object with the total number of
         *         elements available updated.
         */
        public PageBuilder<T> itemsTotalCount(long itemsTotalCount) {
            this.page.setItemsTotalCount(itemsTotalCount);
            return this;
        }

        /**
         * Method that will set the numPageMax in the current PageBuilder
         * object.
         *
         * @param numPageMax
         *            is the new value.
         * @return the current PageBuilder object with the numPageMax attribute
         *         updated.
         */
        public PageBuilder<T> maxPage(int maxPage) {
            this.page.setMaxPage(maxPage);
            return this;
        }

        /**
         * Method that calls the searchFilter field setter.
         *
         * @param searchFilter
         *            is the new value of the searchFilter field.
         * @return the updated PageBuilder object.
         */
        public PageBuilder<T> search(String search) {
            this.page.setSearch(search);
            return this;
        }

        /**
         * Setter of the elementsCount field of the Page.
         *
         * @param elementsCount
         *            is the new value to update.
         * @return the updated PageBuilder object.
         */
        public PageBuilder<T> itemsPerPage(long itemsPerPage) {
            this.page.setItemsPerPage(itemsPerPage);
            return this;
        }

        /**
         * Setter of the orderByFilter Page atttribute.
         *
         * @param orderByFilter
         *            is the new value to set
         * @return the updated PageBuilder object.
         */
        public PageBuilder<T> orderBy(String orderBy) {
            this.page.setOrderBy(orderBy);
            return this;
        }

        /**
         * Setter of the sorting attribute. The sorting attribute indicates if
         * the sorting applied is asc or desc.
         *
         * @param sorting
         *            is the new value to set.
         * @return the updated PageBuilder object.
         */
        public PageBuilder<T> sorting(String sorting) {
            this.page.setSorting(sorting);
            return this;
        }

        /**
         * Method which will return the Page object of the PageBuilder.
         *
         * @return the Page object linked to the PageBuilder.
         */
        public Page<T> build() {
            return this.page;
        }
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int numPage) {
        this.currentPage = numPage;
    }

    public List<T> getElements() {
        return this.elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }

    public long getItemsTotalCount() {
        return this.itemsTotalCount;
    }

    public void setItemsTotalCount(long itemsTotalCount) {
        this.itemsTotalCount = itemsTotalCount;
    }

    public int getMaxPage() {
        return this.maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public String getSearch() {
        return this.search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public long getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(long itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSorting() {
        return sorting;
    }

    public void setSorting(String sorting) {
        this.sorting = sorting;
    }

    public PageBuilder<T> getBuilder() {
        return new PageBuilder<T>();
    }

    @Override
    public String toString() {
        return "Page [numPage=" + currentPage + ", elements=" + elements + ", numElementTotal=" + itemsTotalCount
                + ", numPageMax=" + maxPage + ", searchFilter=" + search + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((elements == null) ? 0 : elements.hashCode());
        result = prime * result + (int) (itemsTotalCount ^ (itemsTotalCount >>> 32));
        result = prime * result + currentPage;
        result = prime * result + maxPage;
        result = prime * result + ((search == null) ? 0 : search.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Page<T> other = (Page<T>) obj;
        if (elements == null) {
            if (other.elements != null) {
                return false;
            }
        } else if (!elements.equals(other.elements)) {
            return false;
        }
        if (itemsTotalCount != other.itemsTotalCount) {
            return false;
        }
        if (currentPage != other.currentPage) {
            return false;
        }
        if (maxPage != other.maxPage) {
            return false;
        }
        if (search == null) {
            if (other.search != null) {
                return false;
            }
        } else if (!search.equals(other.search)) {
            return false;
        }
        return true;
    }
}
