package com.excilys.computerdatabase.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "computer")
public class Computer {

    @GeneratedValue
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "introduced")
    @Convert(converter = DateConverter.class)
    private LocalDateTime introduced;

    @Column(name = "discontinued")
    @Convert(converter = DateConverter.class)
    private LocalDateTime discontinued;

    @ManyToOne(targetEntity = Company.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    private String name;

    /**
     * Computer class builder.
     */
    public static class ComputerBuilder {
        private Computer computer;

        /**
         * Constructor of the ComputerBuilder class.
         */
        public ComputerBuilder() {
            this.computer = new Computer();
        }

        /**
         * Constructor of the Computer attribute of the ComputerBuilder class.
         *
         * @return the Computer object linked to the ComputerBuilder.
         */
        public Computer build() {
            return computer;
        }

        /**
         * Update the introduced attribute of the current PageBuilder.
         *
         * @param introduced
         *            is the new value of the introduced attribute to udpate.
         * @return the current PageBuilder with an updated introduced attribute.
         */
        public ComputerBuilder introduced(LocalDateTime introduced) {
            this.computer.setIntroduced(introduced);
            return this;
        }

        /**
         * Update the discontinued attribute of the current PageBuilder.
         *
         * @param discontinued
         *            is the new value of the discontinued attribute to udpate.
         * @return the current PageBuilder with an updated discontinued
         *         attribute.
         */
        public ComputerBuilder discontinued(LocalDateTime discontinued) {
            this.computer.setDiscontinued(discontinued);
            return this;
        }

        /**
         * Update the name attribute of the current PageBuilder.
         *
         * @param name
         *            is the new value of the name attribute to udpate.
         * @return the current PageBuilder with an updated name attribute.
         */
        public ComputerBuilder name(String name) {
            this.computer.setName(name);
            return this;
        }

        /**
         * Update the company attribute of the current PageBuilder.
         *
         * @param company
         *            is the new value of the company attribute to udpate.
         * @return the current PageBuilder with an updated company attribute.
         */
        public ComputerBuilder company(Company company) {
            this.computer.setCompany(company);
            return this;
        }

        /**
         * Update the id attribute of the current PageBuilder.
         *
         * @param id
         *            is the new value of the id attribute to udpate.
         * @return the current PageBuilder with an updated id attribute.
         */
        public ComputerBuilder id(long id) {
            this.computer.setId(id);
            return this;
        }
    }

    public long getId() {
        return this.id;
    }

    public LocalDateTime getIntroduced() {
        return this.introduced;
    }

    public LocalDateTime getDiscontinued() {
        return this.discontinued;
    }

    public Company getCompany() {
        return this.company;
    }

    public String getName() {
        return this.name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIntroduced(LocalDateTime introduced) {
        this.introduced = introduced;
    }

    public void setDiscontinued(LocalDateTime discontinued) {
        this.discontinued = discontinued;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method that will instantiate a new ComputerBuilder and return it.
     *
     * @return the ComputerBuilder.
     */
    public static ComputerBuilder getBuilder() {
        return new ComputerBuilder();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (int) (prime * result + id);
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
        Computer other = (Computer) obj;
        if (company == null) {
            if (other.company != null) {
                return false;
            }
        } else if (!company.equals(other.company)) {
            return false;
        }
        if (discontinued == null) {
            if (other.discontinued != null) {
                return false;
            }
        } else if (!discontinued.equals(other.discontinued)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (introduced == null) {
            if (other.introduced != null) {
                return false;
            }
        } else if (!introduced.equals(other.introduced)) {
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
        return "";
        // return "Computer [id=" + id + ", name=" + name + ", introduced=" +
        // introduced + ", discontinued=" + discontinued
        // + "\n\t company=" + company.toString() + "]";
    }
}
