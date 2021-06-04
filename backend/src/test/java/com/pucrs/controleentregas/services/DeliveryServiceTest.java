package com.pucrs.controleentregas.services;

import com.pucrs.controleentregas.dtos.EditDeliveryDTO;
import com.pucrs.controleentregas.entities.DeliveryEntity;
import com.pucrs.controleentregas.entities.ResidentEntity;
import com.pucrs.controleentregas.repositories.DeliveryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

//    @Test
//    public void mustReturnCorrectProduct() {
//        ProductEntity productMock = ProductEntity.builder().idProd(1L).name("Visa").build();
//        when(productRepository.findByIdProd(any())).thenReturn(Optional.ofNullable(productMock));
//        ProductEntity result = productService.getProductById(1L);
//        assertEquals(result.getIdProd(), productMock.getIdProd());
//        assertEquals(result.getName(), productMock.getName());
//    }


    @Test
    public void registerWithdrawTest() {
        ResidentEntity residentMock = ResidentEntity.builder().name("Andre").apartment(100).id(1L).build();
        DeliveryEntity deliveryMock = DeliveryEntity.builder().id(1L).description("caixa").apartment(100).build();
        EditDeliveryDTO editDeliveryMock = EditDeliveryDTO.builder().deliveryCode(deliveryMock.getId()).residentCode(residentMock.getId()).build();
        when(residentService.findByActiveId(any())).thenReturn(residentMock);
        when(deliveryRepository.findById(any())).thenReturn(Optional.of(deliveryMock));
        DeliveryEntity result = deliveryService.registerWithdrawal(editDeliveryMock);
        assertNotNull(result.getWithdrawalDate());
    }
}
