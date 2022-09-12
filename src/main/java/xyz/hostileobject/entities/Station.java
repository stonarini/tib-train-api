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

import java.util.Objects;

@Entity
@Table(name = "STATION")
public class Station extends PanacheEntityBase {
	@Id
	@Column(name = "ID")
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@Column(name = "STATION_NAME")
	public String name;

	@JsonIgnore
	@Column(name = "TIME_FROM_STATION_LEFT")
	public Integer timeFromStationLeft;

	@JsonIgnore
	@Column(name = "TIME_FROM_STATION_RIGHT")
	public Integer timeFromStationRight;

	@OneToOne()
	@JsonIgnore
	@JoinColumn(name = "STATION_RIGHT", foreignKey = @ForeignKey(name = "FK_STATION_RIGHT"))
	public Station stationRight;

	@OneToOne()
	@JsonIgnore
	@JoinColumn(name = "STATION_LEFT", foreignKey = @ForeignKey(name = "FK_STATION_LEFT"))
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
		while (!Objects.equals(loopStation.id, returnStation.id)) {
			if (loopStation.stationRight == null) {
				return false;
			}
			loopStation = loopStation.stationRight;
		}
		return true;
	}

	boolean isStationOnTheLeft(Station returnStation) {
		Station loopStation = this;
		while (!Objects.equals(loopStation.id, returnStation.id)) {
			if (loopStation.stationLeft == null) {
				return false;
			}
			loopStation = loopStation.stationLeft;
		}
		return true;
	}
}
