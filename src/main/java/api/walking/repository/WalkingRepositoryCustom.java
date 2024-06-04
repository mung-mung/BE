package api.walking.repository;

import api.walking.dto.WalkingDto;

import java.util.List;

public interface WalkingRepositoryCustom {
    List<WalkingDto> findWalkingsByAllCriteria(Integer id, Integer walkerId, Integer dogId);

}