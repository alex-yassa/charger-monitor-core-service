package pl.twerdenergoplus.charger.monitor.mapper;

import org.mapstruct.Mapper;
import pl.twerdenergoplus.charger.monitor.dto.RoleDto;
import pl.twerdenergoplus.charger.monitor.entity.Role;

@Mapper(componentModel = "spring", uses = {AuthorityMapper.class})
public interface RoleMapper {

    RoleDto toDto(Role entity);
}
