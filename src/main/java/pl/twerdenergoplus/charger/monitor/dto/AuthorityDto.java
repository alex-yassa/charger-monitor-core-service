package pl.twerdenergoplus.charger.monitor.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthorityDto implements Serializable {

    private Long id;
    private String name;
}

