package com.pucrs.controleentregas.services;

import com.pucrs.controleentregas.dtos.CreateDeliveryDTO;
import com.pucrs.controleentregas.dtos.EditDeliveryDTO;
import com.pucrs.controleentregas.dtos.ReportDTO;
import com.pucrs.controleentregas.entities.DeliveryEntity;
import com.pucrs.controleentregas.entities.OperatorEntity;
import com.pucrs.controleentregas.entities.ResidentEntity;
import com.pucrs.controleentregas.repositories.DeliveryRepository;
import com.pucrs.controleentregas.repositories.OperatorRepository;
import com.pucrs.controleentregas.utils.exceptions.DeliveryNotFoundException;
import com.pucrs.controleentregas.utils.exceptions.OperatorNotFoundException;
import com.pucrs.controleentregas.utils.exceptions.ResidentNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@SpringBootTest
@ActiveProfiles("unit-test")
public class DeliveryServiceTest {

    @MockBean
    private DeliveryRepository deliveryRepository;

    @MockBean
    private OperatorRepository operatorRepository;

    @SpyBean
    private DeliveryService deliveryService;

    @SpyBean
    private ResidentService residentService;

    @DisplayName("Testa cadastro de entrega")
    @Test
    public void newDeliveryTest() {
        OperatorEntity operatorEntity = OperatorEntity.builder().id(1L).firstName("luis").lastName("rios").build();
        DeliveryEntity deliveryMock = DeliveryEntity.builder().id(1L).description("Caixa").apartment(100).build();
        CreateDeliveryDTO createDeliveryDTO = CreateDeliveryDTO.builder().description("Caixa").apartment(100).operatorCode(operatorEntity.getId()).build();

        when(deliveryRepository.save(any(DeliveryEntity.class))).thenReturn(deliveryMock);
        when(operatorRepository.findById(any())).thenReturn(Optional.ofNullable(operatorEntity));
        var result = deliveryService.save(createDeliveryDTO);
        assertEquals(deliveryMock , result);
    }

    @DisplayName("Testa cadastro de entrega com operador inexistente")
    @Test
    public void newDeliveryOperatorNotExists_thenException() throws OperatorNotFoundException {
        CreateDeliveryDTO createDeliveryDTO = CreateDeliveryDTO.builder().apartment(101).description("Caixa").operatorCode(2L).build();
        Assertions.assertThrows(OperatorNotFoundException.class, () -> {
            deliveryService.save(createDeliveryDTO);
        });
    }

    @DisplayName("Testa se o método findAllDeliveriesNotWithdrawn retorna entregas não retiradas")
    @Test
    public void getAllUndeliveredTest() {
        DeliveryEntity delivery2 = DeliveryEntity.builder().id(2L).description("Caixa").apartment(102).build();
        DeliveryEntity delivery3 = DeliveryEntity.builder().id(3L).description("Caixa").apartment(103).build();
        DeliveryEntity delivery4 = DeliveryEntity.builder().id(4L).description("Caixa").apartment(104).build();
        List<DeliveryEntity> deliveries = new ArrayList<>(
                Arrays.asList(delivery2, delivery3, delivery4)
        );

        when(deliveryRepository.findAllDeliveriesNotWithdrawn()).thenReturn(deliveries);

        var result = deliveryService.findAllDeliveriesNotWithdrawn();
        assertEquals(3, result.size());
    }

    @DisplayName("Testa uma retirada de entrega")
    @Test
    public void registerWithdrawTest() {
        ResidentEntity residentMock = ResidentEntity.builder().name("Andre").apartment(100).id(1L).build();
        DeliveryEntity deliveryMock = DeliveryEntity.builder().id(1L).description("caixa").apartment(100).build();
        EditDeliveryDTO editDeliveryMock = EditDeliveryDTO.builder().deliveryCode(deliveryMock.getId()).residentCode(residentMock.getId()).build();
        when(residentService.findByActiveId(any())).thenReturn(residentMock);
        when(deliveryRepository.findById(any())).thenReturn(Optional.of(deliveryMock));
        when(deliveryRepository.save(any(DeliveryEntity.class))).thenReturn(deliveryMock);
        var result = deliveryService.registerWithdrawal(editDeliveryMock);
        assertNotNull(result.getWithdrawalDate());
    }

    @DisplayName("Testa retirada com entrega inexistente")
    @Test
    public void registerWithdrawalNotExists_thenException() throws DeliveryNotFoundException {
        DeliveryEntity deliveryMock = DeliveryEntity.builder().id(1L).apartment(100).build();
        ResidentEntity residentMock = ResidentEntity.builder().id(1L).apartment(100).deactivationDate(LocalDateTime.now()).build();
        EditDeliveryDTO editDeliveryMock = EditDeliveryDTO.builder().deliveryCode(deliveryMock.getId()).residentCode(residentMock.getId()).build();

        Assertions.assertThrows(DeliveryNotFoundException.class, () -> {
            deliveryService.registerWithdrawal(editDeliveryMock);
        });
    }

    @DisplayName("Testa retirada de entrega com morador desativado")
    @Test
    public void registerWithdrawalResidentDeactivated_thenException() throws ResidentNotFoundException {
        DeliveryEntity deliveryMock = DeliveryEntity.builder().id(1L).apartment(100).build();
        ResidentEntity residentMock = ResidentEntity.builder().id(1L).apartment(100).deactivationDate(LocalDateTime.now()).build();
        EditDeliveryDTO editDeliveryMock = EditDeliveryDTO.builder().deliveryCode(deliveryMock.getId()).residentCode(residentMock.getId()).build();

        when(deliveryRepository.findById(any())).thenReturn(Optional.ofNullable(deliveryMock));
        when(residentService.findByActiveId(any())).thenReturn(null);

        Assertions.assertThrows(ResidentNotFoundException.class, () -> {
            deliveryService.registerWithdrawal(editDeliveryMock);
        });
    }

    @DisplayName("Testa a quantidade de entregas retiradas nos últimos 30 dias")
    @Test
    public void numberDeliveriesLastThirtyDaysTest() {
        DeliveryEntity delivery1 = DeliveryEntity.builder().id(2L).description("Caixa").apartment(102).build();
        DeliveryEntity delivery2 = DeliveryEntity.builder().id(2L).description("Caixa").apartment(102).build();
        DeliveryEntity delivery3 = DeliveryEntity.builder().id(3L).description("Caixa").apartment(103).build();
        DeliveryEntity delivery4 = DeliveryEntity.builder().id(4L).description("Caixa").apartment(104).build();
        DeliveryEntity delivery5 = DeliveryEntity.builder().id(4L).description("Caixa").apartment(104).build();
        DeliveryEntity delivery6 = DeliveryEntity.builder().id(4L).description("Caixa").apartment(104).build();
        DeliveryEntity delivery7 = DeliveryEntity.builder().id(4L).description("Caixa").apartment(104).build();
        List<DeliveryEntity> deliveries = new ArrayList<>(
                Arrays.asList(delivery1, delivery2, delivery3, delivery4, delivery5, delivery6, delivery7)
        );

        when(deliveryRepository.countDeliveryEntitiesByRegisterDateAfter(any())).thenReturn(deliveries.size());
        var result = deliveryService.numberDeliveriesLastThirtyDays();
        assertEquals(7, result);
    }

    @DisplayName("Testa a geração de relatório")
    @Test
    public void generateReportTest() {
        OperatorEntity operator = OperatorEntity.builder().id(1L).firstName("luis").lastName("rios").build();
        DeliveryEntity delivery1 = DeliveryEntity.builder().id(2L).description("Caixa").apartment(102).operator(operator).build();
        DeliveryEntity delivery2 = DeliveryEntity.builder().id(2L).description("Caixa").apartment(102).operator(operator).build();
        DeliveryEntity delivery3 = DeliveryEntity.builder().id(3L).description("Caixa").apartment(103).operator(operator).build();
        DeliveryEntity delivery4 = DeliveryEntity.builder().id(4L).description("Caixa").apartment(104).operator(operator).build();
        DeliveryEntity delivery5 = DeliveryEntity.builder().id(4L).description("Caixa").apartment(104).operator(operator).build();
        DeliveryEntity delivery6 = DeliveryEntity.builder().id(4L).description("Caixa").apartment(104).operator(operator).build();
        DeliveryEntity delivery7 = DeliveryEntity.builder().id(4L).description("Caixa").apartment(104).operator(operator).build();
        List<DeliveryEntity> deliveries = new ArrayList<>(
                Arrays.asList(delivery1, delivery2, delivery3, delivery4, delivery5, delivery6, delivery7)
        );

        when(deliveryService.findAll()).thenReturn(deliveries);
        List<ReportDTO> listResult = deliveryService.generateReport();
        assertEquals(deliveries.size(), listResult.size());

    }
}
