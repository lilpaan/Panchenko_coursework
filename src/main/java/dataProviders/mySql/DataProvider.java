package dataProviders.mySql;

import dataProviders.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.AbstractDataProvider;
import org.example.ConstantsProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataProvider extends AbstractDataProvider {
    private static final Logger logger = LogManager.getLogger(DataProvider.class);
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static Statement prepareDataBase() {
        try {
            Class.forName(ConstantsProperties.MY_SQL_JDBC_DRIVER);
            connection = DriverManager.getConnection (ConstantsProperties.MY_SQL_ADDRESS,
                    ConstantsProperties.MY_SQL_USER_NAME, ConstantsProperties.MY_SQL_PASSWORD);
            statement = connection.createStatement();
            statement.execute(ConstantsProperties.MY_SQL_CREATE_TABLE);
        } catch (ClassNotFoundException | SQLException e) {
            logger.error(e);
        }
        return statement;
    }

    public void dropData(){
        statement = prepareDataBase();
        try {
            statement.execute(ConstantsProperties.MY_SQL_DELETE_ALL_DATA);
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public Car getById(long id) {
        Car car = new Car();
        statement = prepareDataBase();
        try {
            resultSet = statement.executeQuery(String.format(ConstantsProperties.MY_SQL_GET_ID, id));
        } catch (SQLException e) {
            logger.error(e);
        }
        try {
            if (resultSet.next()) {
                car.setId(resultSet.getLong(1));
                car.setAutoBrand(resultSet.getString(2));
                car.setMakerCountry(resultSet.getString(3));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return car;
    }

    @Override
    public List<Car> selectCars() {
        List<Car> cars = new ArrayList<>();
        statement = prepareDataBase();
        try {
            resultSet = statement.executeQuery(ConstantsProperties.MY_SQL_SELECT_ALL_DATA);
        } catch (SQLException e) {
            logger.error(e);
        }
        try {
            while (resultSet.next()) {
                Car car = new Car(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3));
                cars.add(car);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return cars;
    }

    @Override
    public void insert(Car car) {
        statement = prepareDataBase();
        try {
            statement.execute(String.format(ConstantsProperties.MY_SQL_INSERT, car.getId(), car.getAutoBrand(), car.getMakerCountry()));
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public void delete(long id) {
        boolean a = true;
        if(a) {
            statement = prepareDataBase();
            try {
                statement.execute(String.format(ConstantsProperties.MY_SQL_DELETE, id));
            } catch (SQLException e) {
                logger.error(e);
            }
        }
    }

    @Override
    public void update(Car car) {
        statement = prepareDataBase();
        try {
            statement.execute(String.format(ConstantsProperties.MY_SQL_UPDATE, car.getAutoBrand(), car.getMakerCountry(), car.getId()));
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
