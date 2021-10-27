package com.aws.codestar.projecttemplates.models;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlgorithmRepository extends JpaRepository<Algorithm, UUID> {
    Algorithm getAlgorithmById(UUID id);
    void deleteAlgorithmById(UUID id);


}
