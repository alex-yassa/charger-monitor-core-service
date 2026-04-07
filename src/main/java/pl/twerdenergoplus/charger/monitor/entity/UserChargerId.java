package pl.twerdenergoplus.charger.monitor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class UserChargerId implements Serializable {

    @Column(name = "user_id_fk", nullable = false)
    private Long userId;

    @Column(name = "charger_id_fk", nullable = false)
    private Long chargerId;
}

