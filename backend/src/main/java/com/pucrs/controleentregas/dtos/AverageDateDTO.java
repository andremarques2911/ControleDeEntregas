package com.pucrs.controleentregas.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AverageDateDTO {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

}
