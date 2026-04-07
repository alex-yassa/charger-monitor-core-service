package pl.twerdenergoplus.charger.monitor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.twerdenergoplus.charger.monitor.dto.ChargerCreateDto;
import pl.twerdenergoplus.charger.monitor.dto.ChargerDto;
import pl.twerdenergoplus.charger.monitor.service.ChargerService;

import java.util.List;

@RestController
@RequestMapping("/api/chargers")
@RequiredArgsConstructor
public class ChargerController {

    private final ChargerService chargerService;

    @GetMapping
    public List<ChargerDto> getAll() {
        return chargerService.findAll();
    }

    @GetMapping("/{id}")
    public ChargerDto getById(@PathVariable Long id) {
        return chargerService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ChargerDto> create(@RequestBody ChargerCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chargerService.create(dto));
    }

    @PutMapping("/{id}")
    public ChargerDto update(@PathVariable Long id, @RequestBody ChargerCreateDto dto) {
        return chargerService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        chargerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

