package org.example.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateUtil {

    public ZonedDateTime getCurrentZonedDateTime(ZoneId timezone) {
        return formatZonedDateTime(Instant.now().getEpochSecond(),
                timezone.getId());
    }

    public ZonedDateTime formatZonedDateTime(long timestamp, String timezone) {
        Instant instant = Instant.ofEpochSecond(timestamp);
        if (timezone.contains("Kyiv")) {
            //Workaround because Java doesn't know how to write "Kyiv" correctly, unfortunately
            timezone = timezone.replace("Kyiv", "Kiev");
        }
        return instant.atZone(ZoneId.of(timezone));
    }
}
