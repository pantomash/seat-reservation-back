package pl.pantomash.seatreservation.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.pantomash.seatreservation.domain.Restaurant;
import pl.pantomash.seatreservation.repository.RestaurantRepository;
import pl.pantomash.seatreservation.service.RestaurantService;
import pl.pantomash.seatreservation.service.dto.RestaurantDto;
import pl.pantomash.seatreservation.service.mapper.RestaurantMapper;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantService {

    private final Logger log = LoggerFactory.getLogger(RestaurantServiceImpl.class);
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
    }

    @Override
    public List<RestaurantDto> findAll() {
        return restaurantRepository.findAll().stream()
                .map(restaurantMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public RestaurantDto findOne(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
        return restaurantMapper.toDto(restaurant);
    }

    @Override
    public RestaurantDto saveRestaurant(RestaurantDto restaurantDto) {
        Restaurant restaurant = restaurantMapper.toEntity(restaurantDto);
        restaurantRepository.save(restaurant);
        return restaurantMapper.toDto(restaurant);
    }

    @Override
    public void deleteById(Long id) {
        restaurantRepository.deleteById(id);
    }
}
