package pl.pantomash.seatreservation.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pantomash.seatreservation.domain.Restaurant;
import pl.pantomash.seatreservation.domain.Table;
import pl.pantomash.seatreservation.repository.TableRepository;
import pl.pantomash.seatreservation.service.dto.TableDto;
import pl.pantomash.seatreservation.service.mapper.TableMapper;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TableServiceImplTest {

    @Mock
    private TableRepository tableRepository;

    @Mock
    private TableMapper tableMapper;

    @InjectMocks
    private TableServiceImpl tableService;

    List<Table> tableList;
    Table dummyTable1;
    TableDto dummyTableDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        tableService = new TableServiceImpl(tableRepository, tableMapper);
        tableList = new LinkedList<>();
        Restaurant restaurant = new Restaurant(5L, "KFC", "Żory", "...");
        dummyTable1 = new Table(1L, "Stolik 1", 2, restaurant);
        dummyTableDto = new TableDto(1L, "Stolik 2", 2, restaurant.getId());
        Table dummyTable2 = new Table(2L, "Stolik 2", 2, restaurant);
        tableList.add(dummyTable1);
        tableList.add(dummyTable2);
    }

    @Test
    void findAll() {
        when(tableRepository.findAll()).thenReturn(tableList);
        List<TableDto> result = tableService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void findOne() {
        when(tableRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(dummyTable1));
        when(tableMapper.toDto(dummyTable1)).thenReturn(dummyTableDto);

        TableDto result = tableService.findOne(dummyTable1.getId());

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void saveTable() {
        when(tableMapper.toEntity(dummyTableDto)).thenReturn(dummyTable1);
        when(tableMapper.toDto(dummyTable1)).thenReturn(dummyTableDto);
        when(tableRepository.save(dummyTable1)).thenReturn(dummyTable1);

        TableDto result = tableService.saveTable(dummyTableDto);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void deleteById() {
        tableService.deleteById(dummyTableDto.getId());

        verify(tableRepository, times(1)).deleteById(dummyTableDto.getId());
    }
}