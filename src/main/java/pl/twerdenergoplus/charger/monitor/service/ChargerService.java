package pl.twerdenergoplus.charger.monitor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.twerdenergoplus.charger.monitor.dto.ChargerCreateDto;
import pl.twerdenergoplus.charger.monitor.dto.ChargerDto;
import pl.twerdenergoplus.charger.monitor.entity.Charger;
import pl.twerdenergoplus.charger.monitor.entity.ChargerType;
import pl.twerdenergoplus.charger.monitor.mapper.ChargerMapper;
import pl.twerdenergoplus.charger.monitor.repository.ChargerRepository;
import pl.twerdenergoplus.charger.monitor.repository.ChargerTypeRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChargerService {

    private final ChargerRepository chargerRepository;
    private final ChargerTypeRepository chargerTypeRepository;
    private final ChargerMapper chargerMapper;

    public List<ChargerDto> findAll() {
        return chargerRepository.findAll().stream()
                .map(chargerMapper::toDto)
                .toList();
    }

    public ChargerDto findById(Long id) {
        return chargerRepository.findById(id)
                .map(chargerMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("Charger not found with id: " + id));
    }

    @Transactional
    public ChargerDto create(ChargerCreateDto dto) {
        ChargerType type = chargerTypeRepository.findById(dto.getTypeId())
                .orElseThrow(() -> new NoSuchElementException("ChargerType not found with id: " + dto.getTypeId()));
        Charger entity = chargerMapper.toEntity(dto);
        entity.setType(type);
        return chargerMapper.toDto(chargerRepository.save(entity));
    }

    @Transactional
    public ChargerDto update(Long id, ChargerCreateDto dto) {
        Charger entity = chargerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Charger not found with id: " + id));
        ChargerType type = chargerTypeRepository.findById(dto.getTypeId())
                .orElseThrow(() -> new NoSuchElementException("ChargerType not found with id: " + dto.getTypeId()));
        chargerMapper.updateEntity(dto, entity);
        entity.setType(type);
        return chargerMapper.toDto(chargerRepository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        if (!chargerRepository.existsById(id)) {
            throw new NoSuchElementException("Charger not found with id: " + id);
        }
        chargerRepository.deleteById(id);
    }
}

