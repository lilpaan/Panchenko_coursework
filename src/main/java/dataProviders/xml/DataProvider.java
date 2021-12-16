package dataProviders.xml;

import dataProviders.Car;
import dataProviders.IDataProvider;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.ConstantsProperties;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class DataProvider implements IDataProvider {
    private static final Logger logger = LogManager.getLogger(DataProvider.class);

    @Override
    public Car getById(long id) {
        List<Car> cars = selectCars();
        Car car = null;
        try {
            car = cars.stream().filter(a -> a.getId() == id).collect(Collectors.toList()).get(0);
        } catch (IndexOutOfBoundsException e){
            logger.catching(e);
        }
        return car;
    }

    @Override
    public List<Car> selectCars() {
        Cars cars = new Cars();
        try{
            JAXBContext context = JAXBContext.newInstance(Cars.class);
            cars = (Cars) context.createUnmarshaller().unmarshal(new File(ConstantsProperties.PATH_TO_XML));
        } catch (JAXBException e){
            logger.catching(e);
        }
        return cars.getCars();
    }

    @Override
    public void insert(Car car) {
        File file = new File(ConstantsProperties.PATH_TO_XML);
        JAXBContext context;
        Cars cars = new Cars();
        List<Car> carList = new ArrayList<>();
        Marshaller marshaller;
        if(file.exists()) {
            try {
                context = JAXBContext.newInstance(Cars.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                cars = (Cars) unmarshaller.unmarshal(new File(ConstantsProperties.PATH_TO_XML));
                carList = cars.getCars();
                carList.add(car);
                cars.setCars(carList);
                marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(cars, new File(ConstantsProperties.PATH_TO_XML));
            } catch (JAXBException e) {
                logger.catching(e);
            }
        } else {
            try {
                context = JAXBContext.newInstance(Cars.class);
                carList.add(car);
                cars.setCars(carList);
                marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(cars, new File(ConstantsProperties.PATH_TO_XML));
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(long id) {
        Car car = getById(id);
        if(car == null){
            return;
        }
        JAXBContext context;
        Cars cars = new Cars();
        Marshaller marshaller;
        List<Car> carList = selectCars();
        carList.removeIf(car1 -> (car1.getId() == id));
        cars.setCars(carList);
        try {
            context = JAXBContext.newInstance(Cars.class);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(cars, new File(ConstantsProperties.PATH_TO_XML));
        } catch (JAXBException e) {
            logger.catching(e);
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
}
