package dataProviders.csv;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import dataProviders.Car;
import dataProviders.IDataProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.ConstantsProperties;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataProvider implements IDataProvider {
    private static final Logger logger = LogManager.getLogger(DataProvider.class);

    public DataProvider() {
    }

    @Override
    public Car getById(long id) {
        List<Car> cars = selectCars();
        Car Car = null;
        try {
            Car = cars.stream().filter(a -> a.getId() == id).collect(Collectors.toList()).get(0);
        } catch (IndexOutOfBoundsException e) {
            logger.error(e);
        }
        return Car;
    }


    @Override
    public List<Car> selectCars() {
    List<Car> cars = null;
        try {
        cars = new CsvToBeanBuilder<Car>(new FileReader(ConstantsProperties.PATH_TO_CSV))
                .withType(Car.class).build().parse();
    } catch (FileNotFoundException e) {
        logger.error(e);
    }
        return cars;
    }



    @Override
    public void insert(Car car) {
        Writer writer = null;
        File file = new File(ConstantsProperties.PATH_TO_CSV);
        List<Car> cars = new ArrayList<>();
        if ((file.length() != 0) && file.exists()){
            cars = selectCars();
        }
        cars.add(car);
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            logger.error(e);
        }
        StatefulBeanToCsv<Car> beanToCsv = new StatefulBeanToCsvBuilder<Car>(writer).build();
        try {
            beanToCsv.write(car);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            logger.error(e);
        }
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            logger.error(e);
        }
    }


    @Override
    public void update(Car car) {
        Car byId = getById(car.getId());
        if (byId == null){
            return;
        }
        delete(car.getId());
        insert(car);
    }

    @Override
    public void delete(long id) {
        Car newCar = getById(id);
        if (newCar == null) {
            return;
        }
        List<Car> cars = selectCars();
        cars.removeIf(school -> (school.getId() == id));
        Writer writer = null;
        try {
            writer = new FileWriter(ConstantsProperties.PATH_TO_CSV);
        } catch (IOException e) {
            logger.error(e);
        }
        StatefulBeanToCsv<Car> beanToCsv = new StatefulBeanToCsvBuilder<Car>(writer).build();
        try {
            beanToCsv.write(cars);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            logger.error(e);
        }
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            logger.error(e);
        }
    }
}