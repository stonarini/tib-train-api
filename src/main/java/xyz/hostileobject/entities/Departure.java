package xyz.hostileobject.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "t_departure")
public class Departure extends PanacheEntityBase {
	@Id
	@Column(name = "dep_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@Column(name = "direction")
	@Enumerated(EnumType.STRING)
	public Direction direction;

	@Column(name = "dep_hour")
	public String hour;
}