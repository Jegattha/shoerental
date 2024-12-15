package ch.zhaw.shoerental.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.zhaw.shoerental.model.Vermieter;
import ch.zhaw.shoerental.repository.VermieterRepository;

import java.util.Optional;

@Service
public class VermieterService {

    @Autowired
    private VermieterRepository vermieterRepository;

    public Optional<String> getEmailById(String vermieterId) {
        Optional<Vermieter> vermieter = vermieterRepository.findById(vermieterId);
        return vermieter.map(Vermieter::getEmail);
    }
    public Vermieter getVermieterById(String vermieterId) {
        return vermieterRepository.findById(vermieterId).orElse(null);
    }

}