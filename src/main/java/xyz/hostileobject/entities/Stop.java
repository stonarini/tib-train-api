
package xyz.hostileobject.entities;

import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "t_stops")
public class Stop extends PanacheEntityBase {
	@Id
	@Column(name = "stop_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@Column(name = "group_id")
	public Integer group;

	@ManyToOne
	@JoinColumn(name = "station")
	public Station station;

	@Column(name = "arrival_time")
	public String hour;

	public static Stop getStopFromStation(List<Stop> stops, Station station) {
		Optional<Stop> stop = stops.stream().filter(s -> s.station.id == station.id).findFirst();
		return stop.isPresent() ? stop.get() : null;
	}
}