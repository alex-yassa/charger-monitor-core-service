package pl.twerdenergoplus.charger.monitor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"spring.datasource.url=jdbc:postgresql://localhost:5432/charger_monitor",
		"spring.autoconfigure.exclude=" +
				"org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration," +
				"org.springframework.boot.orm.jpa.autoconfigure.HibernateJpaAutoConfiguration," +
				"org.springframework.boot.liquibase.autoconfigure.LiquibaseAutoConfiguration"
})
class MonitorApplicationTests {

	@Test
	void contextLoads() {
	}

}
