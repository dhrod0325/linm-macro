package lm.macro.auto.data.model.item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class LmHuntJoystick {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String joystickLoc;
    private double joystickDelay;

    public String getJoystickLoc() {
        return joystickLoc;
    }

    public void setJoystickLoc(String joystickLoc) {
        this.joystickLoc = joystickLoc;
    }

    public double getJoystickDelay() {
        return joystickDelay;
    }

    public void setJoystickDelay(double joystickDelay) {
        this.joystickDelay = joystickDelay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "LmHuntJoystick{" +
                "id=" + id +
                ", joystickLoc='" + joystickLoc + '\'' +
                ", joystickDelay=" + joystickDelay +
                '}';
    }
}
