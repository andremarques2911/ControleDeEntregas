package com.pucrs.controleentregas.services;

import com.pucrs.controleentregas.entities.OperatorEntity;
import com.pucrs.controleentregas.repositories.OperatorRepository;
import com.pucrs.controleentregas.utils.exceptions.OperatorException;
import com.pucrs.controleentregas.utils.exceptions.OperatorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperatorService {

    private final OperatorRepository repository;

    @Autowired
    public OperatorService(OperatorRepository repository) {
        this.repository = repository;
    }

    public OperatorEntity save(OperatorEntity operatorEntity) {
        return this.repository.save(operatorEntity);
    }

    public List<OperatorEntity> findAll() {
        return this.repository.findAll();
    }

    public OperatorEntity findById(Long id) {
        Optional<OperatorEntity> operatorEntity = this.repository.findById(id);
        if (operatorEntity.isEmpty()) {
            throw new OperatorNotFoundException();
        }
        return operatorEntity.get();
    }

    public OperatorEntity deleteById(Long id) {
        OperatorEntity operatorEntity = this.findById(id);
        if (operatorEntity == null) {
            throw new OperatorNotFoundException();
        }
        if (operatorEntity.getDeliveries() != null && !operatorEntity.getDeliveries().isEmpty()) {
            throw new OperatorException("Operador possui entregas.");
        }
        this.repository.delete(operatorEntity);
        return operatorEntity;
    }

}
