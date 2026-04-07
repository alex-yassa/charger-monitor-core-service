package pl.twerdenergoplus.charger.monitor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.twerdenergoplus.charger.monitor.dto.ChargerShmDataCreateDto;
import pl.twerdenergoplus.charger.monitor.dto.ChargerShmDataDto;
import pl.twerdenergoplus.charger.monitor.entity.ChargerShmData;
import pl.twerdenergoplus.charger.monitor.entity.ChargerShmDataId;

@Mapper(componentModel = "spring")
public interface ChargerShmDataMapper {

    @Mapping(target = "chargerId", source = "id.chargerId")
    @Mapping(target = "dataShmFileId", source = "id.dataShmFileId")
    @Mapping(target = "timestamp", source = "id.timestamp")
    ChargerShmDataDto toDto(ChargerShmData entity);

    @Mapping(target = "id", expression = "java(buildId(dto))")
    @Mapping(target = "charger", ignore = true)
    @Mapping(target = "index", ignore = true)
    @Mapping(target = "receivedAt", ignore = true)
    ChargerShmData toEntity(ChargerShmDataCreateDto dto);

    default ChargerShmDataId buildId(ChargerShmDataCreateDto dto) {
        ChargerShmDataId id = new ChargerShmDataId();
        id.setChargerId(dto.getChargerId());
        id.setDataShmFileId(dto.getDataShmFileId());
        id.setTimestamp(dto.getTimestamp());
        return id;
    }
}

