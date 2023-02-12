package com.yunbaek.barogodelivery.delivery.domain;

import com.yunbaek.barogodelivery.delivery.dto.DeliveryResponse;
import com.yunbaek.barogodelivery.delivery.dto.DeliverySearchDto;

import java.util.List;

public interface DeliveryRepositoryCustom {

    List<DeliveryResponse> findByMemberId(long memberId, DeliverySearchDto searchDto);

}
