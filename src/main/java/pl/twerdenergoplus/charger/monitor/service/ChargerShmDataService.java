package pl.twerdenergoplus.charger.monitor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.twerdenergoplus.charger.monitor.dto.ChargerShmDataCreateDto;
import pl.twerdenergoplus.charger.monitor.dto.ChargerShmDataDto;
import pl.twerdenergoplus.charger.monitor.entity.Charger;
import pl.twerdenergoplus.charger.monitor.entity.ChargerShmData;
import pl.twerdenergoplus.charger.monitor.entity.ChargerShmDataId;
import pl.twerdenergoplus.charger.monitor.entity.DataShmFile;
import pl.twerdenergoplus.charger.monitor.mapper.ChargerShmDataMapper;
import pl.twerdenergoplus.charger.monitor.repository.ChargerRepository;
import pl.twerdenergoplus.charger.monitor.repository.ChargerShmDataRepository;
import pl.twerdenergoplus.charger.monitor.repository.DataShmFileRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChargerShmDataService {

    private final ChargerShmDataRepository chargerShmDataRepository;
    private final ChargerRepository chargerRepository;
    private final DataShmFileRepository dataShmFileRepository;
    private final ChargerShmDataMapper chargerShmDataMapper;

    public List<ChargerShmDataDto> findAll() {
        return chargerShmDataRepository.findAll().stream()
                .map(chargerShmDataMapper::toDto)
                .toList();
    }

    public List<ChargerShmDataDto> findByChargerId(Long chargerId) {
        return chargerShmDataRepository.findByIdChargerId(chargerId).stream()
                .map(chargerShmDataMapper::toDto)
                .toList();
    }

    public ChargerShmDataDto findById(Long chargerId, Long dataShmFileId, Long timestamp) {
        ChargerShmDataId id = buildId(chargerId, dataShmFileId, timestamp);
        return chargerShmDataRepository.findById(id)
                .map(chargerShmDataMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("ChargerShmData not found for the given composite key"));
    }

    @Transactional
    public ChargerShmDataDto create(ChargerShmDataCreateDto dto) {
        Charger charger = chargerRepository.findById(dto.getChargerId())
                .orElseThrow(() -> new NoSuchElementException("Charger not found with id: " + dto.getChargerId()));
        DataShmFile dataShmFile = dataShmFileRepository.findById(dto.getDataShmFileId())
                .orElseThrow(() -> new NoSuchElementException("DataShmFile not found with id: " + dto.getDataShmFileId()));
        ChargerShmData entity = chargerShmDataMapper.toEntity(dto);
        entity.setCharger(charger);
        entity.setDataShmFile(dataShmFile);
        return chargerShmDataMapper.toDto(chargerShmDataRepository.save(entity));
    }

    @Transactional
    public void delete(Long chargerId, Long dataShmFileId, Long timestamp) {
        ChargerShmDataId id = buildId(chargerId, dataShmFileId, timestamp);
        if (!chargerShmDataRepository.existsById(id)) {
            throw new NoSuchElementException("ChargerShmData not found for the given composite key");
        }
        chargerShmDataRepository.deleteById(id);
    }

    private ChargerShmDataId buildId(Long chargerId, Long dataShmFileId, Long timestamp) {
        ChargerShmDataId id = new ChargerShmDataId();
        id.setChargerId(chargerId);
        id.setDataShmFileId(dataShmFileId);
        id.setTimestamp(timestamp);
        return id;
    }
}

