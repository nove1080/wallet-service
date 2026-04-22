package com.payment.wallet_service.wallet.domain;

import lombok.Builder;

@Builder
public record PaymentOrder(
    Long sellerId,
    Long productId,
    Long amount
) {

}
