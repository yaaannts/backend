package com.customs.repository;

import com.customs.model.Declaration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeclarationRepository extends MongoRepository<Declaration, String> {

    Optional<Declaration> findByDeclNo(String declNo);

    List<Declaration> findByPassport(String passport);

    List<Declaration> findByArrivalDateBetween(LocalDate start, LocalDate end);

    List<Declaration> findByStatus(Declaration.DeclarationStatus status);

    boolean existsByDeclNo(String declNo);
}