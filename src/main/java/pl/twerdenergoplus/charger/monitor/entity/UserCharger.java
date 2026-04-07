package pl.twerdenergoplus.charger.monitor.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_charger")
public class UserCharger {

    @EmbeddedId
    private UserChargerId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id_fk", nullable = false)
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("chargerId")
    @JoinColumn(name = "charger_id_fk", nullable = false)
    private Charger charger;
}

