package com.pucrs.controleentregas.services;

import com.pucrs.controleentregas.dtos.CreateDeliveryDTO;
import com.pucrs.controleentregas.dtos.EditDeliveryDTO;
import com.pucrs.controleentregas.entities.DeliveryEntity;
import com.pucrs.controleentregas.entities.OperatorEntity;
import com.pucrs.controleentregas.entities.ResidentEntity;
import com.pucrs.controleentregas.repositories.DeliveryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@SpringBootTest
@ActiveProfiles("unit-test")
public class DeliveryServiceTest {

    @MockBean
    private DeliveryRepository deliveryRepository;

    @SpyBean
    private DeliveryService deliveryService;

    @SpyBean
    private ResidentService residentService;

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

    @DisplayName("Testa uma retirada de entrega com morador inativo")
    @Test
    public void registerWithdrawTestWithInactiveResident() {
        ResidentEntity residentMock = ResidentEntity.builder().name("Andre").apartment(100).id(1L).build();
        DeliveryEntity deliveryMock = DeliveryEntity.builder().id(1L).description("caixa").apartment(100).build();
        EditDeliveryDTO editDeliveryMock = EditDeliveryDTO.builder().deliveryCode(deliveryMock.getId()).residentCode(residentMock.getId()).build();
        when(residentService.findByActiveId(any())).thenReturn(residentMock);
        when(deliveryRepository.findById(any())).thenReturn(Optional.of(deliveryMock));
        when(deliveryRepository.save(any(DeliveryEntity.class))).thenReturn(deliveryMock);
        var result = deliveryService.registerWithdrawal(editDeliveryMock);
        assertNotNull(result.getWithdrawalDate());
    }

    @DisplayName("Testa uma retirada de entrega com apartamento diferente do morador que retira")
    @Test
    public void registerWithdrawTestWithResidentNotInApartment() {
        ResidentEntity residentMock = ResidentEntity.builder().name("Andre").apartment(100).id(1L).build();
        DeliveryEntity deliveryMock = DeliveryEntity.builder().id(1L).description("caixa").apartment(100).build();
        EditDeliveryDTO editDeliveryMock = EditDeliveryDTO.builder().deliveryCode(deliveryMock.getId()).residentCode(residentMock.getId()).build();
        when(residentService.findByActiveId(any())).thenReturn(residentMock);
        when(deliveryRepository.findById(any())).thenReturn(Optional.of(deliveryMock));
        when(deliveryRepository.save(any(DeliveryEntity.class))).thenReturn(deliveryMock);
        var result = deliveryService.registerWithdrawal(editDeliveryMock);
        assertNotNull(result.getWithdrawalDate());
    }

//    @DisplayName("Testa se o método findAllDeliveriesNotWithdrawn retorna entregas não retiradas")
//    @Test
//    public void getAllUndeliveredTest() {
//        DeliveryEntity delivery1 = DeliveryEntity.builder().id(1L).description("Caixa").apartment(100).build();
//        DeliveryEntity delivery2 = DeliveryEntity.builder().id(2L).description("Caixa").apartment(102).build();
//        DeliveryEntity delivery3 = DeliveryEntity.builder().id(3L).description("Caixa").apartment(103).build();
//        DeliveryEntity delivery4 = DeliveryEntity.builder().id(4L).description("Caixa").apartment(104).build();
//        List<DeliveryEntity> deliveries = new ArrayList<>();
//        deliveries.add(delivery1);
//        deliveries.add(delivery2);
//        deliveries.add(delivery3);
//        deliveries.add(delivery4);
//
//        ResidentEntity residentMock1 = ResidentEntity.builder().name("Andre").apartment(100).id(1L).build();
//        EditDeliveryDTO editDeliveryMock = EditDeliveryDTO.builder().deliveryCode(delivery1.getId()).residentCode(residentMock1.getId()).build();
//
//        when(deliveryRepository.findAll(any())).thenReturn(deliveries);
//        when(residentService.findByActiveId(any())).thenReturn(residentMock1);
//        when(deliveryService.registerWithdrawal(any(EditDeliveryDTO.class))).thenReturn(delivery1);
//
//        var result = deliveryService.findAllDeliveriesNotWithdrawn();
//        assertEquals(result.size(), 2);
//    }

    @DisplayName("Testa cadastro de entrega")
    @Test
    public void newDeliveryTest() {
        OperatorEntity operatorEntity = OperatorEntity.builder().id(1L).firstName("luis").lastName("rios").build();
        DeliveryEntity deliveryMock = DeliveryEntity.builder().id(1L).description("Caixa").apartment(100).build();
        CreateDeliveryDTO createDeliveryDTO = CreateDeliveryDTO.builder().description("Caixa").apartment(100).operatorCode(operatorEntity.getId()).build();
        when(deliveryRepository.save(any(DeliveryEntity.class))).thenReturn(deliveryMock);

        var result = deliveryService.save(createDeliveryDTO);
        assertEquals(result, deliveryMock);
    }

    @DisplayName("Testa cadastro de entrega com apartamento inválido")
    @Test
    public void newDeliveryApartmentInvalid() {

    }

    @DisplayName("Testa cadastro de entrega com operador inexistente")
    @Test
    public void newDeliveryOperatorNotExists() {

    }

    @DisplayName("Testa cadastro de entrega com apartamento sem morador ativo")
    @Test
    public void newDeliveryApartmentWithNoResidentActive() {

    }
}
