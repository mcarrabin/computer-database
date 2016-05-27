package com.excilys.computerdatabase.validator;

import java.time.LocalDate;
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

    /**
     * Method that will verify that introduced and discontinued are coherent.
     *
     * @param introduced
     *            is the introduced date.
     * @param discontinued
     *            is the discontinued date.
     */
    // public String areDatesOk(LocalDate introduced, LocalDate discontinued) {
    // if (introduced == null && discontinued != null) {
    // return "the Computer discontinued date must be null if introduced date
    // is.";
    // } else if (introduced != null && discontinued != null &&
    // discontinued.isBefore(introduced)) {
    // return "the Computer discontinued date must be after the introduced
    // date.";
    // }
    // return null;
    //
    // }

    /**
     * Method that will check every attributes of the ComputerDto object.
     *
     * @param computer
     *            is the object to verify.
     * @throws a
     *             ValidatorException is something went wrong.
     */
    // public void validateComputer(Computer computer) {
    // if (computer.getCompany() != null) {
    // companyValidator.validateCompany(computer.getCompany());
    // }
    // isNameValid(computer.getName());
    // areDatesOk(computer.getIntroduced(), computer.getDiscontinued());
    // }

    /**
     * Method that will check every attributes of the ComputerDto object.
     *
     * @param computer
     *            is the object to verify.
     * @throws a
     *             ValidatorException is something went wrong.
     */
    // public Map<String, String> validate(ComputerDto computer) {
    // Map<String, String> companyErrors = new HashMap<>();
    // if (computer.getCompanyId() != null) {
    // companyErrors = companyValidator.validateCompany(computer.getCompanyId(),
    // computer.getCompanyName());
    // }
    // errorMessages.putAll(companyErrors);
    //
    // isNameValid(computer.getComputerName());
    // isIdValid(computer.getComputerId());
    //
    // isDateValid(computer.getIntroduced(), "introduced");
    // isDateValid(computer.getDiscontinued(), "discontinued");
    // LocalDate introduced = DateMapper.toLocalDate(computer.getIntroduced());
    // LocalDate discontinued =
    // DateMapper.toLocalDate(computer.getDiscontinued());
    //
    // areDatesOk(introduced, discontinued);
    //
    // return errorMessages;
    // }

    /**
     * Method that will check every attributes of the ComputerDto object.
     *
     * @param computer
     *            is the object to verify.
     * @throws a
     *             ValidatorException is something went wrong.
     */
    // public Map<String, String> validate(String name, String pIntroduced,
    // String pDiscontinued) {
    // // isNameValid(name);
    //
    // isDateValid(pIntroduced, "introduced");
    // isDateValid(pDiscontinued, "discontinued");
    // LocalDate introduced = DateMapper.toLocalDate(pIntroduced);
    // LocalDate discontinued = DateMapper.toLocalDate(pDiscontinued);
    //
    // areDatesOk(introduced, discontinued);
    //
    // return errorMessages;
    // }

    @Override
    public boolean supports(Class<?> clazz) {
        return Computer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        ComputerDto computer = (ComputerDto) object;
        LocalDate introduced = DateMapper.toLocalDate(computer.getIntroduced());
        LocalDate discontinued = DateMapper.toLocalDate(computer.getDiscontinued());

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
