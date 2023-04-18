package com.example.demo.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.example.demo.service.LocalDateTimeDeserializer;
import java.time.LocalDateTime;

public class DateTimeRange {
    @JsonProperty("startDatetime")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startDatetime;

    @JsonProperty("endDatetime")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endDatetime;

    public LocalDateTime getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(LocalDateTime startDatetime) {
        this.startDatetime = startDatetime;
    }

    public LocalDateTime getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(LocalDateTime endDatetime) {
        this.endDatetime = endDatetime;
    }
}



