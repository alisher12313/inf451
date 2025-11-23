package com.pm.file_uploaddownload.service;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final GridFsTemplate gridFsTemplate;
    private final GridFsOperations gridFsOperations;

    public String uploadFile(MultipartFile file, UUID studentId, UUID teacherId) throws IOException {
        DBObject metaData = new BasicDBObject();
        metaData.put("contentType", file.getContentType());
        metaData.put("fileSize", file.getSize());
        metaData.put("studentId", studentId);
        metaData.put("teacherId", teacherId);
        metaData.put("uploadTime", LocalDateTime.now());

        ObjectId fileId = gridFsOperations.store(
                file.getInputStream(),
                file.getOriginalFilename(),
                file.getContentType(),
                metaData
        );

        return fileId.toString();
    }

    public GridFsResource downloadFile(String fileId) throws IOException {
        GridFSFile file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(new ObjectId(fileId))));
        if (file == null) throw new FileNotFoundException("File not found: " + fileId);
        return gridFsOperations.getResource(file);
    }
}
