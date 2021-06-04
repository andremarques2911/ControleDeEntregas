package com.pucrs.controleentregas.repositories;

import com.pucrs.controleentregas.entities.ResidentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResidentRepository extends CrudRepository<ResidentEntity, Long> {

    List<ResidentEntity> findAll();

    ResidentEntity findByIdAndDeactivationDateIsNull(Long id);

}
