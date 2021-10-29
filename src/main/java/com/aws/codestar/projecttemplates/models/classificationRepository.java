package com.aws.codestar.projecttemplates.models;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface classificationRepository extends JpaRepository<Classification, UUID> {
}
