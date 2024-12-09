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

    public Vermieter getVermieterById(String vermieterId) {
        Optional<Vermieter> vermieterOpt = vermieterRepository.findById(vermieterId);
        return vermieterOpt.orElse(null);
    }
}
