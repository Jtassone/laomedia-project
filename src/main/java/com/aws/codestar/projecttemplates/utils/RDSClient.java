package com.aws.codestar.projecttemplates.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RDSClient {
    public static Connection getRemoteConnection() {
        try {
            String jdbcUrl = "jdbc:mysql://laomedia.cffhqwuildxe.us-east-1.rds.amazonaws.com:3306/laoData?user=admin&password=Thisisatestpassword!";
            return DriverManager.getConnection(jdbcUrl);
        }
        catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}
