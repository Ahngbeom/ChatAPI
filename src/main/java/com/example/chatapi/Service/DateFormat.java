package com.example.chatapi.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DateFormat {

    public String dataToKor(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG));
    }
}
