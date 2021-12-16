package dataProviders.xml;

import dataProviders.Car;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.example.ConstantsProperties;

import java.util.List;

@XmlRootElement(name = ConstantsProperties.CARS)

public class Cars {
    private List<Car> cars;
    public List<Car> getCars() {
        return cars;
    }
    @XmlElement(name = ConstantsProperties.CAR)
    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public String toString() {
        return cars.toString();
    }
}
