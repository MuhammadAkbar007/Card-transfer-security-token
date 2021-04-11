package pdp.uz.cardtransferwithoutdbsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pdp.uz.cardtransferwithoutdbsecurity.entity.Card;

public interface CardRepository extends JpaRepository<Card, Integer> {
}
