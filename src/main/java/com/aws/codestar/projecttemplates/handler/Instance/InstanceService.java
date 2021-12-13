package com.aws.codestar.projecttemplates.handler.Instance;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.aws.codestar.projecttemplates.handler.Classification.Classification;
import com.aws.codestar.projecttemplates.handler.Implementation.Implementation;
import com.aws.codestar.projecttemplates.utils.S3Client;
import com.aws.codestar.projecttemplates.utils.UUIDUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public static List<Instance> getAllInstances(Connection sqlConnection, S3Client s3Client) throws SQLException, IOException {
        List<Instance> instanceList = new ArrayList<>();
        try (ResultSet sqlResponse = sqlConnection.createStatement().executeQuery("select * from instances")) {
            while (sqlResponse.next()) {
                UUID id = UUIDUtil.getUUIDFromBytes(sqlResponse.getBytes("id"));
                String name = sqlResponse.getString("name");
                UUID algorithmId = UUIDUtil.getUUIDFromBytes(sqlResponse.getBytes("algorithm_id"));
                UUID implementationId = UUIDUtil.getUUIDFromBytes(sqlResponse.getBytes("implementation_id"));
                String fileString = s3Client.downloadFileFromS3("laoinstancebucket", id.toString());
                Instance instance = new Instance(id, name , algorithmId, implementationId, fileString);
                instanceList.add(instance);
            }
        } catch (SQLException | IOException e) {
            System.out.println(e);
            throw e;
        }
        return instanceList;
    }

    public static void deleteInstance(Connection sqlConnection, S3Client s3Client, String id) throws Exception {
        UUID class_uuid = UUID.fromString(id);
        try {
            String deleteSQL = "DELETE FROM instances WHERE id = uuid_to_bin(" + "\"" + class_uuid + "\"" + ")";
            PreparedStatement preparedStatement = sqlConnection.prepareStatement("DELETE FROM instances WHERE id = ?");
            preparedStatement.setBytes(1, UUIDUtil.getBytesFromUUID(class_uuid));
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            s3Client.deleteFileFromS3("laoinstancebucket", id);

        } catch (Exception e) {
            throw new Exception("Failed to delete Classification: " + e.getMessage());
        }
    }
}
