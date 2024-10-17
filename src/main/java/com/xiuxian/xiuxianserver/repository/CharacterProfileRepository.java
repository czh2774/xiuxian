package com.xiuxian.xiuxianserver.repository;

import com.xiuxian.xiuxianserver.entity.CharacterProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacterProfileRepository extends JpaRepository<CharacterProfile, Long> {
    // 可以添加自定义查询方法
    Optional<CharacterProfile> findByPlayerId(Long playerId);
}
