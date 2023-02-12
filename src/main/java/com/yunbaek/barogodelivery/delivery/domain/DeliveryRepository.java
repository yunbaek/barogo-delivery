package com.yunbaek.barogodelivery.delivery.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long>, DeliveryRepositoryCustom {
    Optional<Delivery> findByIdAndMemberId(Long id, Long memberId);
}
