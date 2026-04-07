package pl.twerdenergoplus.charger.monitor.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pl.twerdenergoplus.charger.monitor.dto.AuthorityCreateDto;
import pl.twerdenergoplus.charger.monitor.dto.AuthorityDto;
import pl.twerdenergoplus.charger.monitor.entity.Authority;
@Mapper(componentModel = "spring")
public interface AuthorityMapper {
    AuthorityDto toDto(Authority entity);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    Authority toEntity(AuthorityCreateDto dto);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void updateEntity(AuthorityCreateDto dto, @MappingTarget Authority entity);
}
