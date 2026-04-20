package com.payment.wallet_service.wallet.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;
import lombok.Builder;

@Builder(toBuilder = true)
public record Wallet(
    Long id,
    Long memberId,
    BigDecimal balance,
    List<WalletTransaction> transactions
) {

    public Wallet settle(String orderId, List<PaymentOrder> paymentOrders) {
        return this.toBuilder()
            .transactions(
                paymentOrders.stream()
                    .filter(isMyTransaction())
                    .map(it -> WalletTransaction.builder()
                        .walletId(this.id)
                        .orderId(orderId)
                        .amount(BigDecimal.valueOf(it.amount()))
                        .transactionType(TransactionType.CREDIT)
                        .build()
                    ).toList())
            .balance(addBalance(paymentOrders))
            .build();
    }

    private BigDecimal addBalance(List<PaymentOrder> paymentOrders) {
        return balance.add(paymentOrders.stream()
            .filter(isMyTransaction())
            .map(it -> BigDecimal.valueOf(it.amount()))
            .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    private Predicate<PaymentOrder> isMyTransaction() {
        return it -> memberId.equals(it.sellerId());
    }
}
