package aom.credito.challenge.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Value("${kafka.topic.credito:creditos-consulta}")
    private String creditoTopic;

    // How long to wait when adapting a raw Future by calling get(). Tunable if needed.
    private static final long ADAPT_GET_TIMEOUT_SECONDS = 30;

    /**
     * Publish a small event to Kafka containing the lookup keys and a timestamp.
     * This method is non-blocking and will log success/failure asynchronously.
     */
    public void publishConsultaEvent(String numeroNfse, String numeroCredito) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("numeroNfse", numeroNfse);
        payload.put("numeroCredito", numeroCredito);
        payload.put("timestamp", Instant.now().toString());

        try {

            final String message = OBJECT_MAPPER.writeValueAsString(payload);

            kafkaTemplate.send(creditoTopic, UUID.randomUUID().toString(), message);

        } catch (JsonProcessingException e) {
            log.error("Failed to serialize consulta event", e);
        } catch (Exception e) {
            log.error("Unexpected error when publishing consulta event", e);
        }
    }
}
