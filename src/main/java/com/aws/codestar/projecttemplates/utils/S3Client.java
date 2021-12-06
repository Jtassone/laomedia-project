package com.aws.codestar.projecttemplates.utils;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectResult;

public class S3Client {
    AmazonS3 s3Client;

    public S3Client() {
        s3Client = AmazonS3ClientBuilder.standard()
                .withRegion("us-east-1")
                .build();
    }

    public void uploadImplementationToS3(String bucketName, String stringObjKeyName, String implementation ) {
        try {
            System.out.println("Here we are before putting the object");
            PutObjectResult s3Result = s3Client.putObject(bucketName, stringObjKeyName, implementation);
            System.out.println(s3Result.getMetadata().getETag());
        } catch (AmazonServiceException e) {
            System.out.println(e);
            throw e;
        } catch (SdkClientException e) {
            System.out.println(e);
            throw e;
        }
    }
}
