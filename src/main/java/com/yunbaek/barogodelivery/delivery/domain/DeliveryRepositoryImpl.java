package com.yunbaek.barogodelivery.delivery.domain;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
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
    public List<DeliveryResponse> findByMemberId(long memberId, DeliverySearchDto searchDto) {
        JPAQuery<?> query = queryFactory.from(delivery)
                .where(
                        ltDeliveryId(searchDto.getLastDeliveryId()),
                        delivery.memberId.eq(memberId),
                        betweenDate(searchDto.getFirstDate(), searchDto.getLastDate())
                )
                .orderBy(delivery.id.desc())
                .limit(searchDto.getSize());

        return query.select(Projections.constructor(DeliveryResponse.class,
                delivery.id.as("deliveryId"),
                delivery.memberId,
                delivery.riderId,
                delivery.status.as("deliveryStatus"),
                delivery.arrivalAddress.zipCode.as("arrivalZipCode"),
                delivery.arrivalAddress.state.as("arrivalState"),
                delivery.arrivalAddress.city.as("arrivalCity"),
                delivery.arrivalAddress.street.as("arrivalStreet"),
                delivery.arrivalAddress.detail.as("arrivalDetail"),
                delivery.departureAddress.zipCode.as("departureZipCode"),
                delivery.departureAddress.state.as("departureState"),
                delivery.departureAddress.city.as("departureCity"),
                delivery.departureAddress.street.as("departureStreet"),
                delivery.departureAddress.detail.as("departureDetail"))
        ).fetch();
    }


    private BooleanExpression ltDeliveryId(Long deliveryId) {
        if (deliveryId == null) {
            return null;
        }

        return delivery.id.lt(deliveryId);
    }

    private BooleanExpression betweenDate(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            LocalDate today = LocalDate.now();
            LocalDateTime startOfToday = today.atStartOfDay();
            return delivery.createdDate.between(startOfToday, LocalDateTime.now());
        }
        LocalDateTime endOfEndDay = end.atStartOfDay().plusDays(1).minusSeconds(1L);
        LocalDateTime startOfStartDat = start.atStartOfDay();
        return delivery.createdDate.between(startOfStartDat, endOfEndDay);
    }
}
