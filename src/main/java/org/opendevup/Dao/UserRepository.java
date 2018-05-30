package org.opendevup.Dao;

import org.opendevup.entitee.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer> {

}
