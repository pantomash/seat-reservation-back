package pl.pantomash.seatreservation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pantomash.seatreservation.service.RestaurantService;
import pl.pantomash.seatreservation.service.dto.RestaurantDto;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class RestaurantResource {
    private Logger log = LoggerFactory.getLogger(RestaurantResource.class);
    private final RestaurantService restaurantService;

    public RestaurantResource(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping(value = "/restaurant")
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants() {
        log.debug("REST request to get all Restaurants");
        List<RestaurantDto> restaurantDtoList = restaurantService.findAll();
        if (restaurantDtoList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(restaurantDtoList);
    }

    @GetMapping(value = "/restaurant/{id}")
    public ResponseEntity<RestaurantDto> getRestaurant(@PathVariable Long id) {
        log.debug("REST request to get Restaurant");
        RestaurantDto restaurantDto = restaurantService.findOne(id);
        if (restaurantDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(restaurantDto);
    }

    @PostMapping(value = "/restaurant")
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody RestaurantDto restaurantDto) throws URISyntaxException {
        log.debug("REST request to create Restaurant");
        if (restaurantDto.getId() != null) {
            return ResponseEntity.badRequest().header("A new Restaurant cannot already have an ID").build();
        }
        RestaurantDto result = restaurantService.saveRestaurant(restaurantDto);
        return ResponseEntity.created(new URI("/restaurant/" + result.getId()))
                .body(result);
    }

    @PutMapping(value = "/restaurant")
    public ResponseEntity<RestaurantDto> updateRestaurant(@RequestBody RestaurantDto restaurantDto) throws URISyntaxException {
        log.debug("REST request to update Restaurant");
        if (restaurantDto.getId() == null) {
            return ResponseEntity.badRequest().header("An existing Restaurant must have an ID").build();
        }
        RestaurantDto result = restaurantService.saveRestaurant(restaurantDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/restaurant/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        log.debug("REST request to delete Restaurant : {}", id);
        restaurantService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
