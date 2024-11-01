package com.xiuxian.xiuxianserver.repository;

import com.xiuxian.xiuxianserver.entity.ItemTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemTemplateRepository extends JpaRepository<ItemTemplate, Long> {
}
