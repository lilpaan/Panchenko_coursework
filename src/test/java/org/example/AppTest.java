package org.example;

import dataProviders.Car;
import dataProviders.mySql.DataProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private static final Logger logger = LogManager.getLogger(AppTest.class);

    DataProvider dataProvider = new DataProvider();

    Car new_car_1 = new Car( 1, "Lada", "Russia");
    Car new_car_2 = new Car( 2, "Chevrolet", "USA");

    @Before
    public void insert(){
        drop();
        dataProvider.insert(new_car_1);
        dataProvider.insert(new_car_2);
    }
        @Test
        public void positiveGetById(){
            Car byId = dataProvider.getById(new_car_1.getId());
            assertEquals(new_car_1.getId(), byId.getId());
        }

        @Test
        public void negativeGetById(){
            Car byId = dataProvider.getById(System.currentTimeMillis());
            assertNotEquals(new_car_1.getId(), byId.getId());
        }

        @Test
        public void positiveSelect(){
            List<Car> entities;
            entities = dataProvider.selectCars();
            assertEquals(2, entities.size());
        }

        @Test
        public void positiveDelete(){
            Car forDelete = new Car();
            dataProvider.delete(2);
            assertEquals(forDelete, dataProvider.getById(2));
        }

    @Test
    public void positiveUpdate(){
        Car newEntity = new Car(2,"updated name", "updated surname");
        dataProvider.update(newEntity);
        assertEquals(newEntity, dataProvider.getById(newEntity.getId()));
    }

    @Test
    public void negativeUpdate(){
        Car newEntity = new Car(4, "updated name", "updated surname");
        dataProvider.update(newEntity);
        assertNotEquals(newEntity, dataProvider.getById(newEntity.getId()));
    }

    public void drop(){
        dataProvider.dropData();
    }

    /*    @Test
    public void insert(){
        Car newCar = new Car("Mercedes", "Germany");
        Car newCar1 = new Car("Lada", "Russia");
        dataProvider.insert(newCar);
        dataProvider.insert(newCar1);
    }

    @Test
    public void select(){
        Car newCar = new Car("Lada", "Russia");
        dataProvider.insert(newCar);
        List<Car> newCarList = dataProvider.selectCars();
        assertEquals(newCar, newCarList.get(newCarList.size()-1));
    }

    @Test
    public void getById(){
        Car newCar = new Car("Lada", "Russia");
        dataProvider.insert(newCar);
        Car byId = dataProvider.getById(newCar.getId());
        assertEquals(newCar, byId);
    }

    @Test
    public void update(){
        Car car = new Car("Lada", "Russia");
        Car newCar = new Car("VAZ", "Voljskiy");
        dataProvider.insert(car);
        dataProvider.update(newCar);
        Car byId = dataProvider.getById(newCar.getId());
        assertEquals(newCar, byId);
    }
    @Test
    public void delete() {
        Car newCar = new Car("Lada", "Russia");
        dataProvider.insert(newCar);
        dataProvider.delete(newCar.getId());
    }
}*/
}

