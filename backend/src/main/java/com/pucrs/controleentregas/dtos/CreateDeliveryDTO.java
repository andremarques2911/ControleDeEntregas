package com.pucrs.controleentregas.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateDeliveryDTO {

    private String description;
    private Integer apartment;
    private Long operatorCode;

}
