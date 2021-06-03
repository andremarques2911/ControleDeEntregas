package com.pucrs.controleentregas.services;

import com.pucrs.controleentregas.entities.ResidentEntity;
import com.pucrs.controleentregas.repositories.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ResidentService {

    private final ResidentRepository repository;

    @Autowired
    public ResidentService(ResidentRepository repository) {
        this.repository = repository;
    }

    public ResidentEntity save(ResidentEntity residentEntity) {
        return this.repository.save(residentEntity);
    }

    public List<ResidentEntity> findAll() {
        return this.repository.findAll();
    }

    public ResidentEntity findById(Long id) {
        Optional<ResidentEntity> residentEntity = this.repository.findById(id);
        if (residentEntity.isPresent()) {
            return residentEntity.get();
        }
        return null;
    }

    public ResidentEntity findByActiveId(Long id) {
        ResidentEntity residentEntity = this.repository.findByIdAndAndDeactivationDateIsNull(id);
        return residentEntity;
    }

    public ResidentEntity deactivateResident(Long id) {
        ResidentEntity residentEntity = this.findById(id);
        residentEntity.setDeactivationDate(new Date());
        return this.save(residentEntity);
    }

}
