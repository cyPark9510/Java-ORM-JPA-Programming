package ex08_value_type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Ex08Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    // 기간 Period
    @Embedded
    private Ex08Period workPeriod;

    // 주소 Addrees
    @Embedded
    private Ex08Address homeAddress;

    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOOD",
            joinColumns = @JoinColumn(name = "MEMBER_ID")
    )
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet<>();

//    @ElementCollection
//    @CollectionTable(name = "ADDRESS",
//            joinColumns = @JoinColumn(name = "MEMBER_ID")
//    )
//    private List<Ex08Address> addressHistory = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<Ex08AddressEntity> addressHistory = new ArrayList<>();

    // 주소 Addrees
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city",
                    column = @Column(name = "WORK_CITY")),
            @AttributeOverride(name = "street",
                    column = @Column(name = "WORK_STREET")),
            @AttributeOverride(name = "zipcode",
                    column = @Column(name = "WORK_ZIPCODE"))
    })
    private Ex08Address workAddress;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Ex08Period getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Ex08Period workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Ex08Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Ex08Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Ex08Address getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(Ex08Address workAddress) {
        this.workAddress = workAddress;
    }

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

    public List<Ex08AddressEntity> getAddressHistory() {
        return addressHistory;
    }

    public void setAddressHistory(List<Ex08AddressEntity> addressHistory) {
        this.addressHistory = addressHistory;
    }
}
