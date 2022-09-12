package xyz.hostileobject.resources;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import xyz.hostileobject.entities.*;
import xyz.hostileobject.entities.Error;

@Path("/")
public class AppResource {

    @POST
    @Path("/route")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findRoute(RouteRq request) {
        Station station = Station.find("STATION_NAME", request.stationFrom).firstResult();
        Station finalStation = Station.find("STATION_NAME", request.stationTo).firstResult();
        Direction direction = Station.stationDirectionFromStation(station, finalStation);
        List<Route> routes = Route.findAll().list();
        Optional<Route> optionalFinalRoute = routes.stream()
                .filter(route -> route.direction == direction)
                .filter(route -> route.stops.stream().anyMatch(s -> Objects.equals(s.station.id, station.id) && TimeUtils.isAfterNow(s.getArrivalTime(route.departureHour, direction))))
                .filter(route -> route.stops.stream().anyMatch(s -> Objects.equals(s.station.id, finalStation.id)))
                .min((r1, r2) -> TimeUtils.compare(Stop.getStopFromStation(r1.stops, station).getArrivalTime(r1.departureHour, direction),
                                                   Stop.getStopFromStation(r2.stops, station).getArrivalTime(r2.departureHour, direction)));
        if (optionalFinalRoute.isPresent()) {
            Route finalRoute = optionalFinalRoute.get();
            finalRoute.stops = finalRoute.stops.stream()
                    .peek(s -> s.hour = s.getArrivalTime(finalRoute.departureHour, direction))
                    .sorted((s1, s2) -> TimeUtils.compare(s1.hour, s2.hour))
                    .collect(Collectors.toList());
            return Response.ok(finalRoute).build();
        }
        return Response.ok(new Error("No route found")).build();

    }

}
