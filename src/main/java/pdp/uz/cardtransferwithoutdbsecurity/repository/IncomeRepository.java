package pdp.uz.cardtransferwithoutdbsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pdp.uz.cardtransferwithoutdbsecurity.entity.Income;

public interface IncomeRepository extends JpaRepository<Income, Integer> {
}
