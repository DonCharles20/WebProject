package com.webapp.project.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.webapp.project.models.UsersTable;
/*This Spring Data JPA repository helps reduce the amount of code needed for implementing a DAO pattern
 * for the Clients entity. It extends the JpaRepository interface and provides methods for CRUD operations in Spring
 * Applications.
 */
public interface UserRepository extends JpaRepository<UsersTable, Integer> {
    public UsersTable findByFirstName(String firstName);

    public UsersTable findByLastName(String lastName);

    public UsersTable findByEmail(String email);



}
