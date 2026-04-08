# How to Enable Kafka

Kafka integration is present in the codebase but **disabled by default**.  
All Kafka beans (`KafkaConfig`, `KafkaService`, `KafkaMessageHandler`) are guarded by
`@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")`, so they are
simply not instantiated when the flag is `false`.

---

## Steps to Enable

### 1. Set the flag in the active properties file

Open the properties file that matches your active Spring profile:

| Profile   | File                              |
|-----------|-----------------------------------|
| `develop` | `application-develop.properties`  |
| `prod`    | `application-prod.properties`     |

Change:
```ini
kafka.enabled=false
```
to:
```ini
kafka.enabled=true
```

### 2. Verify the Kafka connection settings

Make sure the rest of the Kafka properties point to your broker:

```ini
kafka.bootstrap-servers=localhost:9092
kafka.group-id=monitor-group
kafka.client-id=monitor-client
kafka.default-topic=monitor
kafka.auto-offset-reset=earliest
kafka.enable-auto-commit=true
```

Adjust `kafka.bootstrap-servers` if your Kafka broker runs on a different host or port.

### 3. Make sure a Kafka broker is running

If you are using the project's `docker-compose.yml`, start the broker with:

```bash
docker compose up -d kafka
```

If Kafka is not included in the compose file yet, add a service or point
`kafka.bootstrap-servers` to an existing broker.

### 4. Start the application

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=develop
```

On startup you should see log lines similar to:

```
INFO  KafkaConfig      - Creating Kafka ProducerFactory ...
INFO  KafkaConfig      - Creating Kafka ConsumerFactory ...
```

---

## How to Disable Again

Set `kafka.enabled=false` (or remove the property entirely — it defaults to `false`
in `KafkaProperties`) and restart the application.

---

## Adding Business Logic

| Class                  | Purpose                                      |
|------------------------|----------------------------------------------|
| `KafkaMessageHandler`  | Receives inbound messages — add logic here   |
| `KafkaService`         | Publishes messages — inject and call `publish()` |
| `KafkaConfig`          | Bean wiring — edit only for infra changes    |
| `KafkaProperties`      | Typed config — add new properties here       |

### Publishing a message

```java
@Service
@RequiredArgsConstructor
public class MyService {

    private final KafkaService kafkaService;

    public void doSomething() {
        // send to the default topic (kafka.default-topic)
        kafkaService.publish("hello from MyService");

        // send to a specific topic
        kafkaService.publish("my-topic", "payload");

        // send with a key
        kafkaService.publish("my-topic", "my-key", "payload");
    }
}
```

### Consuming a message

Extend `KafkaMessageHandler.handleKafkaMessage()` or create a new `@KafkaListener`
method inside the same class:

```java
@KafkaListener(topics = "another-topic", groupId = "${kafka.group-id}")
public void handleAnotherTopic(ConsumerRecord<String, String> record) {
    log.info("Received: {}", record.value());
    // your business logic here
}
```

