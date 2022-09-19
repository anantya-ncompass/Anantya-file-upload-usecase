package com.upload.service;

import com.upload.entity.Attachment;
import com.upload.model.DbResponseData;
import com.upload.model.LocalResponseData;
import com.upload.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.util.Date;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;


    private final String FOLDER_PATH="C:/Users/Anantya/Desktop/MyFiles/";


    public ResponseEntity<?> upload (Target target, MultipartFile file) throws Exception {
        if (target == Target.DB) {
            Attachment attachment = saveAttachmentToDb(file);
            String downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(attachment.getId())
                    .toUriString();

            DbResponseData dbResponseData = new DbResponseData(attachment.getFileName(),
                    downloadURl,
                    file.getContentType(),
                    file.getSize());

            return new ResponseEntity<>(dbResponseData, new HttpHeaders(), HttpStatus.OK);
        } else if (target == Target.HDD) {
            String uploadImage = saveAttachmentToLocal(file);
            LocalResponseData localResponseData = new LocalResponseData(new Date(), uploadImage);
            return new ResponseEntity<>(localResponseData, new HttpHeaders(), HttpStatus.OK);
        } else throw new Exception("Attachment cannot be uploaded.");

    }


    @Override
    public Attachment saveAttachmentToDb(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.isEmpty()) throw new Exception("Attachment is empty");
        try {
            if(fileName.contains("..")) {
                throw new Exception("Filename contains invalid path sequence "
                        + fileName);
            }

            Attachment attachment
                    = new Attachment(fileName,
                    file.getContentType(),
                    file.getBytes());
            return attachmentRepository.save(attachment);

        } catch (Exception e) {
            throw new Exception("Could not save File: " + fileName);
        }
    }

    @Override
    public Attachment getAttachment(String fileId) throws Exception {
        return attachmentRepository
                .findById(fileId)
                .orElseThrow(
                        () -> new Exception("File not found with Id: " + fileId));
    }

    @Override
    public String saveAttachmentToLocal(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        if (fileName.isEmpty()) throw new Exception("Attachment is empty");
        String filePath=FOLDER_PATH+fileName;
        file.transferTo(new File(filePath));
        return "file uploaded successfully : " + filePath;

    }



}
