package com.payment.wallet_service.wallet.infrastructure.jpa.repository;

import com.payment.wallet_service.wallet.domain.Wallet;
import com.payment.wallet_service.wallet.infrastructure.jpa.entity.JpaWalletEntity;
import com.payment.wallet_service.wallet.infrastructure.jpa.mapper.JpaWalletMapper;
import com.payment.wallet_service.wallet.repository.WalletRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JpaWalletRepositoryAdapter implements WalletRepository {

    private final JpaWalletRepository jpaWalletRepository;

    @Override
    public Set<Wallet> selectWallets(Set<Long> sellerIds) {
        return jpaWalletRepository.findByMemberIdIn(sellerIds)
            .stream()
            .map(JpaWalletMapper::mapToDomain)
            .collect(Collectors.toSet());
    }
}

interface JpaWalletRepository extends JpaRepository<JpaWalletEntity, Long> {
    List<JpaWalletEntity> findByMemberIdIn(Set<Long> memberIds);
}
