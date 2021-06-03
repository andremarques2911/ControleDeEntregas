package com.pucrs.controleentregas.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDTO {

    private Integer numberDeliveriesLastThirtyDays;
    private Integer numberDeliveriesNotWithdrawn;
    private AverageDateDTO averageDate;

}
