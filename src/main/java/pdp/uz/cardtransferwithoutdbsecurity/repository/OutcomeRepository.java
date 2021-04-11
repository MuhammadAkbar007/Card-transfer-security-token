package pdp.uz.cardtransferwithoutdbsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pdp.uz.cardtransferwithoutdbsecurity.entity.Outcome;

public interface OutcomeRepository extends JpaRepository<Outcome, Integer> {
}
