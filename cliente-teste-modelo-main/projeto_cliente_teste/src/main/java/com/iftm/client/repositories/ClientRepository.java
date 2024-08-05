package com.iftm.client.repositories;

import com.iftm.client.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    // Buscar cliente pelo nome, ignorando case
    Client findByNameIgnoreCase(String name);

    // Buscar clientes contendo parte do nome, ignorando case
    List<Client> findByNameContainingIgnoreCase(String name);

    // Buscar clientes com salários superiores a um valor
    List<Client> findByIncomeGreaterThan(Double income);

    // Buscar clientes com salários inferiores a um valor
    List<Client> findByIncomeLessThan(Double income);

    // Buscar clientes com salários entre dois valores
    List<Client> findByIncomeBetween(Double minIncome, Double maxIncome);

    // Buscar clientes pela data de nascimento entre duas datas
    List<Client> findByBirthDateBetween(Instant startDate, Instant endDate);
}
