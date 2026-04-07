package pl.twerdenergoplus.charger.monitor.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "charger_shm_data")
public class ChargerShmData {

    @EmbeddedId
    private ChargerShmDataId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("chargerId")
    @JoinColumn(name = "charger_id_fk", nullable = false)
    private Charger charger;

    @Column(nullable = false)
    private Integer data;

    @Column(name = "received_at", nullable = false, updatable = false)
    private LocalDateTime receivedAt = LocalDateTime.now();
}

