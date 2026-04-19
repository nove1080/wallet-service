package com.payment.wallet_service.wallet.infrastructure.jpa.mapper;

import com.payment.wallet_service.wallet.domain.Wallet;
import com.payment.wallet_service.wallet.infrastructure.jpa.entity.JpaWalletEntity;

public abstract class JpaWalletMapper {

    public static Wallet mapToDomain(JpaWalletEntity jpaWalletEntity) {
        return Wallet.builder()
            .id(jpaWalletEntity.getId())
            .memberId(jpaWalletEntity.getMemberId())
            .balance(jpaWalletEntity.getBalance())
            .build();
    }

    public static JpaWalletEntity mapToEntity(Wallet wallet) {
        return JpaWalletEntity.builder()
            .id(wallet.id())
            .memberId(wallet.memberId())
            .balance(wallet.balance())
            .build();
    }

}
