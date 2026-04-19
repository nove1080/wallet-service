package com.payment.wallet_service.wallet.domain;

public record PaymentOrder(
    Long sellerId,
    Long productId,
    Long amount
) {

}
