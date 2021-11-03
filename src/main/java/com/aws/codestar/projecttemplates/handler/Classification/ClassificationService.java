package com.aws.codestar.projecttemplates.handler.Classification;

import com.aws.codestar.projecttemplates.handler.Algorithm.Algorithm;

import java.sql.Connection;
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
                String id = sqlResponse.getString("id");;
                String name = sqlResponse.getString("name");
                String classificationDetails = sqlResponse.getString("classification_details");
                Classification classification = new Classification(id, name, classificationDetails);
                classificationList.add(classification);
            }
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }
        return classificationList;
    }
}
