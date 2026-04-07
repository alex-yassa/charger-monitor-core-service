package pl.twerdenergoplus.charger.monitor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pl.twerdenergoplus.charger.monitor.dto.AppUserCreateDto;
import pl.twerdenergoplus.charger.monitor.dto.AppUserDto;
import pl.twerdenergoplus.charger.monitor.entity.AppUser;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    AppUserDto toDto(AppUser entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "blocked", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "userChargers", ignore = true)
    AppUser toEntity(AppUserCreateDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "blocked", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "userChargers", ignore = true)
    void updateEntity(AppUserCreateDto dto, @MappingTarget AppUser entity);
}

