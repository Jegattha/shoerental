package ch.zhaw.shoerental.controller;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestParam;

import ch.zhaw.shoerental.model.Mieter;
import ch.zhaw.shoerental.model.MieterCreateDTO;
import ch.zhaw.shoerental.repository.MieterRepository;
import ch.zhaw.shoerental.service.MailValidatorService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;





@RestController
@RequestMapping("/api") 
public class MieterController {

    @Autowired
    MieterRepository mieterRepository;

    @Autowired
    MailValidatorService mailValidatorService;



            @GetMapping("/mieter")
public ResponseEntity<Page<Mieter>> getAllMieter(
     @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
     @RequestParam(required = false, defaultValue = "2") Integer pageSize) {
 Page<Mieter> allFree = mieterRepository.findAll(PageRequest.of(pageNumber - 1, pageSize));
 return new ResponseEntity<>(allFree, HttpStatus.OK);
} 


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
          if(mailValidatorService.validateEmail(m.getEmail()).isDns() && mailValidatorService.validateEmail(m.getEmail()).isFormat() && !mailValidatorService.validateEmail(m.getEmail()).isDisposable()){
            return new ResponseEntity<>(m, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

@DeleteMapping("/mieter/delete/{mieterId}")
public ResponseEntity<Void> deleteMieter(@PathVariable String mieterId) {
    if (mieterRepository.existsById(mieterId)) {
        mieterRepository.deleteById(mieterId);
        return new ResponseEntity<>(HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

 @PutMapping("/mieter/{mieterId}")
public ResponseEntity<Mieter> updateMieter(@PathVariable String mieterId, @RequestBody MieterCreateDTO mDTO) {
    Optional<Mieter> optMieter = mieterRepository.findById(mieterId);
    if (optMieter.isPresent()) {
        Mieter existingMieter = optMieter.get();
        // Aktualisiere die Mieterinformationen basierend auf mDTO
        existingMieter.setName(mDTO.getName());
        existingMieter.setEmail(mDTO.getEmail());
        mieterRepository.save(existingMieter);
        return new ResponseEntity<>(existingMieter, HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

@GetMapping("/me/mieter")
public ResponseEntity<Mieter> assignToMe(@AuthenticationPrincipal Jwt jwt) {
    String userEmail = jwt.getClaimAsString("email");
    Mieter mieter = mieterRepository.findFirstByEmail(userEmail);
    if (mieter != null) {
        return new ResponseEntity<>(mieter, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
}

    
}




