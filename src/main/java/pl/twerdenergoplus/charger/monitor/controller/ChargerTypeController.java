package pl.twerdenergoplus.charger.monitor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.twerdenergoplus.charger.monitor.dto.ChargerTypeCreateDto;
import pl.twerdenergoplus.charger.monitor.dto.ChargerTypeDto;
import pl.twerdenergoplus.charger.monitor.service.ChargerTypeService;

import java.util.List;

@RestController
@RequestMapping("/api/charger-types")
@RequiredArgsConstructor
public class ChargerTypeController {

    private final ChargerTypeService chargerTypeService;

    @GetMapping
    public List<ChargerTypeDto> getAll() {
        return chargerTypeService.findAll();
    }

    @GetMapping("/{id}")
    public ChargerTypeDto getById(@PathVariable Long id) {
        return chargerTypeService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ChargerTypeDto> create(@RequestBody ChargerTypeCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chargerTypeService.create(dto));
    }

    @PutMapping("/{id}")
    public ChargerTypeDto update(@PathVariable Long id, @RequestBody ChargerTypeCreateDto dto) {
        return chargerTypeService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        chargerTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

