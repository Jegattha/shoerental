package ch.zhaw.shoerental.repository;
import ch.zhaw.shoerental.model.Vermieter;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface VermieterRepository extends MongoRepository<Vermieter, String> {
    Page<Vermieter> findAll(Pageable pageable);
    Vermieter findFirstByEmail (String email);
    

}