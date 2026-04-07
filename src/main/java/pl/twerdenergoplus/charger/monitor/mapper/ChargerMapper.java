package pl.twerdenergoplus.charger.monitor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pl.twerdenergoplus.charger.monitor.dto.ChargerCreateDto;
import pl.twerdenergoplus.charger.monitor.dto.ChargerDto;
import pl.twerdenergoplus.charger.monitor.entity.Charger;

@Mapper(componentModel = "spring")
public interface ChargerMapper {

    @Mapping(target = "typeId", source = "type.id")
    @Mapping(target = "typeName", source = "type.name")
    ChargerDto toDto(Charger entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "userChargers", ignore = true)
    @Mapping(target = "shmData", ignore = true)
    Charger toEntity(ChargerCreateDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "userChargers", ignore = true)
    @Mapping(target = "shmData", ignore = true)
    void updateEntity(ChargerCreateDto dto, @MappingTarget Charger entity);
}

