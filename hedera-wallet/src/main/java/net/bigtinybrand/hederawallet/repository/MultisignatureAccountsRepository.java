package net.bigtinybrand.hederawallet.repository;

import net.bigtinybrand.hederawallet.entity.MultisignatureAccounts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MultisignatureAccountsRepository extends JpaRepository<MultisignatureAccounts, String> {
    // Custom query method example
    List<MultisignatureAccounts> findByThreshold(Integer threshold);

    // JpaRepository methods (no need to explicitly declare them, added here for illustration)
    // Save the given entity
    <S extends MultisignatureAccounts> S save(S entity);


    List<MultisignatureAccounts> findByAssociatedAccountIdsContaining(String accountId);


    // Retrieve an entity by its id
    Optional<MultisignatureAccounts> findById(String accountId);

    // Check if an entity with the given id exists
    boolean existsById(String accountId);

    // Retrieve all entities
    List<MultisignatureAccounts> findAll();

    // Delete the entity with the given id
    void deleteById(String accountId);

    // Delete the given entity
    void delete(MultisignatureAccounts entity);

    // Delete all entities
    void deleteAll();
}
