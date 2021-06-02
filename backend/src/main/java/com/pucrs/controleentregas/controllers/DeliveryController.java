package com.pucrs.controleentregas.controllers;

import com.pucrs.controleentregas.dtos.CreateDeliveryDTO;
import com.pucrs.controleentregas.dtos.DashboardDTO;
import com.pucrs.controleentregas.dtos.EditDeliveryDTO;
import com.pucrs.controleentregas.entities.DeliveryEntity;
import com.pucrs.controleentregas.services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("deliveries")
public class DeliveryController {

    private final DeliveryService service;

    @Autowired
    public DeliveryController(DeliveryService service) {
        this.service = service;
    }

    @ResponseBody
    @PostMapping
    public DeliveryEntity save(@RequestBody CreateDeliveryDTO createDeliveryDTO) {
        return this.service.save(createDeliveryDTO);
    }

    @ResponseBody
    @GetMapping
    public List<DeliveryEntity> findAll() {
        return this.service.findAll();
    }

    @ResponseBody
    @PutMapping
    public DeliveryEntity registerWithdrawal(@RequestBody EditDeliveryDTO editDeliveryDTO) {
        return this.service.registerWithdrawal(editDeliveryDTO);
    }

    @ResponseBody
    @GetMapping("/findAllByDescription/{description}")
    public List<DeliveryEntity> findAllByDescription(@PathVariable String description) {
        return this.service.findAllByDescriptionLike(description);
    }

    @ResponseBody
    @GetMapping("/findAllDeliveriesNotWithdrawn")
    public List<DeliveryEntity> findAllDeliveriesNotWithdrawn() {
        return this.service.findAllDeliveriesNotWithdrawn();
    }

    @ResponseBody
    @GetMapping("/searchDashboardInformation")
    public DashboardDTO searchDashboardInformation() {
        return this.service.searchDashboardInformation();
    }

}
