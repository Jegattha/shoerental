package ch.zhaw.shoerental.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;

import ch.zhaw.shoerental.model.Mieter;
import ch.zhaw.shoerental.model.MieterCreateDTO;
import ch.zhaw.shoerental.repository.MieterRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;






@RestController
@RequestMapping("/api") 
public class MieterController {

    @Autowired
    MieterRepository mieterRepository;




            @GetMapping("/mieter")
  public ResponseEntity<List<Mieter>> getAllMieter() {
 List<Mieter> allMieters = mieterRepository.findAll();
 return new ResponseEntity<>(allMieters, HttpStatus.OK);
}


            /*public ResponseEntity<Page<Mieter>> getAllMieter(
     @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
     @RequestParam(required = false, defaultValue = "2") Integer pageSize) {
 Page<Mieter> allFree = mieterRepository.findAll(PageRequest.of(pageNumber - 1, pageSize));
 return new ResponseEntity<>(allFree, HttpStatus.OK);
} 
}

*/

@GetMapping("/mieter/{id}")
public ResponseEntity<Mieter> getMieterById(@PathVariable String id) {
    Optional<Mieter> optMieter = mieterRepository.findById(id);
    if (optMieter.isPresent()) {
        return new ResponseEntity<>(optMieter.get(), HttpStatus.OK);
    } else { 
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}



 @PostMapping("/mieter")
  public ResponseEntity<Mieter> createMieter(
      @RequestBody MieterCreateDTO mDTO){
          Mieter mDAO = new Mieter(mDTO.getName(),mDTO.getEmail(), mDTO.getTelefonnummer(), mDTO.getAdresse(), mDTO.getPlz(), mDTO.getOrt());
          Mieter m = mieterRepository.save(mDAO);    
     return new ResponseEntity<>(m, HttpStatus.CREATED);
  }
}



