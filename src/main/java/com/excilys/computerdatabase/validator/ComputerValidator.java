package com.excilys.computerdatabase.validator;

import java.time.LocalDateTime;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.exceptions.ValidatorException;
import com.excilys.computerdatabase.mappers.DateMapper;

public enum ComputerValidator {
    INSTANCE;
    /**
     * Method which checks the name value. It must starts with a letter.
     *
     * @param name
     */
    public void isNameValid(String name) {
        if (name.length() == 0 || !name.matches("^[a-zA-Z]")) {
            throw new ValidatorException(
                    "the Computer name is empty or not valid (expected: starts with a letter and not empty).");
        }
    }

    /**
     * Method which checks the id value. It must be a positive number.
     *
     * @param id
     */
    public void isIdValid(String id) {
        if (id.matches("^[1-9][0-9]*$")) {
            throw new ValidatorException("the Computer id is not valid (expected: positive number).");
        }
    }

    /**
     * Method which checks the id value. It must be a positive number.
     *
     * @param id
     */
    public void isIdValid(long id) {
        if (id <= 0) {
            throw new ValidatorException("the Computer id is not valid (expected: positive number).");
        }
    }

    /**
     * Method that will check the date format.
     *
     * @param date
     *            is the date to verify.
     */
    public void isDateValid(String date) {
        if (!date.matches("^(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/](19[7-9][0-9]|20[0-3][0-9])$")) {
            throw new ValidatorException("the Computer introduced date is not valid (expected: dd/MM/yyyy).");
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
    public void areDatesOk(LocalDateTime introduced, LocalDateTime discontinued) {
        if (introduced == null && discontinued != null) {
            throw new ValidatorException("the Computer discontinued date must be null if introduced date is.");
        } else if (discontinued.isBefore(introduced)) {
            throw new ValidatorException("the Computer discontinued date must be after the introduced date.");
        }

    }

    /**
     * Method that will check every attributes of the ComputerDto object.
     *
     * @param computer
     *            is the object to verify.
     * @throws a
     *             ValidatorException is something went wrong.
     */
    public void validateComputer(Computer computer) {
        if (computer.getCompany() != null) {
            CompanyValidator.INSTANCE.validateCompany(computer.getCompany());
        }
        isNameValid(computer.getName());
        isIdValid(computer.getId());
        areDatesOk(computer.getIntroduced(), computer.getDiscontinued());
    }

    /**
     * Method that will check every attributes of the ComputerDto object.
     *
     * @param computer
     *            is the object to verify.
     * @throws a
     *             ValidatorException is something went wrong.
     */
    public void validateComputerDto(ComputerDto computer) {
        if (computer.getCompanyId() != null) {
            CompanyValidator.INSTANCE.validateCompany(computer.getCompanyId(), computer.getCompanyName());
        }
        isNameValid(computer.getName());
        isIdValid(computer.getId());

        isDateValid(computer.getIntroduced());
        isDateValid(computer.getDiscontinued());
        LocalDateTime introduced = DateMapper.toLocalDateTime(computer.getIntroduced());
        LocalDateTime discontinued = DateMapper.toLocalDateTime(computer.getDiscontinued());

        areDatesOk(introduced, discontinued);
    }
}
