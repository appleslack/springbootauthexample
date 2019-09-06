
package com.redtailsoft.springbootauthupdated.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ApplicationUserRepository
 */
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

    ApplicationUser findByUsername( String username );
    
}
