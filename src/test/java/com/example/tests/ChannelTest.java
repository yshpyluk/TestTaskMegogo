package com.example.tests;

import org.example.client.ChannelClient;
import org.example.model.ResponseMultiDataEntity;
import org.example.model.channel.ChannelEntity;
import org.example.model.channel.ProgramEntity;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ChannelTest {

    ChannelClient channelClient;

    @BeforeClass
    public void setup() {
        channelClient = new ChannelClient();
    }

    @Test
    public void testChannelsHaveProgramsSortedByStartTimeStamp() {
        ResponseMultiDataEntity<ChannelEntity> response =  channelClient.getChannel(List.of(1639111, 1585681, 1639231));

        assertThat(response.getData()).hasSize(3);
        response.getData()
                .forEach(channel -> {
                    List<Long> programsStartTimeStamps = channel.getPrograms().stream()
                            .map(ProgramEntity::getStart_timestamp)
                            .toList();
                    assertThat(programsStartTimeStamps)
                            .as("Programs are not sorted in channel: " + channel.getTitle())
                            .isSorted();
                });
    }

    @Test
    public void testChannelsHaveOngoingProgram() {
        ResponseMultiDataEntity<ChannelEntity> response =  channelClient.getChannel(List.of(1639111, 1585681, 1639231));

        assertThat(response.getData()).hasSize(3);
        response.getData()
                .forEach(channel -> {
                    boolean isOngoingProgram = channel.getPrograms().stream()
                            .anyMatch(program -> {
                                        long currentTimeStamp = Instant.now().getEpochSecond();
                                        return program.getStart_timestamp() < currentTimeStamp &&
                                                currentTimeStamp < program.getEnd_timestamp();
                                    });
                    assertThat(isOngoingProgram)
                            .as("Following channel doesn't have ongoing program: " + channel.getTitle())
                            .isTrue();
                });
    }

    @Test
    public void testChannelsDoNotHaveProgramsFromPast() {
        ResponseMultiDataEntity<ChannelEntity> response =  channelClient.getChannel(List.of(1639111, 1585681, 1639231));

        assertThat(response.getData()).hasSize(3);
        response.getData()
                .forEach(channel -> {
                    boolean hasProgramFromThePast = channel.getPrograms().stream()
                            .anyMatch(program -> {
                                long currentTimeStamp = Instant.now().getEpochSecond();
                                return program.getEnd_timestamp() < currentTimeStamp;
                            });
                    assertThat(hasProgramFromThePast)
                            .as("Following channel has program from the past: " + channel.getTitle())
                            .isFalse();
                });
    }

    @Test
    public void testChannelsDoNotHaveProgramWhichStartsIn24Hours() {
        ResponseMultiDataEntity<ChannelEntity> response =  channelClient.getChannel(List.of(1639111, 1585681, 1639231));

        assertThat(response.getData()).hasSize(3);
        response.getData()
                .forEach(channel -> {
                    boolean hasProgramFromFarFuture = channel.getPrograms().stream()
                            .anyMatch(program -> {
                                long timeInTheFuture = Instant.now().plus(Duration.ofHours(24)).getEpochSecond();
                                return program.getStart_timestamp() > timeInTheFuture;
                            });
                    assertThat(hasProgramFromFarFuture)
                            .as("Following channel has program which starts in more than 24 hours " + channel.getTitle())
                            .isFalse();
                });
    }
}
