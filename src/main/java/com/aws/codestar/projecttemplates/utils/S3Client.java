package com.aws.codestar.projecttemplates.utils;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;

import java.io.IOException;

public class S3Client {
    AmazonS3 s3Client;

    public S3Client() {
        s3Client = AmazonS3ClientBuilder.standard()
                .withRegion("us-east-1")
                .build();
    }

    public PutObjectResult uploadFileToS3(String bucketName, String stringObjKeyName, String fileToUpload ) {
        try {
            System.out.println("Here we are before putting the object");
            PutObjectResult s3Result = s3Client.putObject(bucketName, stringObjKeyName, fileToUpload);
            System.out.println(s3Result.getMetadata().getETag());
            return s3Result;
        } catch (SdkClientException e) {
            System.out.println(e);
            throw e;
        }
    }

    public String downloadFileFromS3(String bucketName, String strinObjKeyName) throws IOException {
        S3Object fullObject = null;
        try {
            fullObject = s3Client.getObject(new GetObjectRequest(bucketName, strinObjKeyName));
            return IOUtils.toString(fullObject.getObjectContent());
        } catch (SdkClientException e) {
            System.out.print(e);
            throw e;
        } finally {
            // To ensure that the network connection doesn't remain open, close any open input streams.
            if (fullObject != null) {
                fullObject.close();
            }
        }
    }
}
