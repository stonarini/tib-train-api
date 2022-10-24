package xyz.hostileobject.resources;

import xyz.hostileobject.entities.Error;
import xyz.hostileobject.entities.*;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
                .filter(route -> route.stops.stream()
                        .anyMatch(s -> Objects.equals(s.station.id, station.id)
                                && TimeUtils.isAfterNow(s.getArrivalTime(route.departureHour, direction))))
                .filter(route -> route.stops.stream().anyMatch(s -> Objects.equals(s.station.id, finalStation.id)))
                .min((r1, r2) -> TimeUtils.compare(
                        Stop.getStopFromStation(r1.stops, station).getArrivalTime(r1.departureHour, direction),
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

    @POST
    @Path("/routes")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findRoutes(RouteRq request) {
        Station station = Station.find("STATION_NAME", request.stationFrom).firstResult();
        Station finalStation = Station.find("STATION_NAME", request.stationTo).firstResult();
        Direction direction = Station.stationDirectionFromStation(station, finalStation);
        List<Route> routes = Route.findAll().list();
        List<Route> optionalFinalRoute = routes.stream()
                .filter(route -> route.direction == direction)
                .filter(route -> route.stops.stream()
                        .anyMatch(s -> Objects.equals(s.station.id, station.id)
                                && TimeUtils.isAfterNow(s.getArrivalTime(route.departureHour, direction))))
                .filter(route -> route.stops.stream().anyMatch(s -> Objects.equals(s.station.id, finalStation.id)))
                .sorted((r1, r2) -> TimeUtils.compare(
                        Stop.getStopFromStation(r1.stops, station).getArrivalTime(r1.departureHour, direction),
                        Stop.getStopFromStation(r2.stops, station).getArrivalTime(r2.departureHour, direction)))
                .collect(Collectors.toList());
        if (optionalFinalRoute.size() > 0) {
            optionalFinalRoute.forEach((fr) -> {
                fr.stops = fr.stops.stream()
                        .peek(s -> s.hour = s.getArrivalTime(fr.departureHour, direction))
                        .sorted((s1, s2) -> TimeUtils.compare(s1.hour, s2.hour))
                        .collect(Collectors.toList());
            });
            return Response.ok(optionalFinalRoute).build();
        }
        return Response.ok(new Error("No route found")).build();

    }

    @PUT
    @Path("/createRoute")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public Response createRoute(CreateRouteRq request) {
        Route newRoute = new Route();
        newRoute.train = Train.find("TRAIN_NAME", request.getTrainName()).firstResult();
        newRoute.departureHour = request.getDepartureHour();
        newRoute.direction = Objects.equals(request.getDepartureStation(), "Palma") ? Direction.LEFT : Direction.RIGHT;
        newRoute.persist();

        List<Stop> stopList = new ArrayList<Stop>();

        switch (newRoute.train.name) {
            case "T1":
                stopList.add(new Stop(newRoute.id, 16));
                break;
            case "T2":
                for (long i = 17; i <= 21; i++) {
                    stopList.add(new Stop(newRoute.id, i));
                }
                break;
            case "T3":
                for (long i = 22; i <= 25; i++) {
                    stopList.add(new Stop(newRoute.id, i));
                }
                break;
        }

        switch (request.getRouteType()) {
            case DIRECT:
                for (long i = 1; i <= 15; i++) {
                    stopList.add(new Stop(newRoute.id, i));
                }
                break;
            case SEMIDIRECT:
                stopList.add(new Stop(newRoute.id, 1));
                for (long i = 10; i <= 15; i++) {
                    stopList.add(new Stop(newRoute.id, i));
                }
                break;
            case METRO:
                for (long i = 1; i <= 10; i++) {
                    stopList.add(new Stop(newRoute.id, i));
                }
                break;
        }
        for (Stop stop : stopList) {
            stop.persist();
        }

        return Response.status(Response.Status.CREATED).entity("Created new route").build();
    }

}
