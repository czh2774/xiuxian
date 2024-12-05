package com.xiuxian.xiuxianserver.repository;

import com.xiuxian.xiuxianserver.entity.BuildingLocationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BuildingLocationTemplateRepository extends JpaRepository<BuildingLocationTemplate, Long> {
    List<BuildingLocationTemplate> findByRequiredCharacterLevel(int level);
    Optional<BuildingLocationTemplate> findByBuildingTemplateId(Long templateId);
} 