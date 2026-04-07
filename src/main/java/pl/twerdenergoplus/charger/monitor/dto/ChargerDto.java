package pl.twerdenergoplus.charger.monitor.dto;

import lombok.Data;

@Data
public class ChargerDto {

    private Long id;
    private String name;
    private Long typeId;
    private String typeName;
}

