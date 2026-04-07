package pl.twerdenergoplus.charger.monitor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.twerdenergoplus.charger.monitor.config.HazelcastConfiguration;
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

    @Cacheable(cacheNames = HazelcastConfiguration.CACHE_APP_USER, key = "'all'")
    public List<AppUserDto> findAll() {
        return appUserRepository.findAll().stream()
                .map(appUserMapper::toDto)
                .toList();
    }

    @Cacheable(cacheNames = HazelcastConfiguration.CACHE_APP_USER, key = "#id")
    public AppUserDto findById(Long id) {
        return appUserRepository.findById(id)
                .map(appUserMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("AppUser not found with id: " + id));
    }

    @Transactional
    @CacheEvict(cacheNames = HazelcastConfiguration.CACHE_APP_USER, key = "'all'")
    public AppUserDto create(AppUserCreateDto dto) {
        AppUser entity = appUserMapper.toEntity(dto);
        return appUserMapper.toDto(appUserRepository.save(entity));
    }

    @Transactional
    @CachePut(cacheNames = HazelcastConfiguration.CACHE_APP_USER, key = "#id")
    @CacheEvict(cacheNames = HazelcastConfiguration.CACHE_APP_USER, key = "'all'")
    public AppUserDto update(Long id, AppUserCreateDto dto) {
        AppUser entity = appUserRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("AppUser not found with id: " + id));
        appUserMapper.updateEntity(dto, entity);
        return appUserMapper.toDto(appUserRepository.save(entity));
    }

    @Transactional
    @CacheEvict(cacheNames = HazelcastConfiguration.CACHE_APP_USER, allEntries = true)
    public void delete(Long id) {
        if (!appUserRepository.existsById(id)) {
            throw new NoSuchElementException("AppUser not found with id: " + id);
        }
        appUserRepository.deleteById(id);
    }
}
