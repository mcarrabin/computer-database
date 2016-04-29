package com.excilys.computerdatabase.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.entities.Company.CompanyBuilder;
import com.excilys.computerdatabase.exceptions.MapperException;

public class CompanyMapper implements Mapper<Company> {
    private static CompanyMapper instance = null;
    private static Logger logger = LoggerFactory.getLogger(CompanyMapper.class);

    /**
     * Méthode qui va retourner une instance de CompanyMapper si aucune n'est
     * présente.
     *
     * @return l'instance de CompanyMapper() actuelle.
     */
    public static CompanyMapper getInstance() {
        if (instance == null) {
            synchronized (CompanyMapper.class) {
                if (instance == null) {
                    instance = new CompanyMapper();
                }
            }
        }
        return instance;
    }

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
            company = new CompanyBuilder().id(id).name(name).build();
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
