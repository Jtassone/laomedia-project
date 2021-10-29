package com.aws.codestar.projecttemplates.models;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface machineConfigRepository extends JpaRepository<MachineConfig, UUID> {
}
