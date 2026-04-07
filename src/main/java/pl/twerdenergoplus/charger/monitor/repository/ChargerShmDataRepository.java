package pl.twerdenergoplus.charger.monitor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.twerdenergoplus.charger.monitor.entity.ChargerShmData;
import pl.twerdenergoplus.charger.monitor.entity.ChargerShmDataId;

import java.util.List;

@Repository
public interface ChargerShmDataRepository extends JpaRepository<ChargerShmData, ChargerShmDataId> {

    List<ChargerShmData> findByIdChargerId(Long chargerId);
}

