package com.excilys.computerdatabase.DAO;

import java.util.ArrayList;

public interface DAO <T>{
	public ArrayList<T> getAll();
	public T getById(Long id);
}
