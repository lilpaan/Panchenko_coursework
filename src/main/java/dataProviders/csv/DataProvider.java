package dataProviders.csv;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import dataProviders.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.ConstantsProperties;
import org.example.AbstractDataProvider;
import org.example.Status;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DataProvider extends AbstractDataProvider {
    private static final Logger logger = LogManager.getLogger(DataProvider.class);

    public DataProvider() {
    }
    public <T> boolean saveCSV (List <T> list, String csvDir) {
        boolean isSaved;
        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter(csvDir));
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter).build();
            beanToCsv.write(list);
            csvWriter.close();
            isSaved = true;
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            logger.error(e);
            isSaved = false;
        }
        return isSaved;
    }

    public <T> Optional<List<T>> readCSV(Class<?> type, String csvDir) {
        Optional<List<T>> opList;
        File file = new File(csvDir);
        if(file.exists()) {
            try {
                FileReader reader = new FileReader(csvDir);
                opList = Optional.of(new CsvToBeanBuilder<T>(reader).withType((Class<? extends T>) type).build().parse());
            } catch (IOException e) {
                logger.error(e);
                opList = Optional.empty();
            }
        } else {
            opList = Optional.empty();
        }
        return opList;
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
        List<Object> cars = new ArrayList<>();
        if (readCSV(Car.class, ConstantsProperties.PATH_TO_CSV).isPresent()) {
            cars = readCSV(Car.class, ConstantsProperties.PATH_TO_CSV).get();
        }
        cars.add(car);
        saveCSV(cars, ConstantsProperties.PATH_TO_CSV);
        saveHistoryContent(getClass().getName(), Status.SUCCESS, car);
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
        List<Car> cars = new ArrayList<>();
        if (readCSV(Car.class, ConstantsProperties.PATH_TO_CSV).isPresent()) {
            cars = (List<Car>) (Object) readCSV(Car.class, ConstantsProperties.PATH_TO_CSV).get();
        }
        cars.removeIf(car -> (car.getId() == id));
        saveCSV(cars, ConstantsProperties.PATH_TO_CSV);
    }
}