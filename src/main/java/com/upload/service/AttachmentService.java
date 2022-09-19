package com.upload.service;

import com.upload.entity.Attachment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {

    public enum Target {
        HDD, DB
    }

     ResponseEntity<?> upload (@RequestParam(value = "target") Target target, @RequestParam("file") MultipartFile file) throws Exception;

        Attachment saveAttachmentToDb(MultipartFile file) throws Exception;

    Attachment getAttachment(String fileId) throws Exception;

    String saveAttachmentToLocal(MultipartFile file) throws Exception;



}
