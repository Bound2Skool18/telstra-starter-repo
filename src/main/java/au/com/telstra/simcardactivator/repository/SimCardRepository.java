package au.com.telstra.simcardactivator.repository;

import au.com.telstra.simcardactivator.model.SimCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimCardRepository extends JpaRepository<SimCard, Long> {

}