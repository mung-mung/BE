package api.owning.repository;

import api.owning.dto.OwningDto;

import java.util.List;

public interface OwningRepositoryCustom {
    List<OwningDto> findOwningsByAllCriteria(Integer id, Integer ownerId, Integer dogId);

}