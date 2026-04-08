package pl.twerdenergoplus.charger.monitor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.twerdenergoplus.charger.monitor.entity.DataShmFile;

@Repository
public interface DataShmFileRepository extends JpaRepository<DataShmFile, Long> {
}

