package ch.zhaw.shoerental.controller;


import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.shoerental.model.AvailabeSchuheDTO;
import ch.zhaw.shoerental.model.Schuhe;
import ch.zhaw.shoerental.model.SchuheStateChangeDTO;
import ch.zhaw.shoerental.model.Mail;
import ch.zhaw.shoerental.service.SchuheService;
import ch.zhaw.shoerental.service.VermieterService;
import ch.zhaw.shoerental.service.MailService;

import ch.zhaw.shoerental.service.MieterService;


@RestController
@RequestMapping("/api/service") 
public class ServiceController {

    @Autowired
    SchuheService schuheService;

    @Autowired
    MieterService mieterService;

    @Autowired
    VermieterService vermieterService;

    @Autowired
    MailService mailService;

    @PutMapping("/assignSchuhe")
    public ResponseEntity<Schuhe> assignSchuhe(@RequestBody SchuheStateChangeDTO changeS) {
        String mieterId = changeS.getMieterId();
        String schuheId = changeS.getSchuheId();
        Optional<Schuhe> schuhe = schuheService.assignSchuhe(schuheId, mieterId);
        
        if (schuhe.isPresent()) {
            Schuhe assignedSchuhe = schuhe.get();
    
            // Dynamische E-Mail-Adressen abrufen
            String mieterEmail = mieterService.getEmail(); // Mieter-E-Mail
            Optional<String> optionalVermieterEmail = vermieterService.getEmailById(assignedSchuhe.getVermieterId());
    
            // E-Mail an den Mieter senden
            if (mieterEmail != null && !mieterEmail.isEmpty()) {
                sendMieterEmail(mieterEmail, schuheId);
            }
    
            // E-Mail an den Vermieter senden
            sendVermieterEmail(optionalVermieterEmail, schuheId, mieterId, assignedSchuhe.getMietdauerVon(), assignedSchuhe.getMietdauerBis());
    
            return new ResponseEntity<>(assignedSchuhe, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
    @PostMapping("/availableSchuhe")
    public ResponseEntity<Schuhe> availableSchuhe(@RequestBody AvailabeSchuheDTO changeA) {
        Optional<Schuhe> schuhe = schuheService.availableSchuhe(changeA.getSchuheId());
        if (schuhe.isPresent()) {
            Schuhe s = schuhe.get();
            String vermieterEmail = vermieterService.getEmailById(s.getVermieterId()).orElse(null);
    
            if (vermieterEmail != null && !vermieterEmail.isEmpty()) {
                Mail mail = new Mail();
                mail.setTo(vermieterEmail);
                mail.setSubject("Schuhe zurückgegeben");
                mail.setMessage("Die Schuhe mit der ID " + s.getSchuheId() + " wurden erfolgreich zurückgegeben.");
                MailService mailService = new MailService();
                boolean isMailSent = mailService.sendMail(mail);
    
                if (isMailSent) {
                    System.out.println("Email sent successfully!");
                } else {
                    System.out.println("Failed to send the email. Check logs for details.");
                }
            }
    
            return new ResponseEntity<>(s, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
    @PostMapping("/mietSchuhe")
    public ResponseEntity<Schuhe> mietSchuhe(@RequestBody SchuheStateChangeDTO changeS) {
        String mieterId = changeS.getMieterId();
        String schuheId = changeS.getSchuheId();
        Date mietdauerVon = changeS.getMietdauerVon();
        Date mietdauerBis = changeS.getMietdauerBis();
    
        Optional<Schuhe> schuhe = schuheService.mietSchuhe(schuheId, mieterId, mietdauerVon, mietdauerBis);
    
        if (schuhe.isPresent()) {
            Schuhe rentedSchuhe = schuhe.get();
    
            // Dynamische E-Mail-Adressen abrufen
            String mieterEmail = mieterService.getEmail();
            Optional<String> optionalVermieterEmail = vermieterService.getEmailById(rentedSchuhe.getVermieterId());
    
            // E-Mails senden
            if (mieterEmail != null && !mieterEmail.isEmpty()) {
                sendMieterEmail(mieterEmail, schuheId);
            }
            sendVermieterEmail(optionalVermieterEmail, schuheId, mieterId, mietdauerVon, mietdauerBis);
    
            return new ResponseEntity<>(rentedSchuhe, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
    private void sendMieterEmail(String mieterEmail, String schuheId) {    
        Mail mieterMail = new Mail();
        mieterMail.setTo(mieterEmail);
        mieterMail.setSubject("Schuhe gemietet");
        mieterMail.setMessage("Du hast die Schuhe mit der ID " + schuheId + " erfolgreich gemietet.");
        boolean isMailSent = mailService.sendMail(mieterMail);
    
        if (isMailSent) {
            System.out.println("Email to Mieter sent successfully!");
        } else {
            System.out.println("Failed to send the email to Mieter. Check logs for details.");
        }
    }
    
    private void sendVermieterEmail(Optional<String> optionalVermieterEmail, String schuheId, String mieterId, Date mietdauerVon, Date mietdauerBis) {
        if (optionalVermieterEmail.isPresent()) {
            String vermieterEmail = optionalVermieterEmail.get();
            Mail vermieterMail = new Mail();
            vermieterMail.setTo(vermieterEmail);
            vermieterMail.setSubject("Schuhe vermietet");
            vermieterMail.setMessage("Die Schuhe mit der ID " + schuheId + " wurden an den Mieter mit der ID " + mieterId + 
                " vermietet. Mietdauer: Von " + mietdauerVon + " bis " + mietdauerBis + ".");
            boolean isMailSent = mailService.sendMail(vermieterMail);
    
            if (isMailSent) {
                System.out.println("Email to Vermieter sent successfully!");
            } else {
                System.out.println("Failed to send the email to Vermieter. Check logs for details.");
            }
        
        }
    }
    @PostMapping("/verifizierung/sendMail")
    public ResponseEntity<String> sendVerificationMail(@RequestBody Mail mail) {
        boolean success = mailService.sendMail(mail);
        if (success) {
            return ResponseEntity.ok("E-Mail erfolgreich gesendet!");
        } else {
            return ResponseEntity.status(500).body("Fehler beim Senden der E-Mail.");
        }
    } 



@PostMapping("/about/sendMail")
public ResponseEntity<String> sendAboutMail(@RequestBody Mail mail) {
    boolean success = mailService.sendMail(mail);
    if (success) {
        return ResponseEntity.ok("E-Mail erfolgreich gesendet!");
    } else {
        return ResponseEntity.status(500).body("Fehler beim Senden der E-Mail.");
    }
}
}

  