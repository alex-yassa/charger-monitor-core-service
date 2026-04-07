package pl.twerdenergoplus.charger.monitor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pl.twerdenergoplus.charger.monitor.dto.ChargerTypeCreateDto;
import pl.twerdenergoplus.charger.monitor.dto.ChargerTypeDto;
import pl.twerdenergoplus.charger.monitor.entity.ChargerType;

@Mapper(componentModel = "spring")
public interface ChargerTypeMapper {

    ChargerTypeDto toDto(ChargerType entity);

    @Mapping(target = "id", ignore = true)
    ChargerType toEntity(ChargerTypeCreateDto dto);

    @Mapping(target = "id", ignore = true)
    void updateEntity(ChargerTypeCreateDto dto, @MappingTarget ChargerType entity);
}

