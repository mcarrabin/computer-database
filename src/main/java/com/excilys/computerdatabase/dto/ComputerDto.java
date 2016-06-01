package com.excilys.computerdatabase.dto;

import javax.validation.constraints.NotNull;

public class ComputerDto {
    private String computerId;

    @NotNull
    private String computerName;

    private String discontinued;

    private String introduced;
    private String companyId;
    private String companyName;

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
         * Method wich calls the computerId constructor of the ComputerDto
         * object.
         *
         * @param computerId
         *            is the value of computerId to set.
         * @return the ComputerDtoBuilder updated.
         */
        public ComputerDtoBuilder id(String id) {
            this.computerDto.setComputerId(id);
            return this;
        }

        /**
         * Method which calls the name constructor of the ComputerDto object.
         *
         * @param name
         *            is the value of name to set.
         * @return the updated ComputerDtoBuilder object.
         */
        public ComputerDtoBuilder name(String name) {
            this.computerDto.setComputerName(name);
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

        public ComputerDtoBuilder companyId(String companyId) {
            this.computerDto.setCompanyId(companyId);
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

    public String getComputerId() {
        return computerId;
    }

    public void setComputerId(String id) {
        this.computerId = id;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public static ComputerDtoBuilder getBuilder() {
        return new ComputerDtoBuilder();
    }
}
