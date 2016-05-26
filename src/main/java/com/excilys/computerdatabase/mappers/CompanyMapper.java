package com.excilys.computerdatabase.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.exceptions.MapperException;

@Component("companyMapper")
public class CompanyMapper implements Mapper<Company>, RowMapper<Company> {

    @Override
    public Company mapUnique(ResultSet result) throws MapperException {
        String name;
        Company company = null;
        try {
            name = result.getString("Name");
            Long id = result.getLong("id");
            company = new Company().getBuilder().id(id).name(name).build();
        } catch (SQLException e) {
            throw new MapperException(e);
        }
        return company;
    }

    @Override
    public List<Company> mapAll(ResultSet result) throws MapperException {
        List<Company> companies = new ArrayList<Company>();
        try {
            while (result.next()) {
                companies.add(mapUnique(result));
            }
        } catch (Exception e) {
            throw new MapperException(e);
        }
        return companies;
    }

    @Override
    public Company mapRow(ResultSet rs, int arg1) throws SQLException {
        Company company = new Company().getBuilder().name(rs.getString(2)).id(rs.getLong(1)).build();
        return company;
    }
}
