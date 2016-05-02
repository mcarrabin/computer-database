package com.excilys.computerdatabase.entities;

public class Company {
    private long id;
    private String name;

    public static class CompanyBuilder {
        private Company company;

        /**
         * CompanyBuilder constructor which instantiates the Company object of
         * the companyBuilder.
         */
        private CompanyBuilder() {
            this.company = new Company();
        }

        /**
         * Setter of the id field of the CompanyBuilder.
         *
         * @param id
         *            value to set to the id field of the CompanyBuilder object
         * @return a CompanyBuilder object with id attribute updated.
         */
        public CompanyBuilder id(long id) {
            this.company.setId(id);
            return this;
        }

        /**
         * Setter of the name field of the CompanyBuilder.
         *
         * @param name
         *            value to set to name attribute of the CompanyBuilder
         *            object
         * @return a CompanyBuilder object with name attribute updated.
         */
        public CompanyBuilder name(String name) {
            this.company.setName(name);
            return this;
        }

        /**
         * Méthode du builder qui va retourner l'objet Company de celui-ci.
         *
         * @return l'objet Company auquel le builder est lié.
         */
        public Company build() {
            return this.company;
        }
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    private void setId(long id) {
        this.id = id;
    }

    private void setName(String name) {
        this.name = name;
    }

    /**
     * Method that returns a new CompanyBuilder object.
     *
     * @return a new CompanyBuilder.
     */
    public CompanyBuilder getBuilder() {
        return new CompanyBuilder();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (int) (prime * result + id);
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Company other = (Company) obj;
        if (id != other.id) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Company [id=" + id + ", name=" + name + "]";
    }
}
