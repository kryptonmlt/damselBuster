package org.kryptonmlt.damselbuster.repositories;

import org.kryptonmlt.damselbuster.pojos.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

    Game findByNameAndDescription(String name, String description);
}
