package dataProviders.xml;

import dataProviders.Car;
import org.example.ConstantsProperties;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
