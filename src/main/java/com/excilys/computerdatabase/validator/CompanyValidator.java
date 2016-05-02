package com.excilys.computerdatabase.validator;

import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.exceptions.ValidatorException;

public enum CompanyValidator {
    INSTANCE;
    /**
     * Method which checks the name value. It must starts with a letter.
     *
     * @param name
     */
    public void isNameValid(String name) {
        if (name.length() == 0 || !name.matches("^[a-zA-Z]")) {
            throw new ValidatorException(
                    "the Company name is empty or not valid (expected: starts with a letter and not empty).");
        }
    }

    /**
     * Method which checks the id value. It must be a positive number.
     *
     * @param id
     */
    public void isIdValid(String id) {
        if (id.matches("\\d*[1-9]\\d*")) {
            throw new ValidatorException("the Company id is not valid (expected: positive number).");
        }
    }

    /**
     * Method which checks the id value. It must be a positive number.
     *
     * @param id
     */
    public void isIdValid(long id) {
        if (id <= 0) {
            throw new ValidatorException("the Company id is not valid (expected: positive number).");
        }
    }

    /**
     * Method that will call the company name and id.
     *
     * @param company
     *            is the company to check.
     * @throws Validator
     *             exception if either the name or id is incorrect.
     */
    public void validateCompany(Company company) {
        isIdValid(company.getId());
        isNameValid(company.getName());
    }

    /**
     * Method that will call the company name and id.
     *
     * @param company
     *            is the company to check.
     * @throws Validator
     *             exception if either the name or id is incorrect.
     */
    public void validateCompany(String id, String name) {
        isIdValid(id);
        isNameValid(name);
    }
}
