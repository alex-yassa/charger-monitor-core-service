package pl.twerdenergoplus.charger.monitor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.twerdenergoplus.charger.monitor.entity.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
}

