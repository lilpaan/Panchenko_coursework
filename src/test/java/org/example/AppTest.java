package org.example;

import dataProviders.xml.DataProvider;
import dataProviders.IDataProvider;
import dataProviders.Car;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */

    IDataProvider dataprov = new DataProvider();

    @Test
    public void insert(){
        Car newCar = new Car("Mercedes", "Germany");
        Car newCar1 = new Car("Lada", "Russia");
        dataprov.insert(newCar);
        dataprov.insert(newCar1);
    }

    @Test
    public void select(){
        Car newCar = new Car("Lada", "Russia");
        dataprov.insert(newCar);
        List<Car> newCarList = dataprov.selectCars();
        assertEquals(newCar, newCarList.get(newCarList.size()-1));
    }

    @Test
    public void getById(){
        Car newCar = new Car("Lada", "Russia");
        dataprov.insert(newCar);
        Car byId = dataprov.getById(newCar.getId());
        assertEquals(newCar, byId);
    }

    @Test
    public void update(){
        Car car = new Car("Lada", "Russia");
        Car newCar = new Car(car.getId(), "VAZ", "Voljskiy");
        dataprov.insert(car);
        dataprov.update(newCar);
        Car byId = dataprov.getById(newCar.getId());
        assertEquals(newCar, byId);
    }
    @Test
    public void delete() {
        Car newCar = new Car("Lada", "Russia");
        dataprov.insert(newCar);
        dataprov.delete(newCar.getId());
    }
}

