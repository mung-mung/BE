package api.common.util.typeConverter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.Duration;

@Converter(autoApply = true)
public class DurationConverter implements AttributeConverter<Duration, String> {

    @Override
    public String convertToDatabaseColumn(Duration attribute) {
        if (attribute == null) {
            return null;
        }

        long seconds = attribute.getSeconds();
        int nanos = attribute.getNano();
        return String.format("%d seconds %d nanoseconds", seconds, nanos);
    }

    @Override
    public Duration convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }

        String[] parts = dbData.split(" ");
        long seconds = Long.parseLong(parts[0]);
        int nanos = Integer.parseInt(parts[2]);
        return Duration.ofSeconds(seconds, nanos);
    }
}
