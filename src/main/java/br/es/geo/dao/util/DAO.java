package br.es.geo.dao.util;

import java.sql.SQLException;
import java.util.List;

public interface DAO <T> {

	T create();
	long insert ( T obj ) throws SQLException, ClassNotFoundException;
	long update ( T obj ) throws SQLException, ClassNotFoundException;
	void delete ( T obj ) throws SQLException, ClassNotFoundException;
	T findbyID  ( long id ) throws SQLException, ClassNotFoundException;
	List<T> findAll() throws SQLException, ClassNotFoundException;
}
