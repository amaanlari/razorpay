package com.lari.razorpaybackend.payment.mapper;

import com.lari.razorpaybackend.payment.dto.response.OrderResponse;
import com.lari.razorpaybackend.payment.entity.OrderRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    OrderResponse toResponse(OrderRecord orderRecord);

    List<OrderResponse> toResponseList(List<OrderRecord> orders);
}
