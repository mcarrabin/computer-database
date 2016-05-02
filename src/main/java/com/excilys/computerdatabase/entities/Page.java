package com.excilys.computerdatabase.entities;

import java.util.List;

public class Page<T> {
    // currentItem
    private int numPage;
    private List<T> elements;
    // totalCount itemsCount
    private long numElementTotal;
    // pageCount
    private int numPageMax;
    private long itemsPerPage;

    private String searchFilter;

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
        public PageBuilder<T> numPage(int numPage) {
            this.page.setNumPage(numPage);
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
        public PageBuilder<T> numElementTotal(long numElementTotal) {
            this.page.setNumElementTotal(numElementTotal);
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
        public PageBuilder<T> numPageMax(int numPageMax) {
            this.page.setNumPageMax(numPageMax);
            return this;
        }

        /**
         * Method that calls the searchFilter field setter.
         *
         * @param searchFilter
         *            is the new value of the searchFilter field.
         * @return the updated PageBuilder object.
         */
        public PageBuilder<T> searchFilter(String searchFilter) {
            this.page.setSearchFilter(searchFilter);
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
         * Method which will return the Page object of the PageBuilder.
         *
         * @return the Page object linked to the PageBuilder.
         */
        public Page<T> build() {
            return this.page;
        }
    }

    public int getNumPage() {
        return this.numPage;
    }

    public void setNumPage(int numPage) {
        this.numPage = numPage;
    }

    public List<T> getElements() {
        return this.elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }

    public long getNumElementTotal() {
        return this.numElementTotal;
    }

    public void setNumElementTotal(long numElementTotal) {
        this.numElementTotal = numElementTotal;
    }

    public int getNumPageMax() {
        return this.numPageMax;
    }

    public void setNumPageMax(int numPageMax) {
        this.numPageMax = numPageMax;
    }

    public String getSearchFilter() {
        return this.searchFilter;
    }

    public void setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
    }

    public long getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(long itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public PageBuilder<T> getBuilder() {
        return new PageBuilder<T>();
    }

    @Override
    public String toString() {
        return "Page [numPage=" + numPage + ", elements=" + elements + ", numElementTotal=" + numElementTotal
                + ", numPageMax=" + numPageMax + ", searchFilter=" + searchFilter + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((elements == null) ? 0 : elements.hashCode());
        result = prime * result + (int) (numElementTotal ^ (numElementTotal >>> 32));
        result = prime * result + numPage;
        result = prime * result + numPageMax;
        result = prime * result + ((searchFilter == null) ? 0 : searchFilter.hashCode());
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
        if (numElementTotal != other.numElementTotal) {
            return false;
        }
        if (numPage != other.numPage) {
            return false;
        }
        if (numPageMax != other.numPageMax) {
            return false;
        }
        if (searchFilter == null) {
            if (other.searchFilter != null) {
                return false;
            }
        } else if (!searchFilter.equals(other.searchFilter)) {
            return false;
        }
        return true;
    }
}
