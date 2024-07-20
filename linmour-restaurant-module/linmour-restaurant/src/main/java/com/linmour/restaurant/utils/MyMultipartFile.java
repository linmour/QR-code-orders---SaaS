package com.linmour.restaurant.utils;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
 
 
public class MyMultipartFile implements MultipartFile {
 
    private final String name;
 
    private String originalFilename;
 
    private String contentType;
 
    private final byte[] content;
 
 
    public MyMultipartFile(String name, byte[] content) {
        this(name, "", null, content);
    }
 
 
    public MyMultipartFile(String name, InputStream contentStream) throws IOException {
        this(name, "", null, FileCopyUtils.copyToByteArray(contentStream));
    }
 
 
    public MyMultipartFile(String name, String originalFilename, String contentType, byte[] content) {
        this.name = name;
        this.originalFilename = (originalFilename != null ? originalFilename : "");
        this.contentType = contentType;
        this.content = (content != null ? content : new byte[0]);
    }
 
    public MyMultipartFile(String name, String originalFilename, String contentType, InputStream contentStream)
            throws IOException {
 
        this(name, originalFilename, contentType, FileCopyUtils.copyToByteArray(contentStream));
    }
 
    @Override
    public String getName() {
        return this.name;
    }
 
    @Override
    public String getOriginalFilename() {
        return this.originalFilename;
    }
 
    @Override
    public String getContentType() {
        return this.contentType;
    }
 
    @Override
    public boolean isEmpty() {
        return (this.content.length == 0);
    }
 
    @Override
    public long getSize() {
        return this.content.length;
    }
 
    @Override
    public byte[] getBytes() throws IOException {
        return this.content;
    }
 
    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(this.content);
    }
 
    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        FileCopyUtils.copy(this.content, dest);
    }
}