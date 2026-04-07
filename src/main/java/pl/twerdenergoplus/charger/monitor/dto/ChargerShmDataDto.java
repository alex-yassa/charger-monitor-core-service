package pl.twerdenergoplus.charger.monitor.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChargerShmDataDto {

    private Long chargerId;
    private Long dataShmFileId;
    private Long timestamp;
    private Integer data;
    private LocalDateTime receivedAt;
}

