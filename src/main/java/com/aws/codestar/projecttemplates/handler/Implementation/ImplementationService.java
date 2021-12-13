package com.aws.codestar.projecttemplates.handler.Implementation;

import com.aws.codestar.projecttemplates.utils.UUIDUtil;
import com.aws.codestar.projecttemplates.utils.S3Client;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ImplementationService {

    //TODO need to handle if there is an implementation in s3 that is not in the database for some reason
    public static List<Implementation> getAllImplementations(Connection sqlConnection, S3Client s3Client) throws SQLException, IOException {
        List<Implementation> implementationList = new ArrayList<>();
        try (ResultSet sqlResponse = sqlConnection.createStatement().executeQuery("select * from implementations")) {
            while (sqlResponse.next()) {
                UUID id = UUIDUtil.getUUIDFromBytes(sqlResponse.getBytes("id"));
                String name = sqlResponse.getString("name");
                UUID algorithmId = UUIDUtil.getUUIDFromBytes(sqlResponse.getBytes("algorithm_id"));
                String implementationDetails = s3Client.downloadFileFromS3("laoimplementationbucket", id.toString());
                Implementation implementation = new Implementation(id, name , implementationDetails, algorithmId);
                implementationList.add(implementation);
            }
        } catch (SQLException | IOException e) {
            System.out.println(e);
            throw e;
        }
        return implementationList;
    }

    public static void saveImplementation(Connection sqlConnection, S3Client s3Client, Implementation implementation) throws SQLException {
        String name = implementation.name;
        String implementationDetails = implementation.implementationDetails;;
        UUID implementationId = UUID.randomUUID();
        UUID algorithmId = implementation.algorithmId;
        try {
            String sqlQuery = "INSERT INTO implementations (id, name, algorithm_id) " +
                    "VALUES (uuid_to_bin(" + "\"" + implementationId + "\"" + "),\"" + name + "\"  ," +
                    " uuid_to_bin(" + "\"" + algorithmId + "\"" + "))";
            System.out.println(sqlQuery);
            sqlConnection.prepareStatement(sqlQuery).executeUpdate();
            s3Client.uploadFileToS3("laoimplementationbucket", implementationId.toString(), implementationDetails );
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }
    }

    public static void deleteImplementation(Connection sqlConnection, S3Client s3Client, String id) throws SQLException, IOException {
        UUID implementationId = UUID.fromString(id);
        try {
            PreparedStatement preparedStatement = sqlConnection.prepareStatement("DELETE FROM implementations WHERE id = ?");
            preparedStatement.setBytes(1, UUIDUtil.getBytesFromUUID(implementationId));
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            s3Client.deleteFileFromS3("laoimplementationbucket", id);
        } catch (SQLException | IOException e) {
            System.out.println(e);
            throw e;
        }
    }

}
