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

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("dataShmFileId")
    @JoinColumn(name = "data_shm_file_id_fk", nullable = false)
    private DataShmFile dataShmFile;

    @Column(nullable = false)
    private Integer index;

    @Column(nullable = false)
    private Integer data;

    @Column(name = "received_at", nullable = false, updatable = false)
    private LocalDateTime receivedAt = LocalDateTime.now();
}

