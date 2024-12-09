package ch.zhaw.shoerental.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;

import ch.zhaw.shoerental.model.Vermieter;
import ch.zhaw.shoerental.model.VermieterCreateDTO;
import ch.zhaw.shoerental.repository.VermieterRepository;
import ch.zhaw.shoerental.service.MailValidatorService;






@RestController
@RequestMapping("/api") 
public class VermieterController {

    @Autowired
    VermieterRepository vermieterRepository;

@Autowired
 MailValidatorService mailValidatorService;


            @GetMapping("/vermieter")
  public ResponseEntity<List<Vermieter>> getAllVermieter() {
 List<Vermieter> allVermieters = vermieterRepository.findAll();
 return new ResponseEntity<>(allVermieters, HttpStatus.OK);
}


    @GetMapping("/{vermieterId}")
    public ResponseEntity<Vermieter> getVermieterById(@PathVariable String vermieterId) {
        Optional<Vermieter> optVermieter = vermieterRepository.findById(vermieterId);
        if (optVermieter.isPresent()) {
            Vermieter vermieter = optVermieter.get();
            return new ResponseEntity<>(vermieter, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


        @PostMapping("/vermieter")
    public ResponseEntity<Vermieter> createVermieter(
        @RequestBody VermieterCreateDTO vDTO){
            Vermieter vDAO = new Vermieter(vDTO.getName(),vDTO.getEmail(), vDTO.getTelefonnummer(), vDTO.getAdresse(), vDTO.getPlz(), vDTO.getOrt());
            Vermieter v = vermieterRepository.save(vDAO);
            return new ResponseEntity<>(v, HttpStatus.CREATED);
        }

}

