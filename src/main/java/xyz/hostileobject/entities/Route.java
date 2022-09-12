package xyz.hostileobject.entities;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "ROUTE")
public class Route extends PanacheEntityBase {

    @Id
    @Column(name = "ID")
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne()
    @JoinColumn(name = "TRAIN_ID")
    public Train train;

    @OneToMany(mappedBy = "route")
    public List<Stop> stops;

    @Column(name = "DIRECTION")
    @Enumerated(EnumType.STRING)
    public Direction direction;

    @Column(name = "DEPARTURE_HOUR")
    public String departureHour;
}