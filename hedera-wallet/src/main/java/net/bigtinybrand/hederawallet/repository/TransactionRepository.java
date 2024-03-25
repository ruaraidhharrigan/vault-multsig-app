package net.bigtinybrand.hederawallet.repository;

import net.bigtinybrand.hederawallet.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findAllByAccountId(String accountId);
    Optional<Transaction> findByAccountIdAndId(String accountId, UUID id);
}
