package com.upload.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbResponseData {

    private String fileName;
    private String downloadURL;
    private String fileType;
    private long fileSize;
}
