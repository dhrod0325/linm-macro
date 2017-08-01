package lm.macro.auto.data.model.item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class LmHuntMap {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @ElementCollection(targetClass = LmHuntJoystick.class)
    private List<LmHuntJoystick> huntJoysticks = new ArrayList<>();

    @Column(columnDefinition = "boolean default 0")
    private boolean noDungeon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LmHuntJoystick> getHuntJoysticks() {
        return huntJoysticks;
    }

    public void setHuntJoysticks(List<LmHuntJoystick> huntJoysticks) {
        this.huntJoysticks = huntJoysticks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "LmHuntMap{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", huntJoysticks=" + huntJoysticks +
                '}';
    }

    public boolean isNoDungeon() {
        return noDungeon;
    }

    public void setNoDungeon(boolean noDungeon) {
        this.noDungeon = noDungeon;
    }
}
