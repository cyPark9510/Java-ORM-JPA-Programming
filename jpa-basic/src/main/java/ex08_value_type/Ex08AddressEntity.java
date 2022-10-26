package ex08_value_type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ADDRESS")
public class Ex08AddressEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Ex08Address address;

    public Ex08AddressEntity() {
    }

    public Ex08AddressEntity(Ex08Address address) {
        this.address = address;
    }

    public Ex08AddressEntity(String city, String street, String zipcode) {
        this.address = new Ex08Address(city, street, zipcode);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ex08Address getAddress() {
        return address;
    }

    public void setAddress(Ex08Address address) {
        this.address = address;
    }
}
