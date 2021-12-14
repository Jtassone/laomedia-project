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
                try (ResultSet sqlResponse2 = sqlConnection.createStatement().executeQuery("select * from machine_config")) {
                    while (sqlResponse.next()) {
                        if (machineConfigId.equals(UUIDUtil.getUUIDFromBytes(sqlResponse2.getBytes("id")))){
                            UUID id2 = UUIDUtil.getUUIDFromBytes(sqlResponse2.getBytes("id"));
                            String core = sqlResponse2.getString("core");
                            String cpu = sqlResponse2.getString("cpu");
                            String l1 = sqlResponse2.getString("l1");
                            String l2 = sqlResponse2.getString("l2");
                            String l3 = sqlResponse2.getString("l3");
                            String num_threads = sqlResponse2.getString("num_threads");
                            String ram = sqlResponse2.getString("ram");
                            MachineConfig config = new MachineConfig(id2, core, cpu, l1, l2, l3, num_threads, ram);
                            benchmark = new Benchmark(id, date , machineConfigId, config, instanceId, implementationId);
                        }
                    }
                }
                    catch (SQLException e) {
                        System.out.println(e);
                        throw e;
                    }
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
            PreparedStatement preparedStatement = sqlConnection.prepareStatement("INSERT INTO benchmarks (id, date, implementationId, instance_id, machine_config_id) VALUES (? , ? , ?, ? , ?)");
            preparedStatement.setBytes(1, UUIDUtil.getBytesFromUUID(benchmark.id));
            preparedStatement.setDate(2, (Date) benchmark.date);
            preparedStatement.setBytes(3, UUIDUtil.getBytesFromUUID(benchmark.implementationId));
            preparedStatement.setBytes(4, UUIDUtil.getBytesFromUUID(benchmark.instanceId));
            preparedStatement.setBytes(5, UUIDUtil.getBytesFromUUID(benchmark.machineConfigId));
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }
    }

    public static void saveMachineConfig(Connection sqlConnection, MachineConfig machineConfig) throws SQLException {
        try {
            PreparedStatement preparedStatement = sqlConnection.prepareStatement("INSERT INTO machine_config (id, cpu, core, l1, l2, l3, num_threads, ram) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setBytes(1, UUIDUtil.getBytesFromUUID(machineConfig.id));
            preparedStatement.setString(2, machineConfig.cpu);
            preparedStatement.setString(3, machineConfig.core);
            preparedStatement.setString(4, machineConfig.l1);
            preparedStatement.setString(5, machineConfig.l2);
            preparedStatement.setString(6, machineConfig.l3);
            preparedStatement.setString(7, machineConfig.numThreads);
            preparedStatement.setString(8, machineConfig.ram);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }
    }

    public static void deleteBenchmark(Connection sqlConnection,String id) throws SQLException{
        UUID benchmarkId = UUID.fromString(id);
        try {
            PreparedStatement preparedStatement = sqlConnection.prepareStatement("DELETE FROM benchmarks WHERE id = ?");
            preparedStatement.setBytes(1, UUIDUtil.getBytesFromUUID(benchmarkId));
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }
    }

}
