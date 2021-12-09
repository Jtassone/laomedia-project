package com.aws.codestar.projecttemplates.handler.Benchmark;

import java.util.UUID;

public class MachineConfig {
    private UUID id;
    public String core;
    public String cpu;
    public String l1;
    public String l2;
    public String l3;
    public Integer num_threads;
    public String ram;

    public MachineConfig(UUID id, String core, String cpu, String l1, String l2, String l3, Integer num_threads, String ram){
        this.id=id;
        this.core=core;
        this.cpu=cpu;
        this.l1=l1;
        this.l2=l2;
        this.l3=l3;
        this.num_threads=num_threads;
        this.ram=ram;
    }


}
