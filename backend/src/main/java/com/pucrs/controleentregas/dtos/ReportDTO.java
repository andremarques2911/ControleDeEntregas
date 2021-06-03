package com.pucrs.controleentregas.dtos;

import com.pucrs.controleentregas.entities.DeliveryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {

    private Long deliveryCode;
    private LocalDateTime registerDate;
    private String description;
    private Integer apartment;
    private String operatorName;
    private LocalDateTime withdrawalDate;
    private String residentName;

    public static ReportDTO parse(DeliveryEntity delivery) {
        String initials = String.valueOf(delivery.getOperator().getFirstName().toUpperCase().charAt(0)) + String.valueOf(delivery.getOperator().getLastName().toUpperCase().charAt(0));
        return ReportDTO.builder()
                .deliveryCode(delivery.getId())
                .registerDate(delivery.getRegisterDate())
                .description(delivery.getDescription())
                .apartment(delivery.getApartment())
                .operatorName(initials)
                .withdrawalDate(delivery.getWithdrawalDate())
                .residentName(delivery.getResident() != null ? delivery.getResident().getName() : "")
                .build();
    }

}
