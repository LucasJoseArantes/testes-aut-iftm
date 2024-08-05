package com.iftm.client;

import com.iftm.client.entities.Client;
import com.iftm.client.repositories.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class DsclientApplicationTests {

    @Autowired
    private ClientRepository repository;

    // Rafael Andrade
    /**
     * Testa a busca de um cliente pelo nome, ignorando a diferença entre maiúsculas e minúsculas.
     */
    @Test
    void testFindByNameIgnoreCase() {
        String name = "clarice lispector";
        Client client = repository.findByNameIgnoreCase(name);
        Assertions.assertNotNull(client, "O cliente deve existir no banco de dados.");
        Assertions.assertEquals(name.toLowerCase(), client.getName().toLowerCase(), "O nome do cliente deve ser case insensitive.");

        Client nonExistingClient = repository.findByNameIgnoreCase("Non Existing Name");
        Assertions.assertNull(nonExistingClient, "O cliente 'Non Existing Name' não deve existir no banco de dados.");
    }

    /**
     * Testa a busca de clientes que contém uma parte do nome, ignorando a diferença entre maiúsculas e minúsculas.
     */
    @Test
    void testFindByNameContainingIgnoreCase() {
        List<Client> clients = repository.findByNameContainingIgnoreCase("lispector");
        Assertions.assertFalse(clients.isEmpty(), "Deve haver clientes com 'lispector' no nome.");

        List<Client> nonExistingClients = repository.findByNameContainingIgnoreCase("nonexistingpart");
        Assertions.assertTrue(nonExistingClients.isEmpty(), "Não deve haver clientes com 'nonexistingpart' no nome.");

        List<Client> allClients = repository.findByNameContainingIgnoreCase("");
        List<Client> allClientsDirect = repository.findAll();
        Assertions.assertEquals(allClientsDirect.size(), allClients.size(), "A busca com nome vazio deve retornar todos os clientes.");
    }

    // Lucas Jose
    /**
     * Testa a busca de clientes com salário superior a um valor específico.
     */
    @Test
    void testFindByIncomeGreaterThan() {
        List<Client> clients = repository.findByIncomeGreaterThan(5000.0);
        Assertions.assertFalse(clients.isEmpty(), "Deve haver clientes com salário superior a 5000.0.");
    }

    /**
     * Testa a busca de clientes com salário inferior a um valor específico.
     */
    @Test
    void testFindByIncomeLessThan() {
        List<Client> clients = repository.findByIncomeLessThan(2000.0);
        Assertions.assertFalse(clients.isEmpty(), "Deve haver clientes com salário inferior a 2000.0.");
    }

    /**
     * Testa a busca de clientes com salário dentro de uma faixa de valores específicos.
     */
    @Test
    void testFindByIncomeBetween() {
        List<Client> clients = repository.findByIncomeBetween(1000.0, 3000.0);
        Assertions.assertFalse(clients.isEmpty(), "Deve haver clientes com salário entre 1000.0 e 3000.0.");
    }

    /**
     * Testa a busca de clientes pela data de nascimento entre duas datas específicas.
     */
    @Test
    void testFindByBirthDateBetween() {
        Instant startDate = Instant.parse("2017-12-25T20:30:50Z");
        Instant endDate = Instant.now();
        List<Client> clients = repository.findByBirthDateBetween(startDate, endDate);
        Assertions.assertFalse(clients.isEmpty(), "Deve haver clientes com data de nascimento entre '2017-12-25T20:30:50Z' e o momento atual.");
    }

    // Joao Vitor Moreira
    /**
     * Testa a atualização de um cliente, modificando o nome, o salário e a data de nascimento.
     */
    @Test
    void testUpdateClient() {
        Optional<Client> optionalClient = repository.findById(1L);
        Assertions.assertTrue(optionalClient.isPresent(), "O cliente com ID 1 deve existir.");

        Client client = optionalClient.get();
        client.setName("New Name");
        client.setIncome(7000.0);
        client.setBirthDate(Instant.parse("2000-01-01T00:00:00Z"));

        repository.save(client);

        Client updatedClient = repository.findById(1L).get();
        Assertions.assertEquals("New Name", updatedClient.getName(), "O nome do cliente deve ser 'New Name'.");
        Assertions.assertEquals(7000.0, updatedClient.getIncome(), "O salário do cliente deve ser 7000.0.");
        Assertions.assertEquals(Instant.parse("2000-01-01T00:00:00Z"), updatedClient.getBirthDate(), "A data de nascimento do cliente deve ser '2000-01-01T00:00:00Z'.");
    }

    /**
     * Testa a busca de um cliente por um ID específico.
     */
    @Test
    void testFindById() {
        Long clientId = 1L;
        Optional<Client> optionalClient = repository.findById(clientId);
        Assertions.assertTrue(optionalClient.isPresent(), "O cliente com ID " + clientId + " deve existir.");

        Long nonExistingClientId = 999L;
        Optional<Client> nonExistingClient = repository.findById(nonExistingClientId);
        Assertions.assertFalse(nonExistingClient.isPresent(), "O cliente com ID " + nonExistingClientId + " não deve existir.");
    }

    /**
     * Testa a deleção de um cliente por um ID específico.
     */
    @Test
    void testDeleteById() {
        Long clientId = 1L;
        repository.deleteById(clientId);

        Optional<Client> optionalClient = repository.findById(clientId);
        Assertions.assertFalse(optionalClient.isPresent(), "O cliente com ID " + clientId + " deve ter sido deletado.");
    }

    /**
     * Testa a busca de todos os clientes.
     */
    @Test
    void testFindAll() {
        List<Client> clients = repository.findAll();
        Assertions.assertNotNull(clients, "A lista de clientes não deve ser nula.");
        Assertions.assertFalse(clients.isEmpty(), "Deve haver pelo menos um cliente no banco de dados.");
    }
}
