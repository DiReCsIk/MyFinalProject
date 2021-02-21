package com.MyServlet.DBManager.Dao;

import com.MyServlet.DBManager.Service.Impl.AdministratorServiceImpl;
import com.MyServlet.Entity.Entity;
import com.MyServlet.Exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public abstract class AbstractDao<E extends Entity> {
    private static final Logger log = Logger.getLogger(AbstractDao.class.getName());
    protected final Connection connection;

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }

    protected E selectByStatement(String sqlStatement, String... whereArgs) throws DAOException {
        E entity = null;
        try (PreparedStatement preparedStatement = createStatement(sqlStatement, whereArgs)) {
            log.info("Executing preparedStatement");
            ResultSet resultSet = preparedStatement.executeQuery();
            log.info("Result processing");
            if (resultSet.next()) {
                entity = createEntity(resultSet);
            }
        } catch (SQLException exception) {
            log.error("Error! " + exception);
            throw new DAOException(exception.getMessage(), exception);
        }
        return entity;
    }

    protected String selectStringByStatement(String sqlStatement, String... whereArgs) throws DAOException {
        String string = null;
        try (PreparedStatement preparedStatement = createStatement(sqlStatement, whereArgs)) {
            log.info("Executing preparedStatement");
            ResultSet resultSet = preparedStatement.executeQuery();
            log.info("Result processing");
            if (resultSet.next()) {
                string = resultSet.getString(1);
            }
        } catch (SQLException exception) {
            log.error("Error! " + exception);
            throw new DAOException(exception.getMessage(), exception);
        }
        return string;
    }

    protected Collection<E> selectAllByStatement(String sqlStatement, String... whereArgs) throws DAOException {
        Collection<E> list = new ArrayList<>();
        try (PreparedStatement preparedStatement = createStatement(sqlStatement, whereArgs)) {
            log.info("Executing preparedStatement");
            ResultSet resultSet = preparedStatement.executeQuery();
            log.info("Result processing");
            while (resultSet.next()) {
                list.add(createEntity(resultSet));
            }
        } catch (SQLException exception) {
            log.error("Error! " + exception);
            throw new DAOException(exception.getMessage(), exception);
        }
        return list;
    }

    private PreparedStatement createStatement(String sqlCommand, String[] args) throws SQLException {
        log.info("Creating statement");
        PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
        int position = 1;
        log.info("Inserting args in statement");
        for (String arg : args) {
            if (arg.matches("^-?\\d+\\.?\\d*$")) {
                preparedStatement.setInt(position++, Integer.parseInt(arg));
            } else {
                preparedStatement.setString(position++, arg);
            }
        }
        log.info("Statement prepared");
        return preparedStatement;
    }

    protected Map<String, ArrayList<String>> selectMapByStatement(String sqlStatement, String[] arrayListArgs, String... arg) throws DAOException {
        Map<String, ArrayList<String>> result = new LinkedHashMap<>();
        for (String arrayListType : arrayListArgs) {
            result.put(arrayListType, new ArrayList<>());
        }
        try (PreparedStatement preparedStatement = createStatement(sqlStatement, arg)) {
            log.info("Executing preparedStatement");
            ResultSet resultSet = preparedStatement.executeQuery();
            log.info("Result processing");
            while (resultSet.next()) {
                int position = 1;
                for (String arrayListType : arrayListArgs) {
                    result.get(arrayListType).add(resultSet.getString(position++));
                }
            }
        } catch (SQLException exception) {
            log.error("Error! " + exception);
            throw new DAOException(exception.getMessage(), exception);
        }
        return result;
    }

    protected List<Integer> selectIntegerListByStatement(String sqlStatement, String... whereArgs) throws DAOException {
        List<Integer> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = createStatement(sqlStatement, whereArgs)) {
            log.info("Executing preparedStatement");
            ResultSet resultSet = preparedStatement.executeQuery();
            log.info("Result processing");
            while (resultSet.next()) {
                result.add(resultSet.getInt(1));
            }
        } catch (Exception exception) {
            log.error("Error! " + exception);
            throw new DAOException(exception.getMessage(), exception);
        }
        return result;
    }

    protected List<String> selectStringListByStatement(String sqlStatement, String... whereArgs) throws DAOException {
        List<String> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = createStatement(sqlStatement, whereArgs)) {
            log.info("Executing preparedStatement");
            ResultSet resultSet = preparedStatement.executeQuery();
            log.info("Result processing");
            while (resultSet.next()) {
                result.add(resultSet.getString(1));
            }
        } catch (Exception exception) {
            log.error("Error! " + exception);
            throw new DAOException(exception.getMessage(), exception);
        }
        return result;
    }

    protected int selectIntByStatement(String sqlStatement, String... arg) throws DAOException {
        try (PreparedStatement preparedStatement = createStatement(sqlStatement, arg)) {
            log.info("Executing preparedStatement");
            ResultSet resultSet = preparedStatement.executeQuery();
            log.info("Result processing");
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException exception) {
            log.error("Error! " + exception);
            throw new DAOException(exception.getMessage(), exception);
        }
        return 0;
    }

    protected abstract E createEntity(ResultSet resultSet) throws SQLException;

    protected void updateByStatement(String sqlStatement, String... args) throws DAOException {
        try (PreparedStatement preparedStatement = createStatement(sqlStatement, args)) {
            log.info("Executing preparedStatement");
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            log.error("Error! " + exception);
            throw new DAOException(exception.getMessage(), exception);
        }
    }
}
