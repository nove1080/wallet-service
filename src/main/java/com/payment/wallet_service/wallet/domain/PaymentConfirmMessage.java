package com.payment.wallet_service.wallet.domain;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record PaymentConfirmMessage (
    Long buyerId,
    String orderId,
    String paymentKey,
    List<SimplePaymentOrder> paymentOrders,
    Long amount,
    LocalDateTime confirmedAt
) {
    @Builder
    record SimplePaymentOrder (
        Long sellerId,
        Long productId,
        Long amount
    ){
    }
}







