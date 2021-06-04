package com.pucrs.controleentregas.services;

import com.pucrs.controleentregas.dtos.EditDeliveryDTO;
import com.pucrs.controleentregas.entities.OperatorEntity;
import com.pucrs.controleentregas.repositories.OperatorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("unit-test")
public class OperatorServiceTest {
    @MockBean
    private OperatorRepository operatorRepository;

    @SpyBean
    private OperatorService operatorService;

//    @Test
//    public void deleteOperatorTest() {
//        OperatorEntity operatorMock = OperatorEntity.builder().id(1L).firstName("Guilherme").lastName("Carvalho").build();
//        when(operatorRepository.findById(any())).thenReturn(Optional.ofNullable(operatorMock));
//        operatorService.deleteById(1L);
//        Mockito.verify(operatorRepository, times(1)).deleteById(operatorMock.getId());
//    }
}
