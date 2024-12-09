package ch.zhaw.shoerental.service;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.zhaw.shoerental.repository.SchuheRepository;
import ch.zhaw.shoerental.repository.MieterRepository;
import ch.zhaw.shoerental.model.Schuhe;
import ch.zhaw.shoerental.model.SchuheState;
import ch.zhaw.shoerental.model.Mieter;

@Service
public class SchuheService {

    @Autowired
    SchuheRepository schuheRepository;

    @Autowired
    MieterRepository mieterRepository;

    public Optional<Schuhe> assignSchuhe(String schuheId, String mieterId){
        Optional<Schuhe> schuheToAssign = schuheRepository.findById(schuheId);
        if(schuheToAssign.isPresent()){
            Schuhe schuhe = schuheToAssign.get();
            if(schuhe.getSchuheState()== SchuheState.VERFUEGBAR){
              Optional <Mieter> m = mieterRepository.findById(mieterId);
                if(m.isPresent()){
                    schuhe.setSchuheState(SchuheState.VERMIETET);
                    schuhe.setMieterId(m.get().getMieterId());
                    schuheRepository.save(schuhe);
                    return Optional.of(schuhe);
                }
           
            }
        }
        return Optional.empty();
    }

    public Optional<Schuhe> availableSchuhe(String schuheId) {
        Optional<Schuhe> schuheToAssign = schuheRepository.findById(schuheId);
        if (schuheToAssign.isPresent()) {
            Schuhe schuhe = schuheToAssign.get();
            if (schuhe.getSchuheState() == SchuheState.VERMIETET) {
                schuhe.setSchuheState(SchuheState.VERFUEGBAR);
                schuhe.setMieterId(null); // Zurücksetzen des Mieter-IDs auf null, da die Kleidung nun verfügbar ist
                schuhe.setMietdauerVon(null);
                schuhe.setMietdauerBis(null);
                schuhe.setTotalPreis(null);
                schuheRepository.save(schuhe);
                return Optional.of(schuhe);
            }
        }
        return Optional.empty();
    }
    
public Optional<Schuhe> mietSchuhe(String schuheId, String mieterId, Date mietdauerVon, Date mietdauerBis) {
    Optional<Schuhe> schuheToRent = schuheRepository.findById(schuheId);
    if (schuheToRent.isPresent()) {
        Schuhe schuhe = schuheToRent.get();
        if (schuhe.getSchuheState() == SchuheState.VERFUEGBAR) {
            Optional<Mieter> m = mieterRepository.findById(mieterId);
            if (m.isPresent()) {
                schuhe.setSchuheState(SchuheState.VERMIETET);
                schuhe.setMieterId(m.get().getMieterId());
                schuhe.setMietdauerVon(mietdauerVon);
                schuhe.setMietdauerBis(mietdauerBis);
               

                // Calculate the rental duration in days
                long rentalDays = ChronoUnit.DAYS.between(mietdauerVon.toInstant(), mietdauerBis.toInstant());

                // Calculate and set the total price based on rental duration
                double totalPreis = rentalDays * schuhe.getPreis();
                schuhe.setTotalPreis(totalPreis);
                schuheRepository.save(schuhe);
                return Optional.of(schuhe);
            }
        }
    }
    return Optional.empty();
}


}
