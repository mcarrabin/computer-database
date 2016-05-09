package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Page;
import com.excilys.computerdatabase.exceptions.DaoException;
import com.excilys.computerdatabase.mappers.ComputerMapper;

public enum ComputerDao implements AbstractDao<Computer> {
    INSTANCE;

    private static final ComputerMapper COMPUTER_MAPPER = ComputerMapper.INSTANCE;
    private static final DBManager DB_MANAGER = DBManager.INSTANCE;

    private static final String GET_BY_PAGE_REQUEST = "select * from computer c left join company comp on comp.id = c.company_id";
    private static final String GET_ALL_REQUEST = "select * from computer c left join company comp on comp.id = c.company_id";
    private static final String GET_BY_ID_REQUEST = "select * from computer c left join company comp on comp.id = c.company_id where c.id = ? ";
    private static final String GET_TOTAL_COUNT_REQUEST = "select count(*) as number from computer c left join company comp on comp.id = c.company_id";
    private static final String UPDATE_REQUEST = "update computer set name = ?, introduced = ?, discontinued = ?, company_id = ? where id = ?";
    private static final String DELETE_REQUEST = "delete from computer where id = ? ";
    private static final String DELETE_BY_COMP_REQUEST = "delete from computer where company_id = ?";
    private static final String CREATE_REQUEST = "insert into computer (name, introduced, discontinued, company_id) values (?, ?, ?, ?)";

    private static final String LIKE_REQUEST = "where c.name like ? or comp.name like ? ";

    /**
     * Méthode qui va construire une liste de toutes les entrées computer
     * contenues en BDD.
     *
     * @return la liste de toutes ces ArrayList<ComputerEntity> computers = new
     *         ArrayList<ComputerEntity>(); entrées
     */
    @Override
    public List<Computer> getAll() {
        List<Computer> computers = new ArrayList<Computer>();
        ResultSet result = null;
        Connection con = DB_MANAGER.getConnection();

        try {
            PreparedStatement statement = con.prepareStatement(GET_ALL_REQUEST);
            result = statement.executeQuery();

            computers = COMPUTER_MAPPER.mapAll(result);
            statement.close();
            result.close();
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            DB_MANAGER.closeConnection();
        }

        return computers;
    }

    /**
     * Méthode qui va récupérer une entree de la table computer en se basant sur
     * l'id passé en paramètre.
     *
     * @param id
     *            id de l'ordinateur à récupérer
     * @return l'ordinateur récupéré en BDD
     */
    @Override
    public Computer getById(long id) throws DaoException {
        ResultSet result = null;
        Computer computer = null;
        Connection con = DB_MANAGER.getConnection();

        try {
            PreparedStatement statement = con.prepareStatement(GET_BY_ID_REQUEST);
            statement.setLong(1, id);
            result = statement.executeQuery();
            if (result.next()) {
                computer = COMPUTER_MAPPER.mapUnique(result);
                statement.close();
                result.close();
            }
            statement.close();
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            DB_MANAGER.closeConnection();
        }
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
        ResultSet result = null;
        Page<Computer> page = null;
        Connection con = DB_MANAGER.getConnection();
        String query = GET_BY_PAGE_REQUEST;

        // if there is a search required, add the condition.
        if (!search.trim().isEmpty()) {
            query += LIKE_REQUEST;
        }

        // if there is a sorting required, add the order by condition.
        if (orderBy.trim().length() > 0) {
            query += " order by " + orderBy;
        }

        query += " limit ?, ?";

        try {
            PreparedStatement statement = con.prepareStatement(query);
            if (!search.trim().isEmpty()) {
                statement.setString(1, "%" + search + "%");
                statement.setString(2, "%" + search + "%");
                statement.setInt(3, nbreLine * (numPage - 1));
                statement.setInt(4, nbreLine);
            } else {
                statement.setInt(1, nbreLine * (numPage - 1));
                statement.setInt(2, nbreLine);
            }

            result = statement.executeQuery();

            computers = COMPUTER_MAPPER.mapAll(result);
            page = new Page<Computer>().getBuilder().elements(computers).currentPage(numPage).build();
            statement.close();
            result.close();
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            DB_MANAGER.closeConnection();
        }

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
        ResultSet res = null;
        Connection con = DB_MANAGER.getConnection();
        String query = GET_TOTAL_COUNT_REQUEST;
        try {
            PreparedStatement statement = con.prepareStatement(GET_TOTAL_COUNT_REQUEST);
            if (!search.trim().isEmpty()) {
                query += LIKE_REQUEST;
                statement.setString(1, "%" + search + "%");
                statement.setString(2, "%" + search + "%");
            }
            res = statement.executeQuery();
            if (res.next()) {
                result = res.getLong("number");
            }
            statement.close();
            res.close();
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            DB_MANAGER.closeConnection();
        }

        return result;
    }

    /**
     * Méthode qui va supprimer l'entree dont l'id correspond avec l'id de
     * l'objet passé en paramètre.
     *
     * @param computer
     *            objet à supprimer dans la bdd.
     * @return true if the computer is deleted, else false.
     * @throws DaoException
     *             which are the exceptions handled by the Dao classes.
     * @throws ConnexionException
     *             which are the exceptions due to connexion issues.
     */

    @Override
    public boolean delete(long id) throws DaoException {
        boolean isDeleteOk = false;
        Connection con = DB_MANAGER.getConnection();
        int response = 0;
        // Connection con = INSTANCE.connect();

        try {
            PreparedStatement statement = con.prepareStatement(DELETE_REQUEST);
            statement.setLong(1, id);
            response = statement.executeUpdate();
            if (response == 1) {
                isDeleteOk = true;
            }
            statement.close();
        } catch (Exception e) {
            throw new DaoException(e);
        } // finally {
          // INSTANCE.closeConnection(con);
          // }

        return isDeleteOk;
    }

    /**
     * Méthode qui va lancer la requête de mise à jour dans la BDD en se basant
     * sur l'id pour retrouver l'entrée à mettre à jour.
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
        boolean isUpdateOk = false;
        int response = 0;
        Connection con = DB_MANAGER.getConnection();

        try {
            PreparedStatement statement = con.prepareStatement(UPDATE_REQUEST);
            statement.setString(1, computer.getName());
            if (computer.getIntroduced() != null) {
                statement.setTimestamp(2, Timestamp.valueOf(computer.getIntroduced().atStartOfDay()));
            } else {
                statement.setNull(2, java.sql.Types.TIMESTAMP);
            }
            if (computer.getDiscontinued() != null) {
                statement.setTimestamp(3, Timestamp.valueOf(computer.getDiscontinued().atStartOfDay()));
            } else {
                statement.setNull(3, java.sql.Types.TIMESTAMP);
            }
            statement.setLong(4, computer.getCompany().getId());
            statement.setLong(5, computer.getId());
            response = statement.executeUpdate();
            if (response == 1) {
                isUpdateOk = true;
            }
            statement.close();
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            DB_MANAGER.closeConnection();
        }

        return isUpdateOk;
    }

    /**
     * Méthode appelée pour créer un computer et l'insérer en bdd.
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
        boolean creationOk = false;
        int result;
        Connection con = DB_MANAGER.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(CREATE_REQUEST);
            statement.setString(1, computer.getName());
            if (computer.getIntroduced() != null) {
                statement.setTimestamp(2, Timestamp.valueOf(computer.getIntroduced().atStartOfDay()));
            } else {
                statement.setNull(2, java.sql.Types.TIMESTAMP);
            }
            if (computer.getDiscontinued() != null) {
                statement.setTimestamp(3, Timestamp.valueOf(computer.getDiscontinued().atStartOfDay()));
            } else {
                statement.setNull(3, java.sql.Types.TIMESTAMP);
            }

            if (computer.getCompany() == null) {
                statement.setNull(4, java.sql.Types.BIGINT);
            } else {
                statement.setLong(4, computer.getCompany().getId());
            }
            result = statement.executeUpdate();
            if (result == 1) {
                creationOk = true;
            }
            statement.close();
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            DB_MANAGER.closeConnection();
        }
        return creationOk;
    }

    /**
     * Method that will delete every computers linked to the company identified
     * by the parameter.
     *
     * @param companyId
     *            is the id of the company which every computer to delete are
     *            linked on.
     * @throws DaoExecption
     *             delete went wrong or prepared statement failed.
     */
    public void deleteByCompany(long companyId) throws DaoException {
        int response = 0;
        Connection con = DB_MANAGER.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(DELETE_BY_COMP_REQUEST);
            statement.setLong(1, companyId);
            response = statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
