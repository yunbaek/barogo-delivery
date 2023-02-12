package com.yunbaek.barogodelivery.delivery.dto;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class DeliverySearchDto {

    @Nullable
    private Long lastDeliveryId;

    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate firstDate;

    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastDate;

    @NotNull
    private int size = 10;
}
