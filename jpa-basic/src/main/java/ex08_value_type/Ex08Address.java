package ex08_value_type;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Ex08Address {
    private String city;
    private String street;
    private String zipcode;

    public Ex08Address() {
    }

    public Ex08Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    private void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    private void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    private void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ex08Address that = (Ex08Address) o;
        return Objects.equals(city, that.city) && Objects.equals(street, that.street) && Objects.equals(zipcode, that.zipcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, zipcode);
    }
}
