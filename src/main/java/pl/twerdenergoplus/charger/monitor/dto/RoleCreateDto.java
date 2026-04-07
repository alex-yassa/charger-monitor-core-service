package pl.twerdenergoplus.charger.monitor.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleCreateDto {

    private String name;
    private List<Long> authorityIds;
}

