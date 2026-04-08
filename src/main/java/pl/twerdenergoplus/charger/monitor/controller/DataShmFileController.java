package pl.twerdenergoplus.charger.monitor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.twerdenergoplus.charger.monitor.dto.DataShmFileCreateDto;
import pl.twerdenergoplus.charger.monitor.dto.DataShmFileDto;
import pl.twerdenergoplus.charger.monitor.service.DataShmFileService;

import java.util.List;

@RestController
@RequestMapping("/api/data-shm-files")
@RequiredArgsConstructor
public class DataShmFileController {

    private final DataShmFileService dataShmFileService;

    @GetMapping
    public List<DataShmFileDto> getAll() {
        return dataShmFileService.findAll();
    }

    @GetMapping("/{id}")
    public DataShmFileDto getById(@PathVariable Long id) {
        return dataShmFileService.findById(id);
    }

    @PostMapping
    public ResponseEntity<DataShmFileDto> create(@RequestBody DataShmFileCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dataShmFileService.create(dto));
    }

    @PutMapping("/{id}")
    public DataShmFileDto update(@PathVariable Long id, @RequestBody DataShmFileCreateDto dto) {
        return dataShmFileService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dataShmFileService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

