package com.excilys.computerdatabase.validator;

import java.util.HashMap;
import java.util.Map;

import com.excilys.computerdatabase.entities.Company;

public enum CompanyValidator {
    INSTANCE;
    private Map<String, String> errorMessages = new HashMap<>();

    public Map<String, String> getErrorMessages() {
        return this.errorMessages;
    }

    public void setErrorMessage(Map<String, String> message) {
        this.errorMessages = message;
    }

    /**
     * Method which checks the name value. It must starts with a letter.
     *
     * @param name
     */
    public void isNameValid(String name) {
        if (name.length() == 0 || !name.matches("^[a-zA-Z0-9\\ &.\\-]+$")) {
            errorMessages.put("companyName",
                    "the Company name is empty or not valid (expected: starts with a letter and not empty).");
        }
    }

    /**
     * Method which checks the id value. It must be a positive number.
     *
     * @param id
     */
    public void isIdValid(String id) {
        if (!id.matches("^[1-9][0-9]*$")) {
            errorMessages.put("companyId", "the Company id is not valid (expected: positive number).");
        }
    }

    /**
     * Method which checks the id value. It must be a positive number.
     *
     * @param id
     */
    public void isIdValid(long id) {
        if (id <= 0) {
            errorMessages.put("companyId", "the Company id is not valid (expected: positive number).");
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
    public Map<String, String> validateCompany(Company company) {
        isIdValid(company.getId());
        isNameValid(company.getName());

        return errorMessages;
    }

    /**
     * Method that will call the company name and id.
     *
     * @param company
     *            is the company to check.
     * @throws Validator
     *             exception if either the name or id is incorrect.
     */
    public Map<String, String> validateCompany(String id, String name) {
        isIdValid(id);
        isNameValid(name);

        return errorMessages;
    }
}
