package com.excilys.computerdatabase.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Page;
import com.excilys.computerdatabase.entities.Page.PageBuilder;
import com.excilys.computerdatabase.exceptions.ConnexionException;
import com.excilys.computerdatabase.exceptions.DaoException;
import com.excilys.computerdatabase.mappers.ComputerMapper;

public class ComputerDao extends AbstractDao<Computer> {
	private static ComputerDao instance = null;
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ComputerDao.class);

	public ComputerDao() {};

	synchronized public static ComputerDao getInstance() {
		if (instance == null) {
			instance = new ComputerDao();
		}
		return instance;
	}

	/**
	 * Méthode qui va construire une liste de toutes les entrées computer
	 * contenues en BDD.
	 * 
	 * @return la liste de toutes ces ArrayList<ComputerEntity> computers = new
	 *         ArrayList<ComputerEntity>(); entrées
	 */

	@Override
	public List<Computer> getAll() throws DaoException, ConnexionException {
		List<Computer> computers = new ArrayList<Computer>();
		ResultSet result = null;
		String query = "select * from computer c left join company comp on comp.id = c.company_id order by c.name";

		try {
			PreparedStatement statement = this.connect().prepareStatement(query);
			result = statement.executeQuery();

			ComputerMapper cm = ComputerMapper.getInstance();
			computers = cm.mapAll(result);
			statement.close();
			result.close();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException("Erreur lors de la récupération des objets Computer");
		} finally {
			this.closeConnection();
		}

		return computers;
	}

	/**
	 * Méthode qui va récupérer une entree de la table computer en se basant sur
	 * l'id passé en paramètre
	 * 
	 * @param id:
	 *            id de l'ordinateur à récupérer
	 * @return l'ordinateur récupéré en BDD
	 */
	@Override
	public Computer getById(long id) throws DaoException, ConnexionException {
		String query = "select * from computer c left join company comp on comp.id = c.company_id where c.id = ? ";
		ResultSet result = null;
		Computer computer = null;

		try {
			PreparedStatement statement = this.connect().prepareStatement(query);
			statement.setLong(1, id);
			result = statement.executeQuery();
			if (result.next()) {
				ComputerMapper cm = ComputerMapper.getInstance();
				computer = cm.mapUnique(result);
				statement.close();
				result.close();
			}
			statement.close();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException("Erreur lors de la récupération d'un objet Computer via id");
		} finally {
			this.closeConnection();
		}
		return computer;
	}
	
	public Page getByPage(int nbreLine, int numPage) throws DaoException, ConnexionException {
		List<Computer> computers = new ArrayList<Computer>();
		ResultSet result = null;
		Page page = null;
		String query = "select * from computer c left join company comp on comp.id = c.company_id order by c.name limit ?, ?";

		try {
			PreparedStatement statement = this.connect().prepareStatement(query);
			statement.setInt(1, nbreLine * numPage +1);
			statement.setInt(2, nbreLine);
			result = statement.executeQuery();

			ComputerMapper cm = ComputerMapper.getInstance();
			computers = cm.mapAll(result);
			page = new PageBuilder().computers(computers).nbreLine(nbreLine).numPage(numPage).build();
			statement.close();
			result.close();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException("Erreur lors de la récupération des objets Computer pour la pagination");
		} finally {
			this.closeConnection();
		}

		return page;
	}

	/**
	 * Méthode qui va supprimer l'entree dont l'id correspond avec l'id de
	 * l'objet passé en paramètre
	 * 
	 * @param computer
	 *            objet à supprimer dans la bdd.
	 */
	public boolean deleteComputer(Computer computer) throws DaoException, ConnexionException {
		boolean isDeleteOk = false;
		int response = 0;
		String query = "delete from computer where id = ? ;";

		try {
			PreparedStatement statement = this.connect().prepareStatement(query);
			statement.setLong(1, computer.getId());
			response = statement.executeUpdate();
			if (response == 1) {
				isDeleteOk = true;
			}
			statement.close();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException("Erreur lors de la suppression d'un objet Computer");
		} finally {
			this.closeConnection();
		}

		return isDeleteOk;
	}

	/**
	 * Méthode qui va lancer la requête de mise à jour dans la BDD en se basant
	 * sur l'id pour retrouver l'entrée à mettre à jour
	 * 
	 * @param computer
	 *            contient l'ordinnateur avec les nouvelles valeurs.
	 */
	public boolean updateComputer(Computer computer) throws DaoException, ConnexionException {
		boolean isUpdateOk = false;
		int response = 0;
		String query = "update computer set name = ?, introduced = ?, discontinued = ?, company_id = ? where id = ?";

		try {
			PreparedStatement statement = this.connect().prepareStatement(query);
			statement.setString(1, computer.getName());
			if (computer.getIntroduced() != null)
				statement.setTimestamp(2, Timestamp.valueOf(computer.getIntroduced()));
			else
				statement.setNull(2, java.sql.Types.TIMESTAMP);
			if (computer.getDiscontinued() != null) {
				statement.setTimestamp(3, Timestamp.valueOf(computer.getDiscontinued()));
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
			LOGGER.error(e.getMessage());
			throw new DaoException("Erreur lors de la mise à jour d'un objet Computer");
		} finally {
			this.closeConnection();
		}

		return isUpdateOk;
	}

	/**
	 * Méthode appelée pour créer un computer et l'insérer en bdd.
	 */
	public boolean createComputer(Computer computer) throws DaoException, ConnexionException {
		String query = "insert into computer (name, introduced, discontinued, company_id) values (?, ?, ?, ?)";
		boolean creationOk = false;
		int result;
		try {
			PreparedStatement statement = this.connect().prepareStatement(query);
			statement.setString(1, computer.getName());
			if (computer.getIntroduced() != null)
				statement.setTimestamp(2, Timestamp.valueOf(computer.getIntroduced()));
			else
				statement.setNull(2, java.sql.Types.TIMESTAMP);
			if (computer.getDiscontinued() != null) {
				statement.setTimestamp(3, Timestamp.valueOf(computer.getDiscontinued()));
			} else {
				statement.setNull(3, java.sql.Types.TIMESTAMP);
			}

			statement.setLong(4, computer.getCompany().getId());
			result = statement.executeUpdate();
			if (result == 1) {
				creationOk = true;
			}
			statement.close();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException("Erreur lors de la création d'un objet Computer");
		} finally {
			this.closeConnection();
		}
		return creationOk;
	}
}
