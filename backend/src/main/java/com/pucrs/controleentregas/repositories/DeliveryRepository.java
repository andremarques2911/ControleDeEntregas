package com.pucrs.controleentregas.repositories;

import com.pucrs.controleentregas.entities.DeliveryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DeliveryRepository extends CrudRepository<DeliveryEntity, Long> {

    List<DeliveryEntity> findAll();

    @Query("from DeliveryEntity vo where upper(vo.description) like concat('%', upper(:description), '%')")
    List<DeliveryEntity> findAllByDescriptionLike(@Param("description") String description);

    List<DeliveryEntity> findAllByResidentId(Long id);

    @Query("from DeliveryEntity vo where vo.withdrawalDate is null order by vo.registerDate")
    List<DeliveryEntity> findAllDeliveriesNotWithdrawn();

    Integer countDeliveryEntitiesByWithdrawalDateBefore(LocalDateTime dateTime);

    @Query("from DeliveryEntity vo where vo.registerDate is not null and vo.withdrawalDate is not null order by vo.registerDate")
    List<DeliveryEntity> findAllDeliveriesWithdrawn();

}
