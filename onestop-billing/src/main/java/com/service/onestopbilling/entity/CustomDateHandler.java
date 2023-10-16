package com.service.onestopbilling.entity;

import lombok.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Data
public class CustomDateHandler {
    private Date endDate;

    public CustomDateHandler() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            this.endDate = formatter.parse("31-10-2023");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setEndDate(String endDateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date endDate = formatter.parse(endDateString);
            this.endDate = endDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void increaseEndDateBy30Days() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.endDate);
        calendar.add(Calendar.DATE, 30);
        this.endDate = calendar.getTime();
    }
}

