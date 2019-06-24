package pl.pantomash.seatreservation.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.pantomash.seatreservation.domain.Table;
import pl.pantomash.seatreservation.service.dto.TableDto;

@Mapper(componentModel = "spring", uses = {RestaurantMapper.class})
public interface TableMapper extends EntityMapper<TableDto, Table>{

    @Mapping(source = "restaurant.id", target = "restaurantId")
    TableDto toDto(Table table);

    @Mapping(source = "restaurantId", target = "restaurant")
    Table toEntity(TableDto tableDto);

    default Table fromId(Long id) {
        if (id == null) {
            return null;
        }
        Table table = new Table();
        table.setId(id);
        return table;
    }
}
