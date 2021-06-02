package com.pucrs.controleentregas.repositories;

import com.pucrs.controleentregas.entities.OperatorEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OperatorRepository extends CrudRepository<OperatorEntity, Long> {

    List<OperatorEntity> findAll();

}
