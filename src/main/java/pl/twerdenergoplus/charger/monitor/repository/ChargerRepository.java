package pl.twerdenergoplus.charger.monitor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.twerdenergoplus.charger.monitor.entity.Charger;

@Repository
public interface ChargerRepository extends JpaRepository<Charger, Long> {
}

