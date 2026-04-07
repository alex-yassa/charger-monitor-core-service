package pl.twerdenergoplus.charger.monitor.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.twerdenergoplus.charger.monitor.dto.AppUserCreateDto;
import pl.twerdenergoplus.charger.monitor.dto.AppUserDto;
import pl.twerdenergoplus.charger.monitor.service.AppUserService;
import java.util.List;
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;
    @GetMapping
    public List<AppUserDto> getAll() {
        return appUserService.findAll();
    }
    @GetMapping("/{id}")
    public AppUserDto getById(@PathVariable Long id) {
        return appUserService.findById(id);
    }
    @PostMapping
    public ResponseEntity<AppUserDto> create(@RequestBody AppUserCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appUserService.create(dto));
    }
    @PutMapping("/{id}")
    public AppUserDto update(@PathVariable Long id, @RequestBody AppUserCreateDto dto) {
        return appUserService.update(id, dto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        appUserService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
