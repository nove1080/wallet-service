package com.payment.wallet_service.wallet.infrastructure.jpa.mapper;

import com.payment.wallet_service.wallet.domain.WalletTransaction;
import com.payment.wallet_service.wallet.infrastructure.jpa.entity.JpaWalletTransactionEntity;

public abstract class JpaWalletTransactionMapper {

    public static JpaWalletTransactionEntity mapToEntity(WalletTransaction walletTransaction) {
        return JpaWalletTransactionEntity.builder()
            .walletId(walletTransaction.walletId())
            .orderId(walletTransaction.orderId())
            .transactionType(walletTransaction.transactionType())
            .amount(walletTransaction.amount())
            .build();
    }

}
