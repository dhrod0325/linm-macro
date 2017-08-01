package lm.macro.auto.data.model.setting;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LmWareHouseSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String wareHouseSerial;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWareHouseSerial() {
        return wareHouseSerial;
    }

    public void setWareHouseSerial(String wareHouseSerial) {
        this.wareHouseSerial = wareHouseSerial;
    }
}
