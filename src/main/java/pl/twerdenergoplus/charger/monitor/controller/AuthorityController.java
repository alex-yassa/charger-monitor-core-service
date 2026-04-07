package pl.twerdenergoplus.charger.monitor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.twerdenergoplus.charger.monitor.dto.AuthorityCreateDto;
import pl.twerdenergoplus.charger.monitor.dto.AuthorityDto;
import pl.twerdenergoplus.charger.monitor.service.AuthorityService;

import java.util.List;

@RestController
@RequestMapping("/api/authorities")
@RequiredArgsConstructor
public class AuthorityController {

    private final AuthorityService authorityService;

    @GetMapping
    @PreAuthorize("hasAuthority('AUTHORITY_READ')")
    public List<AuthorityDto> getAll() {
        return authorityService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('AUTHORITY_READ')")
    public AuthorityDto getById(@PathVariable Long id) {
        return authorityService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('AUTHORITY_WRITE')")
    public ResponseEntity<AuthorityDto> create(@RequestBody AuthorityCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorityService.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('AUTHORITY_WRITE')")
    public AuthorityDto update(@PathVariable Long id, @RequestBody AuthorityCreateDto dto) {
        return authorityService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('AUTHORITY_WRITE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

