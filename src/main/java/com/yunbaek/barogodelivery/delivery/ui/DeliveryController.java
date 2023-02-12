package com.yunbaek.barogodelivery.delivery.ui;

import com.yunbaek.barogodelivery.auth.domain.LoginMember;
import com.yunbaek.barogodelivery.delivery.application.DeliveryService;
import com.yunbaek.barogodelivery.delivery.dto.DeliveryResponse;
import com.yunbaek.barogodelivery.delivery.dto.DeliverySearchDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    /**
     *
     * 배송 조회 api (no-offset 페이징 처리)
     *
     * @param searchDto - 검색 조건
     *                  - lastDeliveryId : 마지막 배송 id
     *                  - firstDate : 검색 시작 날짜
     *                  - lastDate : 검색 마지막 날짜
     *                  - size : 조회할 데이터 수(기본값 10)
     */
    @GetMapping("api/v1/deliveries")
    public ResponseEntity<List<DeliveryResponse>> findDeliveryByMemberId(
            @AuthenticationPrincipal(expression = "loginMember") LoginMember loginMember,
            DeliverySearchDto searchDto) {
        List<DeliveryResponse> deliveryByMemberId = deliveryService.findDeliveryByMemberId(loginMember.id(), searchDto);
        return ResponseEntity.ok(deliveryByMemberId);
    }

    @PostMapping("/deliveries")
    public void createDelivery() {
        deliveryService.createDeliveries();
        System.out.println("createDelivery");
    }

}
