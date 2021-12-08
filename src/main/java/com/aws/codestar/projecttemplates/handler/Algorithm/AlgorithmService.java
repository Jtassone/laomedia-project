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
                UUID id = UUIDUtil.getUUIDFromBytes(sqlIdBytes);
                String name = sqlResponse.getString("name");
                String algorithmDetails = sqlResponse.getString("algorithm_details");
                byte[] classificationIdBytes = sqlResponse.getBytes("classification_id");
                UUID classificationId = UUIDUtil.getUUIDFromBytes(classificationIdBytes);
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

    public static void deleteAlgorithms(Connection sqlConnection,String id ) throws SQLException{
        UUID algo_uuid = UUID.fromString(id);
        try {
            String deleteSQL = "DELETE FROM algorithms WHERE id = uuid_to_bin(" + "\"" + algo_uuid + "\"" + ")";
            PreparedStatement preparedStatement = sqlConnection.prepareStatement(deleteSQL);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }
    }

    public static void reclassifyAlgorithm(Connection sqlConnection,String id, String newClassification ) throws SQLException{
        UUID algo_uuid = UUID.fromString(id);
        try {
            String updateSQL = "UPDATE algorithms SET classification_id = (newClassification) WHERE id = uuid_to_bin(" + "\"" + algo_uuid + "\"" + ") VALUES (\" + newClassification + \")";
            PreparedStatement preparedStatement = sqlConnection.prepareStatement(updateSQL);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }
    }



}
