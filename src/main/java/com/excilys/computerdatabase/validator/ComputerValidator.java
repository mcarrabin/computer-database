package com.excilys.computerdatabase.validator;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.mappers.DateMapper;

@Component("computerValidator")
public class ComputerValidator implements Validator {
    @Autowired
    @Qualifier("companyValidator")
    private CompanyValidator companyValidator;

    private Map<String, String> errorMessages = new HashMap<String, String>();

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
    public int isNameValid(String name) {
        if (name.trim().isEmpty() || !name.matches("^[a-zA-Z0-9\\ \\-&.]+$")) {
            return -1;
        }
        return 0;
    }

    /**
     * Method which checks the id value. It must be a positive number.
     *
     * @param id
     */
    public void isIdValid(String id) {
        if (!id.matches("^[1-9][0-9]*$")) {
            errorMessages.put("id", "the Computer id is not valid (expected: positive number).");
        }
    }

    /**
     * Method which checks the id value. It must be a positive number.
     *
     * @param id
     */
    public void isIdValid(long id) {
        if (id <= 0) {
            errorMessages.put("id", "the Computer id is not valid (expected: positive number).");
        }
    }

    /**
     * Method that will check the date format.
     *
     * @param date
     *            is the date to verify.
     */
    public void isDateValid(String date, String champ) {
        if (date.trim().length() > 0
                && !date.matches("^(0[1-9]|[12][0-9]|3[01])[-](0[1-9]|1[012])[-](19[7-9][0-9]|20[0-3][0-9])$")) {
            errorMessages.put(champ, "the Computer " + champ + " date is not valid (expected: dd-MM-yyyy).");
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Computer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        ComputerDto computer = (ComputerDto) object;
        LocalDateTime introduced = DateMapper.toLocalDateTime(computer.getIntroduced());
        LocalDateTime discontinued = DateMapper.toLocalDateTime(computer.getDiscontinued());

        if (isNameValid(computer.getComputerName()) == -1) {
            errors.rejectValue("computerName", "name.invalid");
        }
        if (introduced == null && discontinued != null) {
            errors.rejectValue("introduced", "dates.invalid.introduced.null");
        } else if (introduced != null && discontinued != null && discontinued.isBefore(introduced)) {
            errors.rejectValue("introduced", "dates.invalid.before");
        }
    }

}
