package xyz.hostileobject.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.IgnoreProperty;

@Entity
@Table(name = "t_station")
public class Station extends PanacheEntityBase {
	@Id
	@Column(name = "station_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@Column(name = "station_name")
	public String stationName;

	@JsonIgnore
	@Column(name = "time_from_station_left", nullable = true)
	public Integer timeFromStationLeft;

	@JsonIgnore
	@Column(name = "time_from_station_right", nullable = true)
	public Integer timeFromStationRight;

	@OneToOne()
	@JsonIgnore
	@JoinColumn(name = "station_right", foreignKey = @ForeignKey(name = "fk_sr"))
	public Station stationRight;

	@OneToOne()
	@JsonIgnore
	@JoinColumn(name = "station_left", foreignKey = @ForeignKey(name = "fk_sl"))
	public Station stationLeft;

	public static Direction stationDirectionFromStation(Station departureStation, Station returnStation) {
		if (departureStation.isStationOnTheRight(returnStation)) {
			return Direction.RIGHT;
		} else if (departureStation.isStationOnTheLeft(returnStation)) {
			return Direction.LEFT;
		} else {
			return null;
		}
	}

	boolean isStationOnTheRight(Station returnStation) {
		Station loopStation = this;
		while (loopStation.id != returnStation.id) {
			System.out.println("lol");
			if (loopStation.stationRight == null) {
				return false;
			}
			loopStation = loopStation.stationRight;
		}
		return true;
	}

	boolean isStationOnTheLeft(Station returnStation) {
		Station loopStation = this;
		while (loopStation.id != returnStation.id) {
			if (loopStation.stationLeft == null) {
				return false;
			}
			loopStation = loopStation.stationLeft;
		}
		return true;
	}
}
