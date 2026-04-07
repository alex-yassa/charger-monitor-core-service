package pl.twerdenergoplus.charger.monitor.dto;

import lombok.Data;

@Data
public class ChargerShmDataCreateDto {

    private Long chargerId;
    private Long dataShmFileId;
    private Long timestamp;
    private Integer data;
}

