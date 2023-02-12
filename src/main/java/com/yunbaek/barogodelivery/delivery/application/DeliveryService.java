package com.yunbaek.barogodelivery.delivery.application;

import com.yunbaek.barogodelivery.delivery.domain.DeliveryRepository;
import com.yunbaek.barogodelivery.delivery.dto.DeliveryResponse;
import com.yunbaek.barogodelivery.delivery.dto.DeliverySearchDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Transactional(readOnly = true)
    public List<DeliveryResponse> findDeliveryByMemberId(DeliverySearchDto searchDto) {
        return deliveryRepository.findByMemberId(searchDto);
    }

}
