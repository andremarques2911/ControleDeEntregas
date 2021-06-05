package com.pucrs.controleentregas.services;

import com.pucrs.controleentregas.entities.DeliveryEntity;
import com.pucrs.controleentregas.entities.OperatorEntity;
import com.pucrs.controleentregas.repositories.DeliveryRepository;
import com.pucrs.controleentregas.repositories.OperatorRepository;
import com.pucrs.controleentregas.utils.exceptions.OperatorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("unit-test")
public class OperatorServiceTest {
    @MockBean
    private OperatorRepository operatorRepository;

    @MockBean
    private DeliveryRepository deliveryRepository;

    @SpyBean
    private OperatorService operatorService;

    @DisplayName("Teste deleta um operador")
    @Test
    public void deleteOperatorTest() {
        OperatorEntity operatorMock = OperatorEntity.builder().id(1L).firstName("Guilherme").lastName("Carvalho").build();
        when(operatorRepository.findById(any())).thenReturn(Optional.ofNullable(operatorMock));
        operatorService.deleteById(operatorMock.getId());
        Mockito.verify(operatorService, times(1)).deleteById(operatorMock.getId());
    }

    @DisplayName("Teste deleta um operador com entrega vinculada retorna exceção")
    @Test
    public void deleteOperatorTestWithDeliveryRegistered_thenException() throws OperatorException {
        OperatorEntity operatorMock = OperatorEntity.builder()
                .id(1L).firstName("Guilherme")
                .lastName("Carvalho")
                .deliveries(Arrays.asList(DeliveryEntity.builder().id(1L).build()))
                .build();
        DeliveryEntity deliveryEntity = DeliveryEntity.builder().id(1L).description("Caixa").operator(operatorMock).build();
        when(operatorRepository.findById(any())).thenReturn(Optional.ofNullable(operatorMock));
        when(deliveryRepository.findById(any())).thenReturn(Optional.ofNullable(deliveryEntity));

        Assertions.assertThrows(OperatorException.class, () -> {
            operatorService.deleteById(operatorMock.getId());
        });
    }
}
