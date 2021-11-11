package com.aws.codestar.projecttemplates.handler.Algorithm;

import com.aws.codestar.projecttemplates.handler.Algorithm.Algorithm;
import com.aws.codestar.projecttemplates.utils.UUIDUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class AlgorithmService {

    public static List<Algorithm> getAllAlgorithms(Connection sqlConnection) throws SQLException {
        List<Algorithm> algorithmList = new ArrayList<>();
        try (ResultSet sqlResponse = sqlConnection.createStatement().executeQuery("select * from algorithms")) {
            while (sqlResponse.next()) {
                byte[] sqlIdBytes = sqlResponse.getBytes("id");
                System.out.println(UUIDUtil.getUUIDFromBytes(sqlIdBytes));
                UUID id = UUID.nameUUIDFromBytes(sqlIdBytes);
                System.out.println(id);
                String name = sqlResponse.getString("name");
                String algorithmDetails = sqlResponse.getString("algorithm_details");
                UUID classificationId = UUID.nameUUIDFromBytes(sqlResponse.getBytes("classification_id"));
                Algorithm algorithm = new Algorithm(id, name, algorithmDetails, classificationId);
                algorithmList.add(algorithm);
            }
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }
        return algorithmList;
    }

//    public static Algorithm getAlgorithmById(UUID id, Connection sqlConnection) {
//        try(ResultSet sqlResponse = sqlConnection.createStatement().executeQuery("select * from algorithms inner join algorithms_implementations")) {
//    }


    public static void postAlgorithms(Connection sqlConnection,Algorithm algorithm ) throws SQLException {
        String name = algorithm.name;
        String algorithmDetails = algorithm.algorithmDetails;
        UUID classificationId = algorithm.classificationId;
        try {
            String sqlQuery = "INSERT INTO algorithms (id, name, algorithm_details, classification_id) VALUES (uuid_to_bin(uuid()),\"" + name + "\" , \"" + algorithmDetails + "\" , uuid_to_bin(" + "\"" + classificationId + "\"" + "))";
            System.out.println(sqlQuery);
            sqlConnection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }
    }

    public static void deleteAlgorithms(Connection sqlConnection,Algorithm algorithm ) throws SQLException{
        String name = algorithm.name;
        try {
            sqlConnection.createStatement().executeQuery("DELETE FROM algorithms WHERE name = name");
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }
    }

}
