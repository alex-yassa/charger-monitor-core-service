package pl.twerdenergoplus.charger.monitor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.twerdenergoplus.charger.monitor.config.HazelcastConfiguration;
import pl.twerdenergoplus.charger.monitor.dto.ChargerTypeCreateDto;
import pl.twerdenergoplus.charger.monitor.dto.ChargerTypeDto;
import pl.twerdenergoplus.charger.monitor.entity.ChargerType;
import pl.twerdenergoplus.charger.monitor.mapper.ChargerTypeMapper;
import pl.twerdenergoplus.charger.monitor.repository.ChargerTypeRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChargerTypeService {

    private final ChargerTypeRepository chargerTypeRepository;
    private final ChargerTypeMapper chargerTypeMapper;

    @Cacheable(cacheNames = HazelcastConfiguration.CACHE_CHARGER_TYPE, key = "'all'")
    public List<ChargerTypeDto> findAll() {
        return chargerTypeRepository.findAll().stream()
                .map(chargerTypeMapper::toDto)
                .toList();
    }

    @Cacheable(cacheNames = HazelcastConfiguration.CACHE_CHARGER_TYPE, key = "#id")
    public ChargerTypeDto findById(Long id) {
        return chargerTypeRepository.findById(id)
                .map(chargerTypeMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("ChargerType not found with id: " + id));
    }

    @Transactional
    @CacheEvict(cacheNames = HazelcastConfiguration.CACHE_CHARGER_TYPE, key = "'all'")
    public ChargerTypeDto create(ChargerTypeCreateDto dto) {
        ChargerType entity = chargerTypeMapper.toEntity(dto);
        return chargerTypeMapper.toDto(chargerTypeRepository.save(entity));
    }

    @Transactional
    @CachePut(cacheNames = HazelcastConfiguration.CACHE_CHARGER_TYPE, key = "#id")
    @CacheEvict(cacheNames = HazelcastConfiguration.CACHE_CHARGER_TYPE, key = "'all'")
    public ChargerTypeDto update(Long id, ChargerTypeCreateDto dto) {
        ChargerType entity = chargerTypeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ChargerType not found with id: " + id));
        chargerTypeMapper.updateEntity(dto, entity);
        return chargerTypeMapper.toDto(chargerTypeRepository.save(entity));
    }

    @Transactional
    @CacheEvict(cacheNames = HazelcastConfiguration.CACHE_CHARGER_TYPE, allEntries = true)
    public void delete(Long id) {
        if (!chargerTypeRepository.existsById(id)) {
            throw new NoSuchElementException("ChargerType not found with id: " + id);
        }
        chargerTypeRepository.deleteById(id);
    }
}
