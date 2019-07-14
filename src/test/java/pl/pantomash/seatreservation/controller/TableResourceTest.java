package pl.pantomash.seatreservation.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.pantomash.seatreservation.service.TableService;
import pl.pantomash.seatreservation.service.dto.RestaurantDto;
import pl.pantomash.seatreservation.service.dto.TableDto;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(TableResource.class)
class TableResourceTest {
    private Logger log = LoggerFactory.getLogger(TableResource.class);

    @MockBean
    public TableService tableService;

    @Autowired
    @InjectMocks
    public TableResource tableResource;

    MockMvc mockMvc;

    List<TableDto> tableDtos;
    TableDto dummyTable1;
    TableDto dummyTableWithoudId;

    @BeforeEach
    void setUp()  {
        MockitoAnnotations.initMocks(this);
        tableDtos = new LinkedList<>();
        RestaurantDto restaurantDto = new RestaurantDto(5L, "KFC", "Żory", "...");
        dummyTableWithoudId = new TableDto(null, "Stolik 1", 4, restaurantDto.getId());
        dummyTable1 = new TableDto(1L, "Stolik 2", 2, restaurantDto.getId());
        tableDtos.add(dummyTable1);
        tableDtos.add(new TableDto(3L, "Stolik 3", 2, restaurantDto.getId()));

        mockMvc = MockMvcBuilders.standaloneSetup(tableResource).build();
    }

    @Test
    void getAllTables() throws Exception {
        when(tableService.findAll()).thenReturn(tableDtos);

        mockMvc.perform(get("/api/table"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getTable() throws Exception {
        when(tableService.findOne(anyLong())).thenReturn(dummyTable1);

        mockMvc.perform(get("/api/table/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Stolik 2")))
                .andExpect(jsonPath("$.seats", is(2)));
    }

    @Test
    void createTable() throws Exception {
        Gson gson = new Gson();
        String jsonContent = gson.toJson(dummyTableWithoudId);

        mockMvc.perform(post("/api/table")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    void updateTable() throws Exception {
        Gson gson = new Gson();
        String jsonContent = gson.toJson(dummyTable1);

        mockMvc.perform(put("/api/table")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTable() throws Exception {
        tableService.deleteById(1L);
        verify(tableService, times(1)).deleteById(anyLong());
    }
}