package com.yunbaek.barogodelivery.delivery.domain;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yunbaek.barogodelivery.delivery.dto.DeliveryResponse;
import com.yunbaek.barogodelivery.delivery.dto.DeliverySearchDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.yunbaek.barogodelivery.delivery.domain.QDelivery.delivery;

public class DeliveryRepositoryImpl implements DeliveryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public DeliveryRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<DeliveryResponse> findByMemberId(DeliverySearchDto searchDto) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfToday = today.atStartOfDay();
        queryFactory.from(delivery)
                .where(
                        ltDeliveryId(searchDto.getLastDeliveryId()),
                        delivery.memberId.eq(1L),
                        delivery.createdDate.between(startOfToday, LocalDateTime.now())
                );

        return queryFactory.select(Projections.fields(DeliveryResponse.class,
                delivery.id.as("deliveryId"),
                delivery.memberId,
                delivery.riderId,
                delivery.status.as("deliveryStatus"),
                delivery.arrivalAddress.as("arrivalAddress"),
                delivery.departureAddress.as("destinationAddress"))
        ).fetch();
    }


    private BooleanExpression ltDeliveryId(Long deliveryId) {
        if (deliveryId == null) {
            return null;
        }

        return delivery.id.lt(deliveryId);
    }
}
