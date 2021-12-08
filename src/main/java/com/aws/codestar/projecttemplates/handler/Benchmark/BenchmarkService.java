package com.aws.codestar.projecttemplates.handler.Benchmark;


import com.aws.codestar.projecttemplates.handler.Implementation.Implementation;
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

public class BenchmarkService {

    public static List<Benchmark> getAllBenchmarks(Connection sqlConnection, S3Client s3Client) throws SQLException, IOException {
        List<Benchmark> benchmarkList = new ArrayList<>();
        try (ResultSet sqlResponse = sqlConnection.createStatement().executeQuery("select * from benchmarks")) {
            while (sqlResponse.next()) {
                UUID id = UUIDUtil.getUUIDFromBytes(sqlResponse.getBytes("id"));
                String name = sqlResponse.getString("name");
                UUID implementationId = UUIDUtil.getUUIDFromBytes(sqlResponse.getBytes("implementation_id"));
                String benchmarkDetails = s3Client.downloadFileFromS3("laobenchmarkbucket", id.toString());
                Benchmark benchmark = new Benchmark(id, name , benchmarkDetails, implementationId);
                benchmarkList.add(benchmark);
            }
        } catch (SQLException | IOException e) {
            System.out.println(e);
            throw e;
        }
        return benchmarkList;
    }

    public static void saveBenchmark(Connection sqlConnection, S3Client s3Client, Benchmark benchmark) throws SQLException {
        String name = benchmark.name;
        String benchmarkDetails = benchmark.benchmarkDetails;;
        UUID benchmarkId = UUID.randomUUID();
        UUID implementationId = benchmark.implementationId;
        try {
            String sqlQuery = "INSERT INTO benchmarks (id, name, benchmarkDetails, implementationId) " +
                    "VALUES (uuid_to_bin(" + "\"" + benchmarkId + "\"" + "),\"" + name + "\" , \"" + benchmarkDetails + "\" ," +
                    " uuid_to_bin(" + "\"" + implementationId + "\"" + "))";
            System.out.println(sqlQuery);
            sqlConnection.prepareStatement(sqlQuery).executeUpdate();
            s3Client.uploadFileToS3("laobenchmarkbucket", benchmarkId.toString(), benchmarkDetails );
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }
    }

    public static void deleteBenchmark(Connection sqlConnection,String id) throws SQLException{
        UUID benchmarkId = UUID.fromString(id);
        try {
            String deleteSQL = "DELETE FROM benchmarks WHERE id = uuid_to_bin(" + "\"" + benchmarkId + "\"" + ")";
            PreparedStatement preparedStatement = sqlConnection.prepareStatement(deleteSQL);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }
    }

}
