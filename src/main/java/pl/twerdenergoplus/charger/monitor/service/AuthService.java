package pl.twerdenergoplus.charger.monitor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.twerdenergoplus.charger.monitor.dto.AppUserCreateDto;
import pl.twerdenergoplus.charger.monitor.dto.AppUserDto;
import pl.twerdenergoplus.charger.monitor.dto.LoginRequestDto;
import pl.twerdenergoplus.charger.monitor.dto.TokenResponseDto;
import pl.twerdenergoplus.charger.monitor.entity.AppUser;
import pl.twerdenergoplus.charger.monitor.mapper.AppUserMapper;
import pl.twerdenergoplus.charger.monitor.repository.AppUserRepository;
import pl.twerdenergoplus.charger.monitor.security.JwtUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final AppUserMapper appUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public TokenResponseDto login(LoginRequestDto dto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        AppUser user = (AppUser) auth.getPrincipal();
        String token = jwtUtil.generateToken(user);
        List<String> authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new TokenResponseDto(token, user.getUsername(), authorities);
    }

    @Transactional
    public AppUserDto register(AppUserCreateDto dto) {
        if (appUserRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Username already taken: " + dto.getName());
        }
        AppUser entity = appUserMapper.toEntity(dto);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        return appUserMapper.toDto(appUserRepository.save(entity));
    }
}

