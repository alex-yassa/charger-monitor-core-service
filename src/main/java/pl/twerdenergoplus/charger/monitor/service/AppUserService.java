package pl.twerdenergoplus.charger.monitor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.twerdenergoplus.charger.monitor.dto.AppUserCreateDto;
import pl.twerdenergoplus.charger.monitor.dto.AppUserDto;
import pl.twerdenergoplus.charger.monitor.entity.AppUser;
import pl.twerdenergoplus.charger.monitor.mapper.AppUserMapper;
import pl.twerdenergoplus.charger.monitor.repository.AppUserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final AppUserMapper appUserMapper;

    public List<AppUserDto> findAll() {
        return appUserRepository.findAll().stream()
                .map(appUserMapper::toDto)
                .toList();
    }

    public AppUserDto findById(Long id) {
        return appUserRepository.findById(id)
                .map(appUserMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("AppUser not found with id: " + id));
    }

    @Transactional
    public AppUserDto create(AppUserCreateDto dto) {
        AppUser entity = appUserMapper.toEntity(dto);
        return appUserMapper.toDto(appUserRepository.save(entity));
    }

    @Transactional
    public AppUserDto update(Long id, AppUserCreateDto dto) {
        AppUser entity = appUserRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("AppUser not found with id: " + id));
        appUserMapper.updateEntity(dto, entity);
        return appUserMapper.toDto(appUserRepository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        if (!appUserRepository.existsById(id)) {
            throw new NoSuchElementException("AppUser not found with id: " + id);
        }
        appUserRepository.deleteById(id);
    }
}

