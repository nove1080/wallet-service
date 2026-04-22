package com.payment.wallet_service.wallet.infrastructure.jpa.repository;

import com.payment.wallet_service.wallet.domain.Wallet;
import com.payment.wallet_service.wallet.infrastructure.jpa.entity.JpaWalletEntity;
import com.payment.wallet_service.wallet.infrastructure.jpa.entity.JpaWalletTransactionEntity;
import com.payment.wallet_service.wallet.infrastructure.jpa.mapper.JpaWalletMapper;
import com.payment.wallet_service.wallet.infrastructure.jpa.mapper.JpaWalletTransactionMapper;
import com.payment.wallet_service.wallet.repository.WalletRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class JpaWalletRepositoryAdapter implements WalletRepository {

    private final JpaWalletRepository jpaWalletRepository;
    private final JpaWalletTransactionRepository jpaWalletTransactionRepository;

    @Override
    @Transactional(readOnly = true)
    public Set<Wallet> selectWallets(Set<Long> sellerIds) {
        return jpaWalletRepository.findByMemberIdIn(sellerIds)
            .stream()
            .map(JpaWalletMapper::mapToDomain)
            .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void saveAll(List<Wallet> wallets) {
        jpaWalletRepository.saveAll(wallets.stream()
            .map(JpaWalletMapper::mapToEntity)
            .toList());
        jpaWalletTransactionRepository.saveAll(wallets.stream()
                .flatMap(wallet -> wallet.transactions().stream())
                .map(JpaWalletTransactionMapper::mapToEntity)
                .toList());
    }

    @Override
    public Boolean existsSettlement(String orderId) {
        return jpaWalletTransactionRepository.existsByOrderId(orderId);
    }
}

interface JpaWalletRepository extends JpaRepository<JpaWalletEntity, Long> {
    List<JpaWalletEntity> findByMemberIdIn(Set<Long> memberIds);
}

interface JpaWalletTransactionRepository extends JpaRepository<JpaWalletTransactionEntity, Long> {

    Boolean existsByOrderId(String orderId);

}
