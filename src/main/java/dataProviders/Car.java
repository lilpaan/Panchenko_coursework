package dataProviders;

import com.opencsv.bean.CsvBindByName;
import org.example.ConstantsProperties;
import java.util.Objects;

public class Car {
    @CsvBindByName(column = ConstantsProperties.ID)
    long id;
    @CsvBindByName(column = ConstantsProperties.AUTO_BRAND)
    String autoBrand;
    @CsvBindByName(column = ConstantsProperties.MAKER_COUNTRY)
    String makerCountry;

    public Car(long id, String autoBrand, String makerCountry) {
        this.id = id;
        this.autoBrand = autoBrand;
        this.makerCountry = makerCountry;
    }

    public Car(String autoBrand, String makerCountry) {
        this.id = System.currentTimeMillis();
        this.autoBrand = autoBrand;
        this.makerCountry = makerCountry;
    }

    public Car() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAutoBrand() {
        return autoBrand;
    }

    public void setAutoBrand(String autoBrand) {
        this.autoBrand = autoBrand;
    }

    public String getSubject() {
        return makerCountry;
    }

    public void setSubject(String makerCountry) {
        this.makerCountry = makerCountry;
    }

    @Override
    public String toString() {
        return "MyCar{" +
                "id=" + id +
                ", Auto Brand='" + autoBrand + '\'' +
                ", Maker Country='" + makerCountry + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car mycar = (Car) o;
        return id == mycar.id && Objects.equals(autoBrand, mycar.autoBrand) && Objects.equals(makerCountry, mycar.makerCountry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, autoBrand, makerCountry);
    }
}
