package com.pucrs.controleentregas;

import com.pucrs.controleentregas.dtos.CreateDeliveryDTO;
import com.pucrs.controleentregas.entities.DeliveryEntity;
import com.pucrs.controleentregas.entities.OperatorEntity;
import com.pucrs.controleentregas.entities.ResidentEntity;
import com.pucrs.controleentregas.services.DeliveryService;
import com.pucrs.controleentregas.services.OperatorService;
import com.pucrs.controleentregas.services.ResidentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile("!unit-test")
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final OperatorService operatorService;
    private final ResidentService residentService;
    private final DeliveryService deliveryService;

    @Autowired
    public CommandLineAppStartupRunner(OperatorService operatorService, ResidentService residentService, DeliveryService deliveryService) {
        this.operatorService = operatorService;
        this.residentService = residentService;
        this.deliveryService = deliveryService;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Começou a popular");

        OperatorEntity operator = this.operatorService.save(OperatorEntity.builder().firstName("Matheus").lastName("Tosin").build());

        ResidentEntity resident = this.residentService.save(ResidentEntity.builder().name("Gui").rg("1234567896").apartment(203).build());

        CreateDeliveryDTO createDeliveryDTO = CreateDeliveryDTO.builder().description("Bolado dos Guri").apartment(resident.getApartment()).operatorCode(operator.getId()).build();
        DeliveryEntity delivery = this.deliveryService.save(createDeliveryDTO);

        log.info("Terminou de popular");
    }
}
