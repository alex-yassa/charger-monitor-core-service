package pl.twerdenergoplus.charger.monitor.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import pl.twerdenergoplus.charger.monitor.mapper.ShmValueMapper;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InfluxService {
    private final InfluxDBClient client;
    private final String bucket;
    private final String org;

    public InfluxService(
            @Value("${influx.url}") String url,
            @Value("${influx.token}") String token,
            @Value("${influx.bucket}") String bucket,
            @Value("${influx.org}") String org) {
        this.client = InfluxDBClientFactory.create(url, token.toCharArray());
        this.bucket = bucket;
        this.org = org;
    }

    public void writeDeviceData(String deviceId, String shmFileName, String index, long timestamp, int data) {
        Instant instantTime = Instant.ofEpochMilli(timestamp);
        // Create a "Point" (this is one row in InfluxDB)
        Point point = Point
                .measurement(shmFileName)
                .addTag("device_id", deviceId)
                .addTag("index", index)
                .addField("data", data)
                .time(instantTime, WritePrecision.MS);

        // Enrich with a human-readable label for known enum/flag signals
        String label = ShmValueMapper.resolve(shmFileName, index, data);
        if (label != null) {
            point.addField("label", label);
        }

        WriteApiBlocking writeApi = client.getWriteApiBlocking();
        writeApi.writePoint(bucket, org, point);
    }
}