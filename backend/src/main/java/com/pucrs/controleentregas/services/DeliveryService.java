package com.pucrs.controleentregas.services;

import com.pucrs.controleentregas.dtos.CreateDeliveryDTO;
import com.pucrs.controleentregas.dtos.DashboardDTO;
import com.pucrs.controleentregas.dtos.EditDeliveryDTO;
import com.pucrs.controleentregas.entities.DeliveryEntity;
import com.pucrs.controleentregas.entities.OperatorEntity;
import com.pucrs.controleentregas.entities.ResidentEntity;
import com.pucrs.controleentregas.repositories.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

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
//        double averageRegister = deliveries.stream().map(delivery -> delivery.getRegisterDate())
//                .mapToLong(teste -> teste.toEpochSecond(ZoneOffset.UTC))
//                .average()
//                .getAsDouble();
//
//        double averageWithdrawal = deliveries.stream().map(delivery -> delivery.getWithdrawalDate())
//                .mapToLong(teste -> teste.toEpochSecond(ZoneOffset.UTC))
//                .average()
//                .getAsDouble();

//        deliveries.stream().map(delivery -> delivery.getRegisterDate().toLocalTime())
//            .mapToInt(LocalTime::toSecondOfDay)
//            .average()
//            .getAsDouble();
        double sum = 0;
        for (DeliveryEntity delivery : deliveries) {
            sum += delivery.getWithdrawalDate().toEpochSecond(ZoneOffset.UTC) - delivery.getRegisterDate().toEpochSecond(ZoneOffset.UTC);
        }
        if (sum > 0) {
            Double average = sum / deliveries.size();
            LocalDateTime averageDate = LocalDateTime.ofEpochSecond(average.longValue(), 0, ZoneOffset.UTC);
            return averageDate;
        }
        return null;
    }

}
