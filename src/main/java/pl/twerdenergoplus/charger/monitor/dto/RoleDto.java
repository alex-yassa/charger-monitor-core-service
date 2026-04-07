package pl.twerdenergoplus.charger.monitor.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RoleDto implements Serializable {

    private Long id;
    private String name;
    private List<AuthorityDto> authorities;
}

