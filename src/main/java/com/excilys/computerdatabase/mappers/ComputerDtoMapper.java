package com.excilys.computerdatabase.mappers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.dto.ComputerDto.ComputerDtoBuilder;
import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.entities.Computer;

@Component("computerDtoMapper")
public class ComputerDtoMapper implements MapperDto<Computer, ComputerDto> {

    /**
     * Method that will map a Computer object to a ComputerDto object.
     *
     * @param computer
     *            is the Computer objects to map.
     * @return a ComputerDto object.
     */
    @Override
    public ComputerDto toDto(Computer computer) {
        ComputerDto dto = null;
        String dateIntroduced = "";
        String dateDiscontinued = "";
        if (computer.getIntroduced() != null) {
            dateIntroduced = DateMapper.toString(computer.getIntroduced());
        }
        if (computer.getDiscontinued() != null) {
            dateDiscontinued = DateMapper.toString(computer.getDiscontinued());
        }
        String computerId = String.valueOf(computer.getId());
        String companyId = String.valueOf(computer.getCompany().getId());

        dto = new ComputerDtoBuilder().id(computerId).companyName(computer.getCompany().getName()).companyId(companyId)
                .name(computer.getName()).introduced(dateIntroduced).discontinued(dateDiscontinued).build();

        return dto;
    }

    /**
     * Method that will map a ComputerDto object to a Computer object.
     *
     * @param dto
     *            is the ComputerDto objects to map.
     * @return a Computer object.
     */
    @Override
    public Computer fromDto(ComputerDto dto) {
        Computer computer = null;
        Company company = null;
        LocalDate introducedDate = null;
        LocalDate discontinuedDate = null;
        if (dto.getIntroduced() != null) {
            introducedDate = DateMapper.toLocalDate(dto.getIntroduced());
        }
        if (dto.getDiscontinued() != null) {
            discontinuedDate = DateMapper.toLocalDate(dto.getDiscontinued());
        }
        long computerId = Long.parseLong(dto.getComputerId());
        long companyId = Long.parseLong(dto.getCompanyId());

        company = new Company().getBuilder().name(dto.getCompanyName()).id(companyId).build();
        computer = new Computer().getBuilder().id(computerId).name(dto.getComputerName()).introduced(introducedDate)
                .discontinued(discontinuedDate).company(company).build();

        return computer;
    }

    /**
     * Method that will convert the Computer list into a ComputerDto list.
     *
     * @param computers
     *            is the list of Computer objects to convert.
     * @return a list of ComputerDto objects.
     */
    @Override
    public List<ComputerDto> toDtoList(List<Computer> computers) {
        List<ComputerDto> dtos = new ArrayList<>();
        for (Computer c : computers) {
            dtos.add(toDto(c));
        }
        return dtos;
    }

    /**
     * Method that will convert the ComputerDto list into a Computer list.
     *
     * @param dtos
     *            is the list of ComputerDto objects to convert.
     * @return a list of Computer objects.
     */
    @Override
    public List<Computer> fromDtoList(List<ComputerDto> dtos) {
        List<Computer> computers = new ArrayList<>();
        for (ComputerDto c : dtos) {
            computers.add(fromDto(c));
        }
        return computers;
    }

}
