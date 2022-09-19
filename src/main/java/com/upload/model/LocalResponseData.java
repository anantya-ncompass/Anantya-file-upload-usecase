package com.upload.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
public class LocalResponseData {
    private Date timestamp;
    private String message;


    public LocalResponseData(Date timestamp, String message)
    {
        this.timestamp = timestamp;
        this.message = message;
    }

}