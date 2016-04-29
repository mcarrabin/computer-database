package com.excilys.computerdatabase.dto;

public class ComputerDto {
    private String name;
    private String introduced;
    private String discontinued;
    private String companyName;

    /**
     * Constructor of the ComputerDto class.
     */
    public ComputerDto() {
        this.name = "";
        this.introduced = "";
        this.discontinued = "";
        this.companyName = "";
    }

    /**
     * Builder of the ComputerDto object.
     *
     * @author excilys
     *
     */
    public static class ComputerDtoBuilder {
        private ComputerDto computerDto;

        /**
         * Public constructor of the ComputerDtoBuilder.
         */
        public ComputerDtoBuilder() {
            computerDto = new ComputerDto();
        }

        /**
         * Method which calls the name constructor of the ComputerDto object.
         *
         * @param name
         *            is the value of name to set.
         * @return the updated ComputerDtoBuilder object.
         */
        public ComputerDtoBuilder name(String name) {
            this.computerDto.setName(name);
            return this;
        }

        /**
         * Method which calls the introduced constructor of the ComputerDto
         * object.
         *
         * @param introduced
         *            is the value of name to set.
         * @return the updated ComputerDtoBuilder object.
         */
        public ComputerDtoBuilder introduced(String introduced) {
            this.computerDto.setIntroduced(introduced);
            return this;
        }

        /**
         * Method which calls the discontinued constructor of the ComputerDto
         * object.
         *
         * @param discontinued
         *            is the value of name to set.
         * @return the updated ComputerDtoBuilder object.
         */
        public ComputerDtoBuilder discontinued(String discontinued) {
            this.computerDto.setDiscontinued(discontinued);
            return this;
        }

        /**
         * Method which calls the companyName constructor of the ComputerDto
         * object.
         *
         * @param companyName
         *            is the value of name to set.
         * @return the updated ComputerDtoBuilder object.
         */
        public ComputerDtoBuilder companyName(String companyName) {
            this.computerDto.setCompanyName(companyName);
            return this;
        }

        /**
         * Method which returns the ComputerDto object of the current
         * ComputerDtoBuilder.
         *
         * @return the the ComputerDto object of the current ComputerDtoBuilder.
         */
        public ComputerDto build() {
            return this.computerDto;
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduced() {
        return introduced;
    }

    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }

    public String getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(String discontinued) {
        this.discontinued = discontinued;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
