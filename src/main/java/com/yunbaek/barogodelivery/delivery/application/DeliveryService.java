package com.yunbaek.barogodelivery.delivery.application;

import com.yunbaek.barogodelivery.common.exception.NotFoundException;
import com.yunbaek.barogodelivery.delivery.domain.Address;
import com.yunbaek.barogodelivery.delivery.domain.Delivery;
import com.yunbaek.barogodelivery.delivery.domain.DeliveryRepository;
import com.yunbaek.barogodelivery.delivery.dto.DeliveryResponse;
import com.yunbaek.barogodelivery.delivery.dto.DeliverySearchDto;
import com.yunbaek.barogodelivery.delivery.dto.DeliveryUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryService {

    private static final String INVALID_SEARCH_DATE_ERROR_MESSAGE = "검색 기간은 3일 이내로 설정해주세요.";
    private static final int MAXIMUM_SEARCH_DATE_PERIOD = 2; // 오늘 포함 차이이므로 Period 는 2일 이내
    private static final String NOT_FOUND_DELIVERY_FOR_UPDATE_MESSAGE_FORMAT = "해당 배송이 존재하지 않습니다. deliveryId : %d , memberId : %d";
    private final DeliveryRepository deliveryRepository;

    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Transactional(readOnly = true)
    public List<DeliveryResponse> findDeliveryByMemberId(long memberId, DeliverySearchDto searchDto) {
        validateSearchDate(searchDto);
        return deliveryRepository.findByMemberId(memberId, searchDto);
    }

    private static void validateSearchDate(DeliverySearchDto searchDto) {
        Period between = Period.between(searchDto.getFirstDate(), searchDto.getLastDate());
        if (moreThanMaximumSearchDatePeriod(between)) {
            throw new IllegalArgumentException(INVALID_SEARCH_DATE_ERROR_MESSAGE);
        }
    }

    private static boolean moreThanMaximumSearchDatePeriod(Period between) {
        return between.getDays() > MAXIMUM_SEARCH_DATE_PERIOD || between.getMonths() != 0 || between.getYears() != 0;
    }

    public void createDeliveries() {
        ArrayList<Delivery> deliveries = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Delivery delivery = Delivery.of(1L, 1L, 1L,
                    Address.of("12345", "서울시", "강남구", "역삼동", i + "길 123-123"),
                    Address.of("12345", "서울시", "강남구", "삼성동", i + "길 123-123"));
            deliveries.add(delivery);
        }
        deliveryRepository.saveAll(deliveries);
    }

    @Transactional
    public void updateDelivery(long memberId, Long deliveryId, DeliveryUpdateRequest request) {
        Delivery delivery = deliveryRepository.findByIdAndMemberId(deliveryId, memberId)
                .orElseThrow(() ->
                        new NotFoundException(String.format(NOT_FOUND_DELIVERY_FOR_UPDATE_MESSAGE_FORMAT, deliveryId, memberId)));

        boolean enableUpdateStatus = delivery.enableUpdateStatus();
        if (!enableUpdateStatus) {
            throw new IllegalArgumentException("배송 상태를 변경할 수 없습니다.");
        }

        Address newDepartureAddress = newDepartureAddress(request);
        delivery.updateDepartureAddress(newDepartureAddress);
    }

    private Address newDepartureAddress(DeliveryUpdateRequest request) {
        return Address.of(
                request.getZipCode(),
                request.getState(),
                request.getCity(),
                request.getStreet(),
                request.getDetail()
        );
    }
}
