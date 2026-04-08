package pl.twerdenergoplus.charger.monitor.config;

import com.hazelcast.config.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {

    public static final String CACHE_APP_USER     = "appUser";
    public static final String CACHE_CHARGER      = "charger";
    public static final String CACHE_CHARGER_TYPE = "chargerType";
    public static final String CACHE_DATA_SHM_FILE = "dataShmFile";

    @Bean
    public Config hazelcastConfig() {
        Config config = new Config();
        config.setInstanceName("charger-monitor-hz");

        config.getNetworkConfig()
                .getJoin()
                .getMulticastConfig()
                .setEnabled(false);

        config.getNetworkConfig()
                .getJoin()
                .getTcpIpConfig()
                .setEnabled(false);

        MapConfig defaults = new MapConfig()
                .setEvictionConfig(
                        new EvictionConfig()
                                .setEvictionPolicy(EvictionPolicy.LRU)
                                .setMaxSizePolicy(MaxSizePolicy.PER_NODE)
                                .setSize(1000)
                )
                .setTimeToLiveSeconds(600)
                .setMaxIdleSeconds(300);

        config.addMapConfig(new MapConfig(defaults).setName(CACHE_APP_USER));
        config.addMapConfig(new MapConfig(defaults).setName(CACHE_CHARGER));
        config.addMapConfig(new MapConfig(defaults).setName(CACHE_CHARGER_TYPE));
        config.addMapConfig(new MapConfig(defaults).setName(CACHE_DATA_SHM_FILE));

        return config;
    }
}

