package com.aws.codestar.projecttemplates.handler.Implementation;

import com.aws.codestar.projecttemplates.utils.UUIDUtil;
import com.aws.codestar.projecttemplates.utils.S3Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ImplementationService {

    public static List<Implementation> getAllImplementations(Connection sqlConnection) throws SQLException {
        List<Implementation> implementationList = new ArrayList<>();
        try (ResultSet sqlResponse = sqlConnection.createStatement().executeQuery("select * from implementations")) {
            while (sqlResponse.next()) {
                UUID id = UUIDUtil.getUUIDFromBytes(sqlResponse.getBytes("id"));
                String name = sqlResponse.getString("name");
                UUID algorithmId = UUIDUtil.getUUIDFromBytes(sqlResponse.getBytes("algorithm_id"));
                String implementationDetails = sqlResponse.getString("implementation_details");
                Implementation implementation = new Implementation(id, name , implementationDetails, algorithmId);
                implementationList.add(implementation);
            }
        } catch (SQLException e) {
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
            String sqlQuery = "INSERT INTO implementations (id, name, implementation_details, algorithm_id) " +
                    "VALUES (uuid_to_bin(" + "\"" + implementationId + "\"" + "),\"" + name + "\" , \"" + implementationDetails + "\" ," +
                    " uuid_to_bin(" + "\"" + algorithmId + "\"" + "))";
            System.out.println(sqlQuery);
            sqlConnection.prepareStatement(sqlQuery).executeUpdate();
            s3Client.uploadImplementationToS3("laoimplementationbucket", implementationId.toString(), implementationDetails );
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }
    }

    public static void deleteImplementation(Connection sqlConnection,String id) throws SQLException{
        UUID implementationId = UUID.fromString(id);
        try {
            String deleteSQL = "DELETE FROM implementations WHERE id = uuid_to_bin(" + "\"" + implementationId + "\"" + ")";
            PreparedStatement preparedStatement = sqlConnection.prepareStatement(deleteSQL);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }
    }

}
