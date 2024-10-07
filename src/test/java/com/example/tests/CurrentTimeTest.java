package com.example.tests;

import org.example.client.TimeClient;
import org.example.model.ResponseEntity;
import org.example.model.TimeEntity;
import org.example.util.DateUtil;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrentTimeTest {

    TimeClient timeClient;
    DateUtil dateUtil;

    @BeforeClass
    public void setup() {
        timeClient = new TimeClient();
        dateUtil = new DateUtil();
    }

    @Test
    public void testCurrentTimeIsCorrect() {
        ResponseEntity<TimeEntity> response = timeClient.getTime();
        ZonedDateTime actualZonedDateTime = dateUtil.formatZonedDateTime(
                response.getData().getTimestamp(),
                response.getData().getTimezone());

        ZonedDateTime expectedZoneDateTime = dateUtil.getCurrentZonedDateTime(ZoneId.systemDefault());

        assertThat(actualZonedDateTime)
                .isBeforeOrEqualTo(expectedZoneDateTime)
                .as("Actual date should be before expected");
        assertThat(actualZonedDateTime.truncatedTo(ChronoUnit.MINUTES))
                .isEqualTo(expectedZoneDateTime.truncatedTo(ChronoUnit.MINUTES))
                .as("Actual and expected dates should match with rounding to minutes");
    }
}
