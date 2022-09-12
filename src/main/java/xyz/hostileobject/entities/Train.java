package xyz.hostileobject.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "TRAIN")
public class Train extends PanacheEntityBase {
	@Id
	@Column(name = "ID")
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@Column(name = "TRAIN_NAME")
	public String name;
}
