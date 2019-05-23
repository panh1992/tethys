package org.athena.common.storage;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.StringUtils;

import java.util.List;

public class StsServiceSample {

    public static void main(String[] args) {

        String accessKey = "qqq";
        String secretKey = "qqq";

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP);

        AmazonS3 conn = new AmazonS3Client(credentials, clientConfig);
        conn.setEndpoint("127.0.0.1:8080");

        List<Bucket> buckets = conn.listBuckets();
        for (Bucket bucket : buckets) {
            System.out.println(bucket.getName() + StringUtils.fromDate(bucket.getCreationDate()));
        }

        ObjectListing objectListing = conn.listObjects("demo");

        List<S3ObjectSummary> s3ObjectSummaries = objectListing.getObjectSummaries();

        s3ObjectSummaries.forEach(s3ObjectSummary -> {
            System.out.println("===================");
            System.out.println("文件key:" + s3ObjectSummary.getKey());
            conn.deleteObject("demo", s3ObjectSummary.getKey());
        });

    }

}
