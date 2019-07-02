package pl.pantomash.seatreservation.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.pantomash.seatreservation.domain.Table;
import pl.pantomash.seatreservation.repository.TableRepository;
import pl.pantomash.seatreservation.service.TableService;
import pl.pantomash.seatreservation.service.dto.TableDto;
import pl.pantomash.seatreservation.service.mapper.TableMapper;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TableServiceImpl implements TableService {

    private final Logger log = LoggerFactory.getLogger(TableServiceImpl.class);
    private final TableRepository tableRepository;
    private final TableMapper tableMapper;

    public TableServiceImpl(TableRepository tableRepository, TableMapper tableMapper) {
        this.tableRepository = tableRepository;
        this.tableMapper = tableMapper;
    }

    @Override
    public List<TableDto> findAll() {
        return tableRepository.findAll().stream()
                .map(tableMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public TableDto findOne(Long id) {
        Table table = tableRepository.findById(id).orElse(null);
        return tableMapper.toDto(table);
    }

    @Override
    public TableDto saveTable(TableDto tableDto) {
        Table table = tableMapper.toEntity(tableDto);
        tableRepository.save(table);
        return tableMapper.toDto(table);
    }

    @Override
    public void deleteById(Long id) {
        tableRepository.deleteById(id);
    }
}
