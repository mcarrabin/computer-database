package com.excilys.computerdatabase.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.exceptions.MapperException;

/**
 * @author excilys
 *
 */
@Component("computerMapper")
public class ComputerMapper implements Mapper<Computer>, RowMapper<Computer> {
    private static Logger logger = LoggerFactory.getLogger(ComputerMapper.class);

    @Autowired
    @Qualifier("dateMapper")
    private DateMapper dateMapper;

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

            Company company = Company.getBuilder().id(companyId).name(companyName).build();
            computer = Computer.getBuilder().id(id).name(name).introduced(introduced).discontinued(discontinued)
                    .company(company).build();
        } catch (Exception e) {
            throw new MapperException(e);
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
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new MapperException(e);
        }
        return computers;
    }

    @Override
    public Computer mapRow(ResultSet rs, int arg1) throws SQLException {
        Computer computer = null;
        Company company = null;
        String introducedDate = rs.getString(3);
        String discontinuedDate = rs.getString(4);
        LocalDateTime introduced = null;
        LocalDateTime discontinued = null;

        if (introducedDate != null && !introducedDate.trim().isEmpty()) {
            introduced = Timestamp.valueOf(introducedDate).toLocalDateTime();
        }
        if (discontinuedDate != null && !discontinuedDate.trim().isEmpty()) {
            discontinued = Timestamp.valueOf(discontinuedDate).toLocalDateTime();
        }

        company = new Company().getBuilder().name(rs.getString(7)).id(rs.getLong(5)).build();
        computer = new Computer().getBuilder().id(rs.getLong(1)).name(rs.getString(2)).company(company)
                .discontinued(discontinued).introduced(introduced).build();

        return computer;
    }
}
