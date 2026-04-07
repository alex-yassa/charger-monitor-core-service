package pl.twerdenergoplus.charger.monitor.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChargerDto implements Serializable {

    private Long id;
    private String name;
    private Long typeId;
    private String typeName;
}

