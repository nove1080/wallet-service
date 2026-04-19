package com.payment.wallet_service.wallet.domain;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record WalletTransaction(
    Long walletId,
    String orderId,
    TransactionType transactionType,
    BigDecimal amount
) {
}
