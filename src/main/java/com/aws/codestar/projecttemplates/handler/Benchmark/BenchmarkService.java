package com.aws.codestar.projecttemplates.handler.Benchmark;


import com.aws.codestar.projecttemplates.handler.Implementation.Implementation;
import com.aws.codestar.projecttemplates.utils.UUIDUtil;
import com.aws.codestar.projecttemplates.utils.S3Client;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BenchmarkService {

    public static List<Benchmark> getAllBenchmarks(Connection sqlConnection, S3Client s3Client) throws SQLException, IOException {
        List<Benchmark> benchmarkList = new ArrayList<>();
        try (ResultSet sqlResponse = sqlConnection.createStatement().executeQuery("select * from benchmarks")) {
            while (sqlResponse.next()) {
                UUID id = UUIDUtil.getUUIDFromBytes(sqlResponse.getBytes("id"));
                Date date = sqlResponse.getDate("date");
                UUID implementationId = UUIDUtil.getUUIDFromBytes(sqlResponse.getBytes("implementation_id"));
                UUID instanceId = UUIDUtil.getUUIDFromBytes(sqlResponse.getBytes("instance_id"));
                UUID machineConfigId = UUIDUtil.getUUIDFromBytes(sqlResponse.getBytes("machine_config_id"));
                Benchmark benchmark = new Benchmark(id, date , machineConfigId, instanceId, implementationId);
                benchmarkList.add(benchmark);
            }
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }
        return benchmarkList;
    }

    public static void saveBenchmark(Connection sqlConnection, Benchmark benchmark) throws SQLException {
        try {
            String sqlQuery = "INSERT INTO benchmarks (id, date, implementationId, instance_id, machine_config_id) " +
                    "VALUES (uuid_to_bin(" + "\"" + benchmark.id + "\"" + "),\"" + benchmark.date + "\" , uuid_to_bin(" + "\"" + benchmark.implementationId + "\"" + ") ," +
                    " uuid_to_bin(" + "\"" + benchmark.instanceId + "\"" + ") , " + "uuid_to_bin(" + "\"" + benchmark.machineConfigId + "\"" + "))";
            System.out.println(sqlQuery);
            sqlConnection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }
    }

    public static void saveMachineConfig(Connection sqlConnection, MachineConfig machineConfig) throws SQLException {
        try {
            String sqlQuery = "INSERT INTO machine_config (id, cpu, core, l1, l2, l3, num_threads, ram) " +
                    "VALUES (uuid_to_bin(" + "\"" + machineConfig.id + "\"" + "),\"" + machineConfig.core + "\" , \"" + machineConfig.cpu + "\" ," +
                    "\" , \"" + machineConfig.l1 + "\" ," + "\" , \"" + machineConfig.l2 + "\" ," + "\" , \"" + machineConfig.l3 + "\" ," +
                    "\" , \"" + machineConfig.numThreads + "\" ," + "\" , \"" + machineConfig.ram + ")";
            System.out.println(sqlQuery);
            sqlConnection.prepareStatement(sqlQuery).executeUpdate();
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
