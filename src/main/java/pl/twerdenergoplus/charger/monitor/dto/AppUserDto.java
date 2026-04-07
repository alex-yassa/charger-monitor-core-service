package pl.twerdenergoplus.charger.monitor.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AppUserDto implements Serializable {

    private Long id;
    private String name;
    private Boolean blocked;
    private LocalDateTime createdAt;
}

