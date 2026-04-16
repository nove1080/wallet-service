package com.payment.wallet_service.wallet.domain;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

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

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class MessageType {
        public static final String PAYMENT_CONFIRM_MESSAGE = "payment-confirm-message";
    }
}
