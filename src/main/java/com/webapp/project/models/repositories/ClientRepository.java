package com.webapp.project.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.webapp.project.models.Clients;

public interface ClientRepository extends JpaRepository<Clients, Integer> {
    public Clients findByFirstName(String firstName);

    public Clients findByLastName(String lastName);

    public Clients findByEmail(String email);



}
