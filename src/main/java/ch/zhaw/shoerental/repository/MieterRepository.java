package ch.zhaw.shoerental.repository;
import ch.zhaw.shoerental.model.Mieter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import org.springframework.data.mongodb.repository.MongoRepository;





public interface MieterRepository extends MongoRepository<Mieter,String> {
//    Optional<Mieter> findById(String mieterId);
//    List<Mieter> findByEmail(String email);
    Mieter findFirstByEmail (String email);
    Page<Mieter> findAll(Pageable pageable);
    
}
