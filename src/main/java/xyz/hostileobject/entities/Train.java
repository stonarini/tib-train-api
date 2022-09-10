package xyz.hostileobject.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "t_train")
public class Train extends PanacheEntityBase {
	@Id
	@Column(name = "train_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@Column(name = "train_name")
	public String trainName;
}
