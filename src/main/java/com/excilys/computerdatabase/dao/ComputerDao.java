package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Page;
import com.excilys.computerdatabase.exceptions.DaoException;
import com.excilys.computerdatabase.mappers.ComputerMapper;
import com.excilys.computerdatabase.mappers.DateMapper;
import com.zaxxer.hikari.HikariDataSource;

@Repository("computerDao")
public class ComputerDao implements AbstractDao<Computer> {

    @Autowired
    @Qualifier("dateMapper")
    public DateMapper dateMapper;

    @Autowired
    @Qualifier("computerMapper")
    public ComputerMapper computerMapper;

    @Autowired
    @Qualifier("dbManager")
    public DBManager dbManager;

    @Autowired
    @Qualifier("dataSource")
    private HikariDataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    private static final String GET_BY_PAGE_REQUEST = "select * from computer c left join company comp on comp.id = c.company_id";
    private static final String GET_ALL_REQUEST = "select * from computer c left join company comp on comp.id = c.company_id";
    private static final String GET_BY_ID_REQUEST = "select * from computer c left join company comp on comp.id = c.company_id where c.id = ? ";
    private static final String GET_TOTAL_COUNT_REQUEST = "select count(*) as number from computer c left join company comp on comp.id = c.company_id";
    private static final String UPDATE_REQUEST = "update computer set name = ?, introduced = ?, discontinued = ?, company_id = ? where id = ?";
    private static final String DELETE_REQUEST = "delete from computer where id = ? ";
    private static final String DELETE_BY_COMP_REQUEST = "delete from computer where company_id = ?";
    private static final String CREATE_REQUEST = "insert into computer (name, introduced, discontinued, company_id) values (?, ?, ?, ?)";

    private static final String LIKE_REQUEST = " where c.name like ? or comp.name like ? ";

    public void setDataSource(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Method that will return a connection from the dbManager instance.
     *
     * @return a connection to the database.
     */
    public Connection connect() {
        return dbManager.getConnection();
    }

    @Override
    public List<Computer> getAll() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        List<Computer> computers = jdbcTemplate.queryForList(GET_ALL_REQUEST, Computer.class);
        return computers;
    }

    @Override
    public Computer getById(long id) throws DaoException {
        jdbcTemplate = new JdbcTemplate(dataSource);
        Computer computer = jdbcTemplate.queryForObject(GET_BY_ID_REQUEST, new Object[] { id }, new ComputerMapper());
        return computer;
    }

    /**
     * Method that will call the PageSGET_BY_PAGE_REQUESTervice and return the
     * built page.
     *
     * @param nbreLine
     *            (int) wanted in the page.
     * @param numPage
     *            (int) is the index of the page we want to build.
     * @param name
     *            is the name filter in case of a filter is applied.
     * @return the built page.
     * @throws DaoException
     *             which are the exceptions handled by the Dao classes.
     */
    public Page<Computer> getByPage(int nbreLine, int numPage, String search, String orderBy) throws DaoException {
        List<Computer> computers = new ArrayList<Computer>();
        jdbcTemplate = new JdbcTemplate(dataSource);

        String query = GET_BY_PAGE_REQUEST;

        // if there is a search required, add the condition.
        if (search != null && !search.trim().isEmpty()) {
            query += LIKE_REQUEST;
        }

        // if there is a sorting required, add the order by condition.
        if (orderBy != null && !orderBy.trim().isEmpty()) {
            query += " order by " + orderBy;
        }

        query += " limit ?, ?";
        if (search != null && !search.trim().isEmpty()) {
            computers = jdbcTemplate.query(query,
                    new Object[] { "%" + search + "%", "%" + search + "%", nbreLine * (numPage - 1), nbreLine },
                    new ComputerMapper());
        } else {
            computers = jdbcTemplate.query(query, new Object[] { nbreLine * (numPage - 1), nbreLine },
                    new ComputerMapper());
        }
        Page<Computer> page = new Page<Computer>().getBuilder().elements(computers).currentPage(numPage).build();

        return page;
    }

    /**
     * Method that will ask the DB how many entries are in the Computer table
     * with eventually a filter on the name attribute.
     *
     * @param name
     *            is the filter on the name values.
     * @return the number of entries in the Computer table in the DB filtered on
     *         the name attribute.
     * @throws DaoException
     *             if anything goes wrong.
     * @throws ConnexionException
     *             if something went wrong for connection opening of closing.
     */
    public long getNumTotalComputer(String search) throws DaoException {
        long result = 0;
        jdbcTemplate = new JdbcTemplate(dataSource);
        String query = GET_TOTAL_COUNT_REQUEST;

        if (search != null && !search.trim().isEmpty()) {
            query += LIKE_REQUEST;
            result = jdbcTemplate.queryForObject(query, new Object[] { "%" + search + "%", "%" + search + "%" },
                    long.class);
        } else {
            result = jdbcTemplate.queryForObject(query, long.class);
        }

        return result;
    }

    @Override
    public boolean delete(long id) throws DaoException {
        jdbcTemplate = new JdbcTemplate(dataSource);
        boolean isDeleteOk = jdbcTemplate.update(DELETE_REQUEST, id) > 0 ? true : false;

        return isDeleteOk;
    }

    /**
     * Methode qui va lancer la requete de mise a jour dans la BDD en se basant
     * sur l'id pour retrouver l'entree a mettre a jour.
     *
     * @param computer
     *            contient l'ordinnateur avec les nouvelles valeurs.
     * @return true if the computer sent as a parameter has been updated, else
     *         false.
     * @throws DaoException
     *             which are the exceptions handled by the Dao classes.
     * @throws ConnexionException
     *             which are the exceptions due to connexion issues.
     */
    public boolean updateComputer(Computer computer) throws DaoException {
        jdbcTemplate = new JdbcTemplate(dataSource);
        boolean isUpdateOk = jdbcTemplate.update(UPDATE_REQUEST, computer.getName(),
                DateMapper.toTimeStamp(computer.getIntroduced()), DateMapper.toTimeStamp(computer.getDiscontinued()),
                computer.getCompany().getId(), computer.getId()) > 0 ? true : false;

        return isUpdateOk;
    }

    /**
     * Methode appelee pour creer un computer et l'inserer en bdd.
     *
     * @param computer
     *            is the Computer object to create.
     * @return true if the computer has been created in the database else false.
     * @throws DaoException
     *             which are the exceptions handled by the Dao classes.
     * @throws ConnexionException
     *             which are the exceptions due to connexion issues.
     */
    public boolean createComputer(Computer computer) throws DaoException {
        jdbcTemplate = new JdbcTemplate(dataSource);
        Company company = computer.getCompany();
        boolean creationOk = jdbcTemplate.update(CREATE_REQUEST, computer.getName(),
                DateMapper.toTimeStamp(computer.getIntroduced()), DateMapper.toTimeStamp(computer.getDiscontinued()),
                (company == null ? null : company.getId())) > 0 ? true : false;
        return creationOk;
    }

    /**
     * Method that will delete every computers linked to the company identified
     * by the parameter.
     *
     * @param companyId
     *            is the id of the company which every computer to delete are
     *            linked on.
     * @return true if delete went well else false.
     * @throws DaoExecption
     *             delete went wrong or prepared statement failed.
     */
    public boolean deleteByCompany(long companyId) throws DaoException {
        jdbcTemplate = new JdbcTemplate(dataSource);
        boolean isDeleteOk = jdbcTemplate.update(DELETE_BY_COMP_REQUEST, companyId) > 0 ? true : false;

        return isDeleteOk;
    }
}
