package com.pucrs.controleentregas.services;

import com.pucrs.controleentregas.dtos.*;
import com.pucrs.controleentregas.entities.DeliveryEntity;
import com.pucrs.controleentregas.entities.OperatorEntity;
import com.pucrs.controleentregas.entities.ResidentEntity;
import com.pucrs.controleentregas.repositories.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
        ResidentEntity residentEntity = this.residentService.findByActiveId(editDeliveryDTO.getResidentCode());
        if (deliveryEntity == null || residentEntity == null) {
            return null;
        }
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
                .averageDate(this.averageTimeBetweenRegistrationAndWithdrawalDeliveries())
                .build();
    }

    public Integer numberDeliveriesLastThirtyDays() {
        return this.repository.countDeliveryEntitiesByWithdrawalDateBefore(LocalDateTime.now().minusDays(30L));
    }

    public Integer numberDeliveriesNotWithdrawn() {
        return this.findAllDeliveriesNotWithdrawn().size();
    }

    public AverageDateDTO averageTimeBetweenRegistrationAndWithdrawalDeliveries() {
        List<DeliveryEntity> deliveries = this.repository.findAllDeliveriesWithdrawn();

        int year = 0;
        int month = 0;
        int day = 0;
        int hour = 0;
        int minute = 0;
        int second = 0;

        for (DeliveryEntity delivery : deliveries) {
            year += ChronoUnit.YEARS.between(delivery.getRegisterDate().toLocalDate(), delivery.getWithdrawalDate().toLocalDate());
            month += ChronoUnit.DAYS.between(delivery.getRegisterDate().toLocalDate(), delivery.getWithdrawalDate().toLocalDate());
            day += ChronoUnit.DAYS.between(delivery.getRegisterDate().toLocalDate(), delivery.getWithdrawalDate().toLocalDate());
            hour += ChronoUnit.HOURS.between(delivery.getRegisterDate().toLocalTime(), delivery.getWithdrawalDate().toLocalTime());
            minute += ChronoUnit.MINUTES.between(delivery.getRegisterDate().toLocalTime(), delivery.getWithdrawalDate().toLocalTime());
            second += ChronoUnit.SECONDS.between(delivery.getRegisterDate().toLocalTime(), delivery.getWithdrawalDate().toLocalTime());
        }
        year /= deliveries.size();
        month /= deliveries.size();
        day /= deliveries.size();
        hour /= deliveries.size();
        minute /= deliveries.size();
        second /= deliveries.size();

        return AverageDateDTO.builder()
                .year(year)
                .month(month)
                .day(day)
                .hour(hour)
                .minute(minute)
                .second(second)
                .build();
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
