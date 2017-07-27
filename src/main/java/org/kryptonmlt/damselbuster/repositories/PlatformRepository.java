package org.kryptonmlt.damselbuster.repositories;

import org.kryptonmlt.damselbuster.pojos.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformRepository extends JpaRepository<Platform, Long> {
    
	Platform findByName(String name);
}
