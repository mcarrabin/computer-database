package com.excilys.computerdatabase.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.entities.Company.CompanyBuilder;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Computer.ComputerBuilder;
import com.excilys.computerdatabase.exceptions.MapperException;

/**
 * @author excilys
 *
 */
public class ComputerMapper implements Mapper<Computer> {
    private static ComputerMapper instance = null;
    private static Logger logger = LoggerFactory.getLogger(ComputerMapper.class);

    /**
     * Méthode qui va vérifier la présence d'une instance de ComputerMapper et
     * en retourner une. Si pas d'instance, en créer une.
     *
     * @return l'instance ou cours ou l'instance juste créée.
     */
    public static ComputerMapper getInstance() {
        if (instance == null) {
            synchronized (ComputerMapper.class) {
                if (instance == null) {
                    instance = new ComputerMapper();
                }
            }
        }
        return instance;
    }

    /**
     * Méthode qui va créer et retourner un objet complété avec le contenu du
     * resultSet.
     *
     * @param result
     * @return l'objet créé et complété
     */
    @Override
    public Computer mapUnique(ResultSet result) throws MapperException {
        LocalDateTime introduced, discontinued;
        Long companyId, id;
        Computer computer = null;
        try {
            String name = result.getString("c.name");
            introduced = result.getTimestamp("c.introduced") == null ? null
                    : result.getTimestamp("introduced").toLocalDateTime();
            discontinued = result.getTimestamp("c.discontinued") == null ? null
                    : result.getTimestamp("discontinued").toLocalDateTime();
            companyId = result.getLong("comp.id");
            String companyName = result.getString("comp.name");
            id = result.getLong("id");

            Company company = new CompanyBuilder().id(companyId).name(companyName).build();
            computer = new ComputerBuilder().id(id).name(name).introduced(introduced).discontinued(discontinued)
                    .company(company).build();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new MapperException("Erreur lors du mapping d'un objet Computer");
        }

        return computer;
    }

    @Override
    public List<Computer> mapAll(ResultSet result) throws MapperException {
        List<Computer> computers = new ArrayList<Computer>();

        try {
            while (result.next()) {
                computers.add(mapUnique(result));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new MapperException("Erreur lors du mapping des objets Computer");
        }
        return computers;
    }
}
