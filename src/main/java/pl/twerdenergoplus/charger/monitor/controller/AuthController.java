package pl.twerdenergoplus.charger.monitor.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.twerdenergoplus.charger.monitor.dto.AppUserCreateDto;
import pl.twerdenergoplus.charger.monitor.dto.AppUserDto;
import pl.twerdenergoplus.charger.monitor.dto.LoginRequestDto;
import pl.twerdenergoplus.charger.monitor.dto.TokenResponseDto;
import pl.twerdenergoplus.charger.monitor.service.AuthService;
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
    @PostMapping("/register")
    public ResponseEntity<AppUserDto> register(@RequestBody AppUserCreateDto dto) {
        return ResponseEntity.ok(authService.register(dto));
    }
}
