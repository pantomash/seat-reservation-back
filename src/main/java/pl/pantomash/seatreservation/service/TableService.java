package pl.pantomash.seatreservation.service;

import pl.pantomash.seatreservation.service.dto.TableDto;

import java.util.List;

public interface TableService {
    List<TableDto> findAll();
    TableDto findOne(Long id);
    TableDto saveTable(TableDto tableDto);
    void deleteById(Long id);
}
