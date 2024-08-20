package com.example.gadgetsore.repository;

import com.example.gadgetsore.entity.Smartphone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SmartphoneRepository extends JpaRepository<Smartphone, Long>, JpaSpecificationExecutor<Smartphone> {
}
