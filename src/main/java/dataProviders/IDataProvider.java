package dataProviders;

import java.util.List;

public interface IDataProvider {
        Car getById(long id);
        List<Car> selectCars();
        void insert(Car car);
        void delete(long id);
        void update(Car car);
    }
