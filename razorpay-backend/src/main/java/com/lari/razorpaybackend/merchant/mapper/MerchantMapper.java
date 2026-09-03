package com.lari.razorpaybackend.merchant.mapper;

import com.lari.razorpaybackend.merchant.dto.MerchantResponse;
import com.lari.razorpaybackend.merchant.dto.MerchantSignupRequest;
import com.lari.razorpaybackend.merchant.entity.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MerchantMapper {

    Merchant toEntityFromMerchantSignupRequest(MerchantSignupRequest request);

    MerchantResponse toResponse(Merchant merchant);
}
