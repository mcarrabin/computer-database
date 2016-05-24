package com.excilys.computerdatabase.mappers;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.entities.Company.CompanyBuilder;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Computer.ComputerBuilder;
import com.excilys.computerdatabase.exceptions.MapperException;

/**
 * @author excilys
 *
 */
@Component("computerMapper")
public class ComputerMapper implements Mapper<Computer> {
    private static Logger logger = LoggerFactory.getLogger(ComputerMapper.class);

    /**
     * Méthode qui va créer et retourner un objet complété avec le contenu du
     * resultSet.
     *
     * @param result
     *            is the resultSet Line to parse into a Computer object.
     *
     * @return The Computer object built base on the ResultSet received as a
     *         parameter.
     */
    @Override
    public Computer mapUnique(ResultSet result) throws MapperException {
        LocalDate introduced, discontinued;
        Long companyId, id;
        Computer computer = null;
        try {
            String name = result.getString("c.name");
            introduced = result.getTimestamp("c.introduced") == null ? null
                    : result.getTimestamp("introduced").toLocalDateTime().toLocalDate();
            discontinued = result.getTimestamp("c.discontinued") == null ? null
                    : result.getTimestamp("discontinued").toLocalDateTime().toLocalDate();
            companyId = result.getLong("comp.id");
            String companyName = result.getString("comp.name");
            id = result.getLong("id");

            Company company = new CompanyBuilder().id(companyId).name(companyName).build();
            computer = new ComputerBuilder().id(id).name(name).introduced(introduced).discontinued(discontinued)
                    .company(company).build();
        } catch (Exception e) {
            throw new MapperException(e);
        }

        return computer;
    }

    /**
     * Method that will parse a result set into a Computer List.
     *
     * @param result
     *            is the ResultSet to parse.
     *
     * @return a list of Computer objects.
     */
    @Override
    public List<Computer> mapAll(ResultSet result) throws MapperException {
        List<Computer> computers = new ArrayList<Computer>();

        try {
            while (result.next()) {
                computers.add(mapUnique(result));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new MapperException(e);
        }
        return computers;
    }
}
