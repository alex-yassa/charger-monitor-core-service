package pl.twerdenergoplus.charger.monitor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.twerdenergoplus.charger.monitor.config.HazelcastConfiguration;
import pl.twerdenergoplus.charger.monitor.dto.DataShmFileCreateDto;
import pl.twerdenergoplus.charger.monitor.dto.DataShmFileDto;
import pl.twerdenergoplus.charger.monitor.entity.DataShmFile;
import pl.twerdenergoplus.charger.monitor.mapper.DataShmFileMapper;
import pl.twerdenergoplus.charger.monitor.repository.DataShmFileRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DataShmFileService {

    private final DataShmFileRepository dataShmFileRepository;
    private final DataShmFileMapper dataShmFileMapper;

    @Cacheable(cacheNames = HazelcastConfiguration.CACHE_DATA_SHM_FILE, key = "'all'")
    public List<DataShmFileDto> findAll() {
        return dataShmFileRepository.findAll().stream()
                .map(dataShmFileMapper::toDto)
                .toList();
    }

    @Cacheable(cacheNames = HazelcastConfiguration.CACHE_DATA_SHM_FILE, key = "#id")
    public DataShmFileDto findById(Long id) {
        return dataShmFileRepository.findById(id)
                .map(dataShmFileMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("DataShmFile not found with id: " + id));
    }

    @Transactional
    @CacheEvict(cacheNames = HazelcastConfiguration.CACHE_DATA_SHM_FILE, key = "'all'")
    public DataShmFileDto create(DataShmFileCreateDto dto) {
        DataShmFile entity = dataShmFileMapper.toEntity(dto);
        return dataShmFileMapper.toDto(dataShmFileRepository.save(entity));
    }

    @Transactional
    @Caching(
        put    = @CachePut(cacheNames = HazelcastConfiguration.CACHE_DATA_SHM_FILE, key = "#id"),
        evict  = @CacheEvict(cacheNames = HazelcastConfiguration.CACHE_DATA_SHM_FILE, key = "'all'")
    )
    public DataShmFileDto update(Long id, DataShmFileCreateDto dto) {
        DataShmFile entity = dataShmFileRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("DataShmFile not found with id: " + id));
        dataShmFileMapper.updateEntity(dto, entity);
        return dataShmFileMapper.toDto(dataShmFileRepository.save(entity));
    }

    @Transactional
    @CacheEvict(cacheNames = HazelcastConfiguration.CACHE_DATA_SHM_FILE, allEntries = true)
    public void delete(Long id) {
        if (!dataShmFileRepository.existsById(id)) {
            throw new NoSuchElementException("DataShmFile not found with id: " + id);
        }
        dataShmFileRepository.deleteById(id);
    }
}

