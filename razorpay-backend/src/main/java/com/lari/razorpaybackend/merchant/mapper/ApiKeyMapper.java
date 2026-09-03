package com.lari.razorpaybackend.merchant.mapper;

import com.lari.razorpaybackend.merchant.dto.ApiKeyCreateResponse;
import com.lari.razorpaybackend.merchant.dto.ApiKeyResponse;
import com.lari.razorpaybackend.merchant.entity.ApiKey;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApiKeyMapper {

    ApiKeyCreateResponse toCreateResponse(ApiKey apiKey);
    ApiKeyResponse toResponse(ApiKey apiKey);
    List<ApiKeyResponse> toResponseList(List<ApiKey> apiKeys);

}
