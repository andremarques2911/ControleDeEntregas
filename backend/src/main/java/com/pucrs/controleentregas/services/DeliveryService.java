package com.pucrs.controleentregas.services;

import com.pucrs.controleentregas.dtos.CreateDeliveryDTO;
import com.pucrs.controleentregas.dtos.DashboardDTO;
import com.pucrs.controleentregas.dtos.EditDeliveryDTO;
import com.pucrs.controleentregas.dtos.ReportDTO;
import com.pucrs.controleentregas.entities.DeliveryEntity;
import com.pucrs.controleentregas.entities.OperatorEntity;
import com.pucrs.controleentregas.entities.ResidentEntity;
import com.pucrs.controleentregas.repositories.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class DeliveryService {

    private final DeliveryRepository repository;
    private final OperatorService operatorService;
    private final ResidentService residentService;

    @Autowired
    public DeliveryService(DeliveryRepository repository, OperatorService operatorService, ResidentService residentService) {
        this.repository = repository;
        this.operatorService = operatorService;
        this.residentService = residentService;
    }

    public DeliveryEntity save(CreateDeliveryDTO createDeliveryDTO) {
        OperatorEntity operator = this.operatorService.findById(createDeliveryDTO.getOperatorCode());
        DeliveryEntity deliveryEntity = DeliveryEntity.builder()
                .description(createDeliveryDTO.getDescription())
                .apartment(createDeliveryDTO.getApartment())
                .operator(operator)
                .registerDate(LocalDateTime.now())
                .build();
        return this.repository.save(deliveryEntity);
    }

    public DeliveryEntity findById(Long id) {
        Optional<DeliveryEntity> deliveryEntity = this.repository.findById(id);
        if (deliveryEntity.isPresent()) {
            return deliveryEntity.get();
        }
        return null;
    }

    public List<DeliveryEntity> findAll() {
        return this.repository.findAll();
    }

    public DeliveryEntity registerWithdrawal(EditDeliveryDTO editDeliveryDTO) {
        DeliveryEntity deliveryEntity = this.findById(editDeliveryDTO.getDeliveryCode());
        OperatorEntity operatorEntity = this.operatorService.findById(editDeliveryDTO.getOperatorCode());
        ResidentEntity residentEntity = this.residentService.findByActiveId(editDeliveryDTO.getResidentCode());
        if (deliveryEntity == null || operatorEntity == null || residentEntity == null) {
            return null;
        }
        deliveryEntity.setOperator(operatorEntity);
        deliveryEntity.setResident(residentEntity);
        deliveryEntity.setWithdrawalDate(LocalDateTime.now());
        return this.repository.save(deliveryEntity);
    }

    public List<DeliveryEntity> findAllByDescriptionLike(String description) {
        return this.repository.findAllByDescriptionLike(description);
    }

    public List<DeliveryEntity> findAllByResidentId(Long id) {
        return this.repository.findAllByResidentId(id);
    }

    public List<DeliveryEntity> findAllDeliveriesNotWithdrawn() {
        return this.repository.findAllDeliveriesNotWithdrawn();
    }

    public DashboardDTO searchDashboardInformation() {
        return DashboardDTO.builder()
                .numberDeliveriesLastThirtyDays(this.numberDeliveriesLastThirtyDays())
                .numberDeliveriesNotWithdrawn(this.numberDeliveriesNotWithdrawn())
                .average(this.averageTimeBetweenRegistrationAndWithdrawalDeliveries())
                .build();
    }

    public Integer numberDeliveriesLastThirtyDays() {
        return this.repository.countDeliveryEntitiesByWithdrawalDateBefore(LocalDateTime.now().minusDays(30L));
    }

    public Integer numberDeliveriesNotWithdrawn() {
        return this.findAllDeliveriesNotWithdrawn().size();
    }

    public LocalDateTime averageTimeBetweenRegistrationAndWithdrawalDeliveries() {
        List<DeliveryEntity> deliveries = this.repository.findAllDeliveriesWithdrawn();
        return null;
    }

    public List<ReportDTO> generateReport() {
        List<DeliveryEntity> deliveries = this.findAll();
        List<ReportDTO> report = new ArrayList<>();
        for (DeliveryEntity delivery : deliveries) {
            report.add(ReportDTO.parse(delivery));
        }
        return report;
    }

}
