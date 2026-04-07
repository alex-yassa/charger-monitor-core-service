package pl.twerdenergoplus.charger.monitor.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "charger")
public class Charger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id_fk", nullable = false)
    private ChargerType type;

    @OneToMany(mappedBy = "charger", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCharger> userChargers;

    @OneToMany(mappedBy = "charger", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChargerShmData> shmData;
}

