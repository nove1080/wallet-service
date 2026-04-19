package com.payment.wallet_service.wallet.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record PaymentConfirmMessage (
    Long buyerId,
    String orderId,
    String paymentKey,
    List<PaymentOrder> paymentOrders,
    Long amount,
    LocalDateTime confirmedAt
) {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class MessageType {
        public static final String PAYMENT_CONFIRM_SUCCESS = "payment-confirm-success";
    }

    public Set<Long> getSellerIds() {
        return paymentOrders.stream()
            .map(PaymentOrder::sellerId)
            .collect(Collectors.toSet());
    }
}
