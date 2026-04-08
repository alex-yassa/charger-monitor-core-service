package pl.twerdenergoplus.charger.monitor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pl.twerdenergoplus.charger.monitor.dto.DataShmFileCreateDto;
import pl.twerdenergoplus.charger.monitor.dto.DataShmFileDto;
import pl.twerdenergoplus.charger.monitor.entity.DataShmFile;

@Mapper(componentModel = "spring")
public interface DataShmFileMapper {

    DataShmFileDto toDto(DataShmFile entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "shmData", ignore = true)
    DataShmFile toEntity(DataShmFileCreateDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "shmData", ignore = true)
    void updateEntity(DataShmFileCreateDto dto, @MappingTarget DataShmFile entity);
}

