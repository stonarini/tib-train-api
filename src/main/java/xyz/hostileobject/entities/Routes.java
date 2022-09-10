package xyz.hostileobject.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "t_routes")
public class Routes extends PanacheEntityBase {

    @Id
    @Column(name = "route_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne()
    @JoinColumn(name = "train")
    public Train train;

    @ManyToMany()
    @JoinTable(name = "route_stops", joinColumns = { @JoinColumn(name = "route_id") }, inverseJoinColumns = {
            @JoinColumn(name = "group_id", unique = false) })
    public List<Stop> stops;

    @ManyToOne()
    @JoinColumn(name = "departure")
    public Departure departure;
}