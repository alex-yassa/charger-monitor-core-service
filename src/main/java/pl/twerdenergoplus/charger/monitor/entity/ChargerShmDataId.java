package pl.twerdenergoplus.charger.monitor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class ChargerShmDataId implements Serializable {

    @Column(name = "charger_id_fk", nullable = false)
    private Long chargerId;

    @Column(name = "data_shm_file_id_fk", nullable = false)
    private Long dataShmFileId;

    @Column(name = "timestamp", nullable = false)
    private Long timestamp;
}

