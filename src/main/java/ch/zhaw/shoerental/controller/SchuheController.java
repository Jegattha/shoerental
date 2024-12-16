package ch.zhaw.shoerental.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.shoerental.model.Schuhe;
import ch.zhaw.shoerental.model.SchuheCreateDTO;
import ch.zhaw.shoerental.model.SchuheStateAggregation;
import ch.zhaw.shoerental.model.MieterSchuheAggregationDTO;
import ch.zhaw.shoerental.model.Vermieter;
import ch.zhaw.shoerental.repository.SchuheRepository;
import ch.zhaw.shoerental.service.VermieterService;
import ch.zhaw.shoerental.service.RoleService; 

@RestController
@RequestMapping("/api")
public class SchuheController {

    @Autowired
    SchuheRepository schuheRepository;

    @Autowired
    private VermieterService vermieterService;

    @Autowired
    RoleService roleService;


    @PostMapping("/schuhe")
   
     public ResponseEntity<Schuhe> createSchuhe(

             @RequestBody SchuheCreateDTO sDTO, @RequestParam String vermieterId) {

         Vermieter vermieter = vermieterService.getVermieterById(vermieterId);
     
         if (vermieter == null) {
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }
     
         Schuhe sDAO = new Schuhe(sDTO.getMarke(), sDTO.getPreis(), sDTO.getSchuheType(), sDTO.getGroesse(), sDTO.getSchuheFarbe(), sDTO.getSchuheBeschreibung(), sDTO.getVermieterId());
         sDAO.setVermieterId(vermieterId);
         Schuhe s = schuheRepository.save(sDAO);
         return new ResponseEntity<>(s, HttpStatus.CREATED);
     }
     

    
        @GetMapping("/schuhe")
        public ResponseEntity<List<Schuhe>> getAllSchuhe(@RequestParam(required = false) String marke) {
            List<Schuhe> allSchuhe;
            if (marke != null) {
                allSchuhe = schuheRepository.findByMarke(marke);
            } else {
                allSchuhe = schuheRepository.findAll();
            }
        
            return new ResponseEntity<>(allSchuhe, HttpStatus.OK);
        }
        @GetMapping("/schuhe/aggregation/state")
        public ResponseEntity<List<SchuheStateAggregation>> getSchuheStateAggregation(){
            return new ResponseEntity<>(schuheRepository.getSchuheStateAggregations(), HttpStatus.OK);
        }

        @GetMapping("/schuhe/{schuheId}")
            public ResponseEntity<Schuhe> getSchuheById(@PathVariable String schuheId){
                Optional<Schuhe> schuheOpt = schuheRepository.findById(schuheId);
                if(schuheOpt.isPresent()){
                    return new ResponseEntity<>(schuheOpt.get(), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }

            @DeleteMapping("/schuhe/delete/{schuheId}")
            public ResponseEntity<Void> deleteKleidungbyId(@PathVariable String schuheId) {
                Optional<Schuhe> schuheOpt = schuheRepository.findById(schuheId);
            
                if (schuheOpt.isPresent()) {
                    schuheRepository.deleteById(schuheId);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
            
            @PutMapping("/schuhe/update/{schuheId}")
            public ResponseEntity<Schuhe> updateSchuhe(
                    @PathVariable String schuheId,
                    @RequestBody SchuheCreateDTO sDTO) {
                Optional<Schuhe> existingSchuheOpt = schuheRepository.findById(schuheId);
        
                if (existingSchuheOpt.isPresent()) {
                    Schuhe existingSchuhe = existingSchuheOpt.get();
                    
                    // Aktualisiere die Werte der vorhandenen Kleidung mit den neuen Werten aus kDTO
                    existingSchuhe.setMarke(sDTO.getMarke());
                    existingSchuhe.setPreis(sDTO.getPreis());
                    existingSchuhe.setSchuheType(sDTO.getSchuheType());
                    existingSchuhe.setGroesse(sDTO.getGroesse());
                    existingSchuhe.setSchuheFarbe(sDTO.getSchuheFarbe());
                    existingSchuhe.setSchuheBeschreibung(sDTO.getSchuheBeschreibung());
                    
        
                    // Speichere die aktualisierte Kleidung in der Datenbank
                    Schuhe updatedSchuhe = schuheRepository.save(existingSchuhe);
        
                    return new ResponseEntity<>(updatedSchuhe, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }

            @GetMapping("/schuhe/byMieter")
            public ResponseEntity<List<MieterSchuheAggregationDTO>> getSchuheMieterStateAggregation(){
                return new ResponseEntity<>(schuheRepository.getMieterSchuheAggregation(), HttpStatus.OK);
            }
        

            @DeleteMapping("/schuheTest")
           
            public ResponseEntity<String> deleteAllSchuhen() {
            schuheRepository.deleteAll();
            return ResponseEntity.status(HttpStatus.OK).body("DELETED");
            }

        }

