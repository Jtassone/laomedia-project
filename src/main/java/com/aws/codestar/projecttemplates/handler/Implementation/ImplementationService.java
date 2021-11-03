package com.aws.codestar.projecttemplates.handler.Implementation;

import com.aws.codestar.projecttemplates.handler.Algorithm.Algorithm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ImplementationService {
    public static List<Implementation> getAllImplementations(Connection sqlConnection) throws SQLException {
        List<Implementation> implementationList = new ArrayList<>();
        try (ResultSet sqlResponse = sqlConnection.createStatement().executeQuery("select * from algorithms")) {
            while (sqlResponse.next()) {
                UUID id = UUID.nameUUIDFromBytes(sqlResponse.getBytes("id"));
                String name = sqlResponse.getString("name");
                String fileName = sqlResponse.getString("file_name");
                String implementationDetails = sqlResponse.getString("implementation_details");
                Implementation implementation = new Implementation(id, name, fileName, implementationDetails);
                implementationList.add(implementation);
            }
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }
        return implementationList;
    }

}
