package com.excilys.computerdatabase.mappers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.dto.ComputerDto.ComputerDtoBuilder;
import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.entities.Company.CompanyBuilder;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Computer.ComputerBuilder;

public class ComputerDtoMapper implements MapperDto<Computer, ComputerDto> {
    private static ComputerDtoMapper instance = null;

    /**
     * Constructor of the ComputerDtoMapper class.
     */
    public ComputerDtoMapper() {
    }

    /**
     * Méthode qui va vérifier la présence d'une instance de ComputerMapper et
     * en retourner une. Si pas d'instance, en créer une.
     *
     * @return l'instance ou cours ou l'instance juste créée.
     */
    public static ComputerDtoMapper getInstance() {
        if (instance == null) {
            synchronized (ComputerDtoMapper.class) {
                if (instance == null) {
                    instance = new ComputerDtoMapper();
                }
            }
        }
        return instance;
    }

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
        String dateIntroduced = computer.getIntroduced() == null ? "" : DateMapper.toString(computer.getIntroduced());
        String dateDiscontinued = computer.getDiscontinued() == null ? ""
                : DateMapper.toString(computer.getDiscontinued());

        dto = new ComputerDtoBuilder().companyName(computer.getCompany().getName()).name(computer.getName())
                .introduced(dateIntroduced).discontinued(dateDiscontinued).build();

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
        LocalDateTime introducedDate = DateMapper.toLocalDateTime(dto.getIntroduced());
        LocalDateTime discontinuedDate = DateMapper.toLocalDateTime(dto.getDiscontinued());

        company = new CompanyBuilder().name(dto.getCompanyName()).id(-1).build();
        computer = new ComputerBuilder().company(company).name(dto.getName()).introduced(introducedDate)
                .discontinued(discontinuedDate).build();

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
