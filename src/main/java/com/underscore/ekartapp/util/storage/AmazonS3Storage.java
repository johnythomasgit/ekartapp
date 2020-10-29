/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.util.storage;

import com.amazonaws.AmazonClientException;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.elastictranscoder.AmazonElasticTranscoder;
import com.amazonaws.services.elastictranscoder.AmazonElasticTranscoderClient;
import com.amazonaws.services.elastictranscoder.model.CreateJobOutput;
import com.amazonaws.services.elastictranscoder.model.CreateJobRequest;
import com.amazonaws.services.elastictranscoder.model.CreateJobResult;
import com.amazonaws.services.elastictranscoder.model.Job;
import com.amazonaws.services.elastictranscoder.model.JobInput;
import com.amazonaws.services.elastictranscoder.model.JobOutput;
import com.amazonaws.services.elastictranscoder.model.ReadJobRequest;
import com.amazonaws.services.elastictranscoder.model.ReadJobResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.MultipleFileDownload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


/**
 *
 * @author ajmal
 */
public class AmazonS3Storage implements Storage, InitializingBean {
    @Value("${aws.s3.bucket}")
    private String bucket;
    @Value("${aws.s3.region}")
    private String region;
    @Value("${aws.s3.url}")
    private String s3Url;
    @Value("${aws.s3.basepath}")
    private String basePath;
    @Value("${aws.s3.bucket}")
    private String cloudBucket;
    @Value("${aws.access.key}")
    private String accessKey;
    @Value("${aws.access.secret}")
    private String accessSecret;

    private String outputPath;
//    private String presetId = "1351620000001-000030";
//    private String presetId = "1569925913183-q9e71q";
    
//    @Value("${preset.id}")
    private String presetId;
    
//    @Value("${pipe.line.id}")
    private String pipelineId;
    private String thumbnailPath = "thumbnail/";
    private String thumbnailName = "";

    
    private AmazonS3 amazonS3Client = null;
    private AmazonElasticTranscoder amazonElasticTranscoder = null;
    final Region s3Region = Region.getRegion(Regions.AP_NORTHEAST_1);

    private static final long TEMP_URL_EXPIRY_TIME = 1000 * 30 * 60; // 30 minutes
    private static final Logger LOGGER = LoggerFactory.getLogger(AmazonS3Storage.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        
        Assert.notNull(bucket, "bucket must be set.");
        Assert.notNull(accessKey, "accessKey must be present.");
        Assert.notNull(accessSecret, "accessSecret must be present.");
        Assert.notNull(region, "region must be set.");
                    
        if (StringUtils.isEmpty(accessKey) && StringUtils.isEmpty(accessSecret)) {

            amazonS3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(region)
                    .build();

        } else {

            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, accessSecret);
            amazonS3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(region)
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .build();

            amazonElasticTranscoder = new AmazonElasticTranscoderClient(awsCredentials);

        }
        S3ClientOptions.builder().setPathStyleAccess(true);
    }

    public void setS3Url(String s3Url) {
        this.s3Url = s3Url;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public void setBasePath(String path) {
        this.basePath = path.trim();
    }

    @Override
    public String getBasePath() {
        return basePath;
    }

    @Override
    public void setBaseUrl(String url) {
    }

    @Override
    public String getBaseUrl() {
        return s3Url + "/" + bucket + "/" + basePath;
    }

    private Info buildInfo(String name, boolean isPublic) throws JsonProcessingException {
        URL url = null;
        String path;
        
        path = name;
        
//        if (name.startsWith("/")) {
//            path = name;
//        } else {
//            path = "/" + name;
//        }
        
        if (isPublic) {
            url = amazonS3Client.getUrl(bucket, name);
            LOGGER.info("s3 URL::" + url);
        }   
        Info info = new Info(path, url == null ? null : url.toString());
        LOGGER.info("s3 response::" + new ObjectMapper().writeValueAsString(info));
        return info;
    }

    private Info saveImage(String name, InputStream in, long size, String contentType, boolean isPublic) throws IOException {
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(size);
        meta.setContentType(contentType);
//        cloudBucket = cloudBucket+basePath;
        System.out.println("cloudBucket >>>>>>>>>> "+cloudBucket);
        System.out.println("name     >>>>>>>>>> "+name);
        
        PutObjectRequest request = new PutObjectRequest(cloudBucket,basePath+name, in, meta);
        
        
        amazonS3Client.putObject(request);
        return buildInfo(name, isPublic);
    }

    @Override
    public Info saveImage(String name, MultipartFile file, boolean isPublic) throws IOException {
        return saveImage(name, file.getInputStream(), file.getSize(), file.getContentType(), isPublic);
    }
    
    
    
    private Info save(String name, InputStream in, long size, String contentType, boolean isPublic) throws IOException {
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(size);
        meta.setContentType(contentType);
        PutObjectRequest request = new PutObjectRequest(bucket, basePath + name, in, meta);
//        if (isPublic) {
//            request.setCannedAcl(CannedAccessControlList.PublicRead);
//        }
        amazonS3Client.putObject(request);
        return buildInfo(name, isPublic);
    }

    
    @Override
    public Info uploadBufferedImageToServer(BufferedImage image, String fileName, String imageType) throws IOException, NullPointerException {
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		ImageIO.write(image, "png", outstream);
                outstream.flush();
		byte[] buffer = outstream.toByteArray();                                                        
		InputStream is = new ByteArrayInputStream(buffer);
		ObjectMetadata meta = new ObjectMetadata();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
		meta.setContentType("image/" + imageType);
		meta.setContentLength(buffer.length);
                                                                                                                                                    
		amazonS3Client.putObject(new PutObjectRequest(cloudBucket, basePath+fileName, is, meta));
                return buildInfo(fileName, true);
	}
    
       
    @Override
    public Info uploadFileTos3bucket(String fileName, File file) throws JsonProcessingException {
        amazonS3Client.putObject(new PutObjectRequest(cloudBucket, basePath+fileName, file));
            
    return buildInfo(fileName, true);
}
    @Override
    public Info save(String name, MultipartFile file, boolean isPublic) throws IOException {
        return save(name, file.getInputStream(), file.getSize(), file.getContentType(), isPublic);
    }

    @Override
    public Info save(String name, InputStream in, String contentType, boolean isPublic) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Info save(String name, byte[] data, String contentType, boolean isPublic) throws IOException {
        return save(name, new ByteArrayInputStream(data), data.length, contentType, isPublic);
    }

    @Override
    public Info save(String name, byte[] data, String contentType, boolean isPublic, boolean append) throws IOException {

        return buildInfo(name, isPublic);
    }

    @Override
    public InputStream get(String path) throws IOException {
        try {
            System.out.println("bucket:--------"+bucket);
            System.out.println("basePath+path:--------"+basePath+path);
            return amazonS3Client.getObject(bucket, basePath+path).getObjectContent();
        } catch (AmazonClientException ex) {
            throw new IOException("Error in getting S3 object", ex);
        }
    }

    @Override
    public void delete(String path) throws IOException {
        try {
            amazonS3Client.deleteObject(bucket, basePath+path);
        } catch (AmazonClientException ex) {
            throw new IOException("Error in deleting S3 object", ex);
        }
    }

    @Override
    public void copy(String srcPath, String dstPath) throws IOException {
        try {
            amazonS3Client.copyObject(bucket, srcPath, bucket, dstPath);
        } catch (AmazonClientException ex) {
            throw new IOException("Error in copying S3 object", ex);
        }
    }

    @Override
    public Info move(String srcPath, String dstPath, Boolean isPublic) throws IOException {
        try {
            Info info;
            amazonS3Client.copyObject(bucket, srcPath, bucket, basePath + dstPath);
            if (isPublic) {
                amazonS3Client.setObjectAcl(bucket, basePath + dstPath, CannedAccessControlList.PublicRead);
                info = buildInfo(dstPath, true);
            } else {
                info = buildInfo(dstPath, false);
            }
            amazonS3Client.deleteObject(bucket, srcPath);
            return info;
        } catch (AmazonClientException ex) {
            throw new IOException("Error in moving S3 object", ex);
        }
    }

    /**
     * This function is to get the list of objects path in a given sub folder of
     * S3 bucket.
     *
     * @param subFolder <code>String</code> parameter. If the contents of a
     * logical sub folder "upload/tmp/" are to be obtained, pass the parameter
     * as "upload/tmp/". NOTE: This parameter must end with a forward slash (/).
     * If the parameter is an empty string, all the objects in the bucket will
     * be listed.
     * @return <code>List</code>. Function returns the path of objects in the
     * logical sub folder, as a list.
     */
    @Override
    public List<String> list(String subFolder) {
        ObjectListing ol = amazonS3Client.listObjects(bucket, subFolder);
        List<S3ObjectSummary> objects = ol.getObjectSummaries();
        List<String> pathList = null;
        for (S3ObjectSummary os : objects) {
            if (pathList == null) {
                pathList = new LinkedList<>();
            }
            pathList.add(os.getKey());
        }
        return pathList;
    }

    /**
     * Function to get the temporary URL to access a given private object in the
     * Amazon S3 storage.
     *
     * @param relativePath Relative path of the object as <code>String</code>.
     * Eg: /upload/camera_archive/CAM100_25072016.mp4
     * @return The temporary URL as <code>String</code>.
     */
    @Override
    public String getTempUrl(String relativePath) {
        java.util.Date expiry = new java.util.Date();
        long msec = expiry.getTime();
        msec += TEMP_URL_EXPIRY_TIME;
        expiry.setTime(msec);
        GeneratePresignedUrlRequest generatePresignedUrlRequest
                = new GeneratePresignedUrlRequest(bucket, relativePath);
        generatePresignedUrlRequest.setMethod(HttpMethod.GET);
        generatePresignedUrlRequest.setExpiration(expiry);

        URL tempUrl = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);

        return tempUrl.toString();
    }

    @Override
    public boolean isValidImage(MultipartFile file) {
        boolean valid = false;
        try {
            String contentType = file.getContentType();
            if (Objects.equals(contentType, "image/gif")
                    || Objects.equals(contentType, "image/jpg")
                    || Objects.equals(contentType, "image/jpeg")
                    || Objects.equals(contentType, "image/png")
                    || Objects.equals(contentType, "image/bmp")) {

                valid = true;
            }
        } catch (Exception ex) {
            LOGGER.error("Error::image validation:" + ex);
        }
        return valid;
    }

    @Override
    public void download(String source, String destination, boolean isPublic) throws IOException {
        try {
            amazonS3Client.getObject(new GetObjectRequest(bucket, source),
                    new File(destination));
        } catch (AmazonClientException ex) {
            throw new IOException("Error in copying S3 object", ex);
        }

    }
    
      @Override
    public void downloadThumbnail(String source, String destination, boolean isPublic) throws IOException {
        try {            
            amazonS3Client.getObject(new GetObjectRequest(cloudBucket, source),
                    new File(destination));
        } catch (AmazonClientException ex) {
            throw new IOException("Error in copying S3 object", ex);
        }

    }

    @Override
    public void downloadAllFiles(String source, String destination, boolean isPublic) throws IOException {
        try {
            TransferManager transferManager = new TransferManager(amazonS3Client);
            File dir = new File(destination);

            MultipleFileDownload download = transferManager.downloadDirectory(bucket, source, dir);
            download.waitForCompletion();
        } catch (AmazonClientException | InterruptedException ex) {
            throw new IOException("Error in copying S3 object", ex);
        }

    }

    @Override
    public Info save(String name, File file, boolean isPublic) throws IOException {

        PutObjectRequest request = new PutObjectRequest(bucket, name, file);
        if (isPublic) {
//            request.setCannedAcl(CannedAccessControlList.PublicRead);
        }
        amazonS3Client.putObject(request);
        return buildInfo(name, isPublic);
    }

    @Override
    public Job jobCreation(String inputPath) {
        LOGGER.info("Start JobCreation ");
        outputPath = inputPath;
        amazonElasticTranscoder.setEndpoint("elastictranscoder.ap-northeast-1.amazonaws.com");
        LOGGER.info("amazonElasticTranscoder end point set ");
        JobInput input = new JobInput().withKey(inputPath);
        thumbnailName = thumbnailNameGeneration(inputPath);
        LOGGER.info("Thumbnail name created ");
//        Preset preset=new Preset().withThumbnails(thumbnails);
        CreateJobOutput outputs = new CreateJobOutput()
                .withKey(outputPath)
                .withPresetId(presetId)
                .withThumbnailPattern(thumbnailPath + thumbnailName);

        CreateJobRequest createJobRequest = new CreateJobRequest()
                .withPipelineId(pipelineId)
                .withInput(input)
                .withOutputs(outputs);
        LOGGER.info("Job Request Created ");
        LOGGER.info("Job Creation Started... ");
        CreateJobResult createJobResult = amazonElasticTranscoder.createJob(createJobRequest);
        LOGGER.info("Video transcode job completed ");

        return createJobResult.getJob();
    }
      @Override
    public Job jobCreation1(String inputPath) {
        LOGGER.info("Start JobCreation ");
        outputPath = "video/"+inputPath;
        amazonElasticTranscoder.setEndpoint("elastictranscoder.ap-northeast-1.amazonaws.com");
        LOGGER.info("amazonElasticTranscoder end point set ");
        JobInput input = new JobInput().withKey(outputPath);
        thumbnailName = thumbnailNameGeneration(outputPath);
        LOGGER.info("Thumbnail name created ");
        CreateJobOutput outputs = new CreateJobOutput()
                .withKey(outputPath)
                .withPresetId(presetId)
                .withThumbnailPattern(thumbnailPath + thumbnailName);
                
        CreateJobRequest createJobRequest = new CreateJobRequest()
                .withPipelineId(pipelineId)
                .withInput(input)
                .withOutputs(outputs);
        LOGGER.info("Job Request Created ");
        LOGGER.info("Job Creation Started... ");
        CreateJobResult createJobResult = amazonElasticTranscoder.createJob(createJobRequest);
        LOGGER.info("Video transcode job completed ");

        return createJobResult.getJob();
    }
    
    @Override
    public JobOutput getJobDetails(String id) {
        Job job;
        amazonElasticTranscoder.setEndpoint("elastictranscoder.ap-northeast-1.amazonaws.com");
        LOGGER.info("Collecting Video trancode job details");
        ReadJobRequest rj = new ReadJobRequest();
        rj.setId(id);
        ReadJobResult jobResult = amazonElasticTranscoder.readJob(rj);
        job = jobResult.getJob();
        JobOutput jobout = job.getOutput();
        LOGGER.info("Job details Collected");
        return jobout;
    }

    String thumbnailNameGeneration(String file) {
//        String name;
//        name = StringUtils.substringAfter(file, "/");
//        name = StringUtils.substringBefore(name, ".");
//        name += "thumb_{count}";
//        return name;
 String creativeName ="";
//commented---------------
//        creativeName = StringUtils.substringAfter(file, "/");
//        creativeName = StringUtils.substringBefore(creativeName, ".");
//commented---------------
        String name =creativeName+"/"+creativeName+"thumb_{count}";
        return name;

    }
    
 

}
