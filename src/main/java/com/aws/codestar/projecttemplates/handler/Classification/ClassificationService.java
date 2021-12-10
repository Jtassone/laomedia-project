package com.aws.codestar.projecttemplates.handler.Classification;

import com.aws.codestar.projecttemplates.utils.UUIDUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClassificationService {


    public static List<Classification> getAllClassifications(Connection sqlConnection) throws SQLException {
        List<Classification> classificationList = new ArrayList<>();
        try (ResultSet sqlResponse = sqlConnection.createStatement().executeQuery("select * from classifications")) {
            while (sqlResponse.next()) {
                UUID id = UUIDUtil.getUUIDFromBytes(sqlResponse.getBytes("id"));;
                String name = sqlResponse.getString("name");
                byte[] parentClassificationId = sqlResponse.getBytes("parent_classification_id");
                Classification classification;
                if (parentClassificationId == null) {
                    classification = new Classification(id, name);
                } else {
                    classification = new Classification(id, name, UUIDUtil.getUUIDFromBytes(parentClassificationId));
                }
                classificationList.add(classification);
            }
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }
        return classificationList;
    }

    public static void mergeClassification(Connection sqlConnection, UUID classificationId, UUID oldClassificationID, String classificationName) throws SQLException {
        try {
            deleteClassification(sqlConnection, oldClassificationID.toString());
            String sqlQuery = "UPDATE classifications SET parent_classification_id = uuid_to_bin(\"" + classificationId + "\") WHERE parent_classification_id = uuid_to_bin(\"" + oldClassificationID + "\")";
            System.out.println(sqlQuery);
            sqlConnection.prepareStatement(sqlQuery).executeUpdate();
            sqlQuery = "UPDATE classifications SET name = " + classificationName + " WHERE id = uuid_to_bin(\"" + classificationId + "\")";
            System.out.println(sqlQuery);
            sqlConnection.prepareStatement(sqlQuery).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void postClassification(Connection sqlConnection,Classification classification ) throws SQLException {
        String name = classification.name;
        try {
            String sqlQuery = "INSERT INTO classifications (id, name) VALUES (uuid_to_bin(uuid()),\"" + name + "\")";
            System.out.println(sqlQuery);
            sqlConnection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }
    }

    public static void postSubClassification(Connection sqlConnection,Classification classification ) throws SQLException {
        String name = classification.name;
        UUID parentClassificationId = classification.parentClassificationId;
        try {
            String sqlQuery = "INSERT INTO classifications (id, name) VALUES (uuid_to_bin(uuid()),\"" + name + "\" + uuid_to_bin(" + "\"" + parentClassificationId + "\"" + "))";
            System.out.println(sqlQuery);
            sqlConnection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }
    }
    
    public static void deleteClassification(Connection sqlConnection, String id) throws Exception {
        UUID class_uuid = UUID.fromString(id);
        try {
            String deleteSQL = "DELETE FROM classifications WHERE id = uuid_to_bin(" + "\"" + class_uuid + "\"" + ")";
            PreparedStatement preparedStatement = sqlConnection.prepareStatement(deleteSQL);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new Exception("Failed to delete Classification: " + e.getMessage());
        }
    }
}
