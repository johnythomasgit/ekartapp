/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.util.storage;


import com.amazonaws.services.elastictranscoder.model.Job;
import com.amazonaws.services.elastictranscoder.model.JobOutput;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author nirmal
 */
public interface Storage {

    public static class Info {

        public final String path;
        public final String url;

        protected Info(String path, String url) {
            this.path = path;
            this.url = url;
        }
    }

    public void setBasePath(String path);

    public String getBasePath();

    public void setBaseUrl(String url);

    public String getBaseUrl();
    
    public Info saveImage(String name, MultipartFile file, boolean isPublic) throws IOException;
    
    public Info save(String name, MultipartFile file, boolean isPublic) throws IOException;

    public Info save(String name, InputStream in, String contentType, boolean isPublic) throws IOException;

    public Info save(String name, byte[] data, String contentType, boolean isPublic) throws IOException;
    
    public Info save(String name, byte[] data, String contentType, boolean isPublic, boolean append) throws IOException;
    
    public Info save(String name, File file, boolean isPUblic) throws IOException;

    public InputStream get(String path) throws IOException;

    public void delete(String path) throws IOException;

    public void copy(String srcPath, String dstPath) throws IOException;

    public Info move(String srcPath, String dstPath, Boolean isPublic) throws IOException;

    public List<String> list(String subFolder);

    public String getTempUrl(String relativePath);
    
    public boolean isValidImage(MultipartFile file);
    
    public void download(String source, String destination, boolean isPublic)throws IOException;
    
    public void downloadThumbnail(String source, String destination, boolean isPublic)throws IOException;
    
    public void downloadAllFiles(String source, String destination, boolean isPublic)throws IOException;
    
    public Job jobCreation(String input);
    public Job jobCreation1(String input);
    public Info uploadBufferedImageToServer(BufferedImage image, String fileName, String imageType) throws IOException, NullPointerException;
    public Info uploadFileTos3bucket(String fileName, File file) throws JsonProcessingException;
    
    
    public JobOutput getJobDetails(String id);
}
