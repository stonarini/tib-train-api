package xyz.hostileobject.entities;

public class CreateRouteRq {
    String trainName;
    String departureStation;
    String departureHour;
    RouteType routeType;

    public String getTrainName() {
        return trainName;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public String getDepartureHour() {
        return departureHour;
    }

    public RouteType getRouteType() {
        return routeType;
    }
}
