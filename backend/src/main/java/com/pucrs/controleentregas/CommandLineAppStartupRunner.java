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
        OperatorEntity operator2 = this.operatorService.save(OperatorEntity.builder().firstName("Guilherme").lastName("Carvalho").build());
        OperatorEntity operator3 = this.operatorService.save(OperatorEntity.builder().firstName("Guilherme").lastName("Argilar").build());
        OperatorEntity operator4 = this.operatorService.save(OperatorEntity.builder().firstName("Aloisio").lastName("Bestian").build());
        OperatorEntity operator5 = this.operatorService.save(OperatorEntity.builder().firstName("Giovanni").lastName("Frozza").build());

        ResidentEntity resident = this.residentService.save(ResidentEntity.builder().name("Gui").rg("1234567896").apartment(203).build());
        ResidentEntity resident2 = this.residentService.save(ResidentEntity.builder().name("Rui").rg("7458965214").apartment(704).build());
        ResidentEntity resident3 = this.residentService.save(ResidentEntity.builder().name("Andre").rg("2354178546").apartment(805).build());
        ResidentEntity resident4 = this.residentService.save(ResidentEntity.builder().name("Mick").rg("1578452369").apartment(101).build());
        ResidentEntity resident5 = this.residentService.save(ResidentEntity.builder().name("Callegari").rg("8452169842").apartment(302).build());

        CreateDeliveryDTO createDeliveryDTO = CreateDeliveryDTO.builder().description("Notebook").apartment(resident.getApartment()).operatorCode(operator.getId()).build();
        CreateDeliveryDTO createDeliveryDTO2 = CreateDeliveryDTO.builder().description("Mouse").apartment(resident2.getApartment()).operatorCode(operator2.getId()).build();
        CreateDeliveryDTO createDeliveryDTO3 = CreateDeliveryDTO.builder().description("Teclado").apartment(resident3.getApartment()).operatorCode(operator3.getId()).build();
        CreateDeliveryDTO createDeliveryDTO4 = CreateDeliveryDTO.builder().description("Headset").apartment(resident4.getApartment()).operatorCode(operator4.getId()).build();
        CreateDeliveryDTO createDeliveryDTO5 = CreateDeliveryDTO.builder().description("Monitor").apartment(resident5.getApartment()).operatorCode(operator5.getId()).build();
        DeliveryEntity delivery = this.deliveryService.save(createDeliveryDTO);
        DeliveryEntity delivery2 = this.deliveryService.save(createDeliveryDTO2);
        DeliveryEntity delivery3 = this.deliveryService.save(createDeliveryDTO3);
        DeliveryEntity delivery4 = this.deliveryService.save(createDeliveryDTO4);
        DeliveryEntity delivery5 = this.deliveryService.save(createDeliveryDTO5);

        log.info("Terminou de popular");
    }
}
