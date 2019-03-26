package org.athena.common.util;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TimeUtilTest {

    private LocalDateTime time = LocalDateTime.of(1992, 7, 2, 23, 59, 59);

    private LocalDate date = LocalDate.of(1992, 7, 2);

    private String timeStr = "1992-07-02 23:59:59";

    private String dateStr = "1992-07-02";

    @Test
    public void parseLocalDateTime() {

        LocalDateTime parseLocalDateTime = TimeUtil.parseLocalDateTime(this.timeStr);

        assertEquals(this.time, parseLocalDateTime);

    }

    @Test
    public void formatLocalDateTime() {

        String timeStr = TimeUtil.formatLocalDateTime(this.time);

        assertEquals(this.timeStr, timeStr);

    }

    @Test
    public void parseLocalDate() {

        LocalDate parseLocalDate = TimeUtil.parseLocalDate(this.dateStr);

        assertEquals(this.date, parseLocalDate);

    }

    @Test
    public void formatLocalDate() {

        String dateStr = TimeUtil.formatLocalDate(this.date);

        assertEquals(this.dateStr, dateStr);

    }

    @Test
    public void validityPeriod() {

        LocalDateTime startTime = TimeUtil.parseLocalDateTime("1992-07-02 23:59:59");

        LocalDateTime endTime = TimeUtil.parseLocalDateTime("2093-02-03 00:00:00");

        assertTrue(TimeUtil.validityPeriod(LocalDateTime.now(), startTime, endTime));

    }

}