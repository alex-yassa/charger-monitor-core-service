package pl.twerdenergoplus.charger.monitor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TokenResponseDto {

    private String token;
    private String username;
    private List<String> authorities;
}

