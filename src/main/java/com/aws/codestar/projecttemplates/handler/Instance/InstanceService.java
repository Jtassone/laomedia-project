package com.aws.codestar.projecttemplates.handler.Instance;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.aws.codestar.projecttemplates.utils.S3Client;

import java.sql.Connection;
import java.sql.SQLException;

public class InstanceService {

    public static void saveInstance(Connection sqlConnection, S3Client s3Client, Instance instance, String instanceDetails) throws SQLException {
        try {
            PutObjectResult s3Result = s3Client.uploadFileToS3("laoinstancebucket", instance.id.toString(), instanceDetails );
            String sqlQuery = "INSERT INTO instances (id, name, s3_etag, algorithm_id, implementation_id) " +
                    "VALUES (uuid_to_bin(" + "\"" + instance.id + "\"" + "),\"" + instance.name + "\" , \"" + s3Result.getETag() + "\" ," +
                    " uuid_to_bin(" + "\"" + instance.algorithmId + "\"" + ")," +
                    " uuid_to_bin(" + "\"" + instance.implementationId + "\"" + "))";
            System.out.println(sqlQuery);
            sqlConnection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }
    }
}
