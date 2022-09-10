package xyz.hostileobject.resources;

import java.time.LocalTime;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import xyz.hostileobject.entities.Direction;
import xyz.hostileobject.entities.Routes;
import xyz.hostileobject.entities.Station;
import xyz.hostileobject.entities.Stop;

@Path("/")
public class AppResource {

    private boolean isAfterNow(String departureHour, String arrivalTime) {
        LocalTime arrTime = sumTime(departureHour, arrivalTime);
        LocalTime currTime = LocalTime.of(6, 10);
        return currTime.isBefore(arrTime);
    }

    private LocalTime sumTime(String departureHour, String arrivalTime) {
        LocalTime arrTime = toLocalTime(departureHour);
        return arrTime.plusMinutes(Long.parseLong(arrivalTime));
    }

    private LocalTime toLocalTime(String time) {
        return LocalTime.of(Integer.parseInt(time.substring(0, time.indexOf(":"))),
                Integer.parseInt(time.substring(time.indexOf(":") + 1)));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response root() {
        Station station = Station.find("station_name", "Lloseta").firstResult();
        Station finalStation = Station.find("station_name", "Sa Pobla").firstResult();
        Direction direction = Station.stationDirectionFromStation(station, finalStation);
        List<Routes> routes = Routes.findAll().list();
        Optional<Routes> optionalFinalRoute = routes.stream()
                .peek(l -> System.out.println("here 1"))
                .filter(route -> route.departure.direction == direction)
                .filter(route -> route.stops.stream().anyMatch(s -> s.station.id == station.id))
                .peek(l -> System.out.println("here 3"))
                .filter(route -> route.stops.stream().anyMatch(s -> s.station.id == finalStation.id))
                .peek(l -> System.out.println("here 4"))
                // .filter(route -> route.stops.stream().allMatch(s ->
                // isAfterNow(route.departure.hour, s.hour)))
                .peek(l -> System.out.println("here 5"))
                .sorted((r1, r2) -> toLocalTime(Stop.getStopFromStation(r1.stops, station).hour)
                        .isAfter(toLocalTime(Stop.getStopFromStation(r2.stops, station).hour)) ? -1 : 1)
                .findFirst();
        if (optionalFinalRoute.isPresent()) {
            return Response.ok(optionalFinalRoute.get()).build();
        }
        return Response.ok("No route found").build();

    }

}
