package com.excilys.computerdatabase.entities;

import java.util.ArrayList;
import java.util.List;

public class Page {
    private int nbreLine;
    private int numPage;
    private List<Computer> computers;

    /**
     * Constructor of the Page class.
     */
    public Page() {
        this.nbreLine = 0;
        this.numPage = 0;
        this.computers = new ArrayList<Computer>();
    }

    public static class PageBuilder {
        private Page page;

        /**
         * Constructor of the PageBuilder.
         */
        public PageBuilder() {
            this.page = new Page();
        }

        /**
         * Method which will update the nbreLine attribute of the current Page
         * object.
         *
         * @param nbreLine
         *            is the new value to update.
         * @return the PageBuilder object with the numPage attribute updated.
         */
        public PageBuilder nbreLine(int nbreLine) {
            this.page.setNbreLine(nbreLine);
            return this;
        }

        /**
         * Method which will update the numPage attribute of the current Page
         * object.
         *
         * @param numPage
         *            is the new value to update.
         * @return the PageBuilder object with the numPage attribute updated.
         */
        public PageBuilder numPage(int numPage) {
            this.page.setNumPage(numPage);
            return this;
        }

        /**
         * Method which will set the computers attribute of the current page
         * object.
         *
         * @param computers
         *            is a list of computers
         * @return the current PageBuilder with the computers attribute updated.
         */
        public PageBuilder computers(List<Computer> computers) {
            this.page.setComputers(computers);
            return this;
        }

        /**
         * Méthode which will return the Page object of the PageBuilder.
         *
         * @return the Page object linked to the PageBuilder.
         */
        public Page build() {
            return this.page;
        }
    }

    public int getNbreLine() {
        return nbreLine;
    }

    public void setNbreLine(int nbreLine) {
        this.nbreLine = nbreLine;
    }

    public int getNumPage() {
        return numPage;
    }

    public void setNumPage(int numPage) {
        this.numPage = numPage;
    }

    public List<Computer> getComputers() {
        return computers;
    }

    public void setComputers(List<Computer> computers) {
        this.computers = computers;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Computer c : computers) {
            result.append(c.toString()).append("\n");
        }
        result.append("Page [nbreLine=" + nbreLine + ", numPage=" + numPage + "]");
        return result.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + numPage;
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
        Page other = (Page) obj;
        if (computers == null) {
            if (other.computers != null) {
                return false;
            }
        } else if (!computers.equals(other.computers)) {
            return false;
        }
        if (nbreLine != other.nbreLine) {
            return false;
        }
        if (numPage != other.numPage) {
            return false;
        }
        return true;
    }
}
