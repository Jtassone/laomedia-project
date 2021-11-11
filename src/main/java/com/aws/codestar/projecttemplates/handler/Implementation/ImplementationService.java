package com.aws.codestar.projecttemplates.handler.Implementation;

import com.aws.codestar.projecttemplates.handler.Algorithm.Algorithm;
import com.aws.codestar.projecttemplates.utils.UUIDUtil;

import java.sql.Connection;
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

    public static void postImplementation(Connection sqlConnection, Implementation implementation) throws SQLException {
        String name = implementation.name;
        String implementationDetails = implementation.implementationDetails;;
        UUID algorithmId = implementation.algorithmId;
        try {
            String sqlQuery = "INSERT INTO implementations (id, name, implementation_details, algorithm_id) VALUES (uuid_to_bin(uuid()),\"" + name + "\" , \"" + implementationDetails + "\" , uuid_to_bin(" + "\"" + algorithmId + "\"" + "))";
            System.out.println(sqlQuery);
            sqlConnection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }
    }

}
