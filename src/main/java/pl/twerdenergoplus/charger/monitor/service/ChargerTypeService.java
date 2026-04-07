package pl.twerdenergoplus.charger.monitor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public List<ChargerTypeDto> findAll() {
        return chargerTypeRepository.findAll().stream()
                .map(chargerTypeMapper::toDto)
                .toList();
    }

    public ChargerTypeDto findById(Long id) {
        return chargerTypeRepository.findById(id)
                .map(chargerTypeMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("ChargerType not found with id: " + id));
    }

    @Transactional
    public ChargerTypeDto create(ChargerTypeCreateDto dto) {
        ChargerType entity = chargerTypeMapper.toEntity(dto);
        return chargerTypeMapper.toDto(chargerTypeRepository.save(entity));
    }

    @Transactional
    public ChargerTypeDto update(Long id, ChargerTypeCreateDto dto) {
        ChargerType entity = chargerTypeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ChargerType not found with id: " + id));
        chargerTypeMapper.updateEntity(dto, entity);
        return chargerTypeMapper.toDto(chargerTypeRepository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        if (!chargerTypeRepository.existsById(id)) {
            throw new NoSuchElementException("ChargerType not found with id: " + id);
        }
        chargerTypeRepository.deleteById(id);
    }
}

