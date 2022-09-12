
package xyz.hostileobject.entities;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import xyz.hostileobject.resources.TimeUtils;

@Entity
@Table(name = "STOP")
public class Stop extends PanacheEntityBase {
	@Id
	@Column(name = "ID")
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@JsonIgnore
	@JoinColumn(name = "ROUTE_ID")
	@ManyToOne
	public Route route;

	@ManyToOne
	@JoinColumn(name = "STATION_ID")
	public Station station;

	@Transient
	public String hour;
	public static Stop getStopFromStation(List<Stop> stops, Station station) {
		Optional<Stop> stop = stops.stream().filter(s -> Objects.equals(s.station.id, station.id)).findFirst();
		return stop.orElse(null);
	}

	public String getArrivalTime(String departureTime, Direction direction) {
		Integer arrivalTime = 0;
		Station loopStation = this.station;
		switch (direction.name()) {
			case "LEFT":
				while (loopStation.stationRight != null) {
					arrivalTime += loopStation.timeFromStationRight;
					loopStation = loopStation.stationRight;
				}
				break;
			case "RIGHT":
				while (loopStation.stationLeft != null) {
					arrivalTime += loopStation.timeFromStationLeft;
					if (Objects.equals(loopStation.name, "Lloseta")) {
						loopStation = Station.find("STATION_NAME", "Inca (" + this.route.train.name +")").firstResult();
					} else {
						loopStation = loopStation.stationLeft;
					}
				}
				break;
			default:
				break;
		}
		return TimeUtils.sumTime(departureTime, arrivalTime.toString()).toString();
	}
}