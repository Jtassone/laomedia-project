package com.aws.codestar.projecttemplates.handler.Benchmark;

import java.util.Date;
import java.util.UUID;

public class Benchmark {


    public UUID id;

    public Date date;

    public UUID machineConfigId;

    public UUID instanceId;

    public UUID implementationId;



    public Benchmark(UUID id, Date date, UUID machineConfigId, UUID instanceId, UUID implementationId) {
        this.id = id;
        this.date = date;
        this.machineConfigId = machineConfigId;
        this.instanceId = instanceId;
        this.implementationId = implementationId;
    }
}
