package com.excilys.computerdatabase.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.exceptions.MapperException;

@Component("companyMapper")
public class CompanyMapper implements Mapper<Company> {

    /**
     * Méthode qui va créer et retourner un objet CompanyEntity complété avec le
     * contenu du resultSet.
     *
     * @param result
     * @return l'objet CompanyEntity créé et complété
     */
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

    /**
     * Méthode qui va boucler sur le result set et créer une liste d'objet
     * Company.
     *
     * @param result
     * @return la liste de Company créé
     */
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
}
