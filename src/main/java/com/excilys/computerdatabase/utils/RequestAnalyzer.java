package com.excilys.computerdatabase.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.dto.ComputerDto;

@Component("requestAnalyzer")
public class RequestAnalyzer {
    // INSTANCE;

    /**
     * This method will get the parameter from the request.
     *
     * @param parameterName
     *            is the name of the parameter
     * @param request
     *            is the request received
     * @param defaultValue
     *            is the default value to assign if the request parameter is
     *            null;
     * @return the parameter value if not null, defaultValue if null.
     */
    public String getStringParameter(String parameterName, HttpServletRequest request, String defaultValue) {
        String result;
        result = request.getParameter(parameterName) == null ? defaultValue : request.getParameter(parameterName);
        return result;
    }

    /**
     * Method that will build a ComputerDto using the received parameters.
     *
     * @param id
     *            is the ComputerDto id.
     * @param name
     *            is the ComputerDto name.
     * @param introduced
     *            is the ComputerDto introduced date.
     * @param discontinued
     *            is the ComputerDto discontinued date.
     * @param companyName
     *            is the ComputerDto company name.
     * @param companyId
     *            is the ComputerDto company id.
     * @return the computer Object.
     */
    public ComputerDto getComputerDtoFromParam(String id, String name, String introduced, String discontinued,
            String companyId, String companyName) {
        ComputerDto dto = ComputerDto.getBuilder().id(id).name(name).introduced(introduced).discontinued(discontinued)
                .companyId(companyId).companyName(companyName).build();
        return dto;
    }
}
