package pl.pantomash.seatreservation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pantomash.seatreservation.service.TableService;
import pl.pantomash.seatreservation.service.dto.TableDto;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class TableResource {
    private Logger log = LoggerFactory.getLogger(TableResource.class);
    private final TableService tableService;

    public TableResource(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping(value = "/table")
    public ResponseEntity<List<TableDto>> getAllTables() {
        log.debug("REST request to get all Tables");
        List<TableDto> tablesDtoList = tableService.findAll();
        if (tablesDtoList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tablesDtoList);
    }

    @GetMapping(value = "/table/{id}")
    public ResponseEntity<TableDto> getTable(@PathVariable Long id) {
        log.debug("REST request to get Table");
        TableDto tableDto = tableService.findOne(id);
        if (tableDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tableDto);
    }

    @PostMapping(value = "/table")
    public ResponseEntity<TableDto> createTable(@RequestBody TableDto tableDto) throws URISyntaxException {
        log.debug("REST request to create Table");
        if (tableDto.getId() != null) {
            return ResponseEntity.badRequest().header("A new Table cannot have an ID").build();
        }
        TableDto result = tableService.saveTable(tableDto);
        return ResponseEntity.ok(result);
    }

    @PutMapping(value = "/table")
    public ResponseEntity<TableDto> updateTable(@RequestBody TableDto tableDto) throws URISyntaxException {
        log.debug("REST request to update Table");
        if (tableDto.getId() == null) {
            return ResponseEntity.badRequest().header("An existing Table must have an ID").build();
        }
        TableDto result = tableService.saveTable(tableDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/table/{id}")
    public ResponseEntity<Void> deleteTable(@PathVariable Long id) {
        log.debug("REST request to delete Table : {}", id);
        tableService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
