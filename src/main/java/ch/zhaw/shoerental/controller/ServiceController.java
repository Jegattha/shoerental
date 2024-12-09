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
import ch.zhaw.shoerental.service.MailService;



@RestController
@RequestMapping("/api/service") 
public class ServiceController {

    @Autowired
    SchuheService schuheService;

    @PutMapping("/assignSchuhe")
    public ResponseEntity<Schuhe> assignSchuhe(@RequestBody SchuheStateChangeDTO changeS){
        String mieterId = changeS.getMieterId();
        String schuheId = changeS.getSchuheId();
        Optional<Schuhe> schuhe = schuheService.assignSchuhe(schuheId, mieterId);
        if(schuhe.isPresent()){
            Mail mail = new Mail();
            mail.setTo("tharsana18@outlook.de");
            mail.setSubject("Schuhe Assigned");
            mail.setMessage("Mieter have been assigned to the Schuhe with ID: " + schuheId);
            MailService mailService = new MailService();
            boolean isMailSent = mailService.sendMail(mail);

            if (isMailSent) {
                System.out.println("Email sent successfully!");
            } else {
                System.out.println("Failed to send the email. Check logs for details.");
            }

            return new ResponseEntity<>(schuhe.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/availableSchuhe")
    public ResponseEntity<Schuhe> availableSchuhe(@RequestBody AvailabeSchuheDTO changeA) {
        Optional<Schuhe> schuhe = schuheService.availableSchuhe(changeA.getSchuheId());
        if(schuhe.isPresent()){
            Schuhe s = schuhe.get();
            Mail mail = new Mail();
            mail.setTo("tharsana18@outlook.de");
            mail.setSubject("Schuhe zurückgegeben");
            mail.setMessage("Du hast die Schuhe-ID: " + s.getSchuheId() +" erfolgreich zurückgesendet.");
            MailService mailService = new MailService();
            boolean isMailSent = mailService.sendMail(mail);

            if (isMailSent) {
                System.out.println("Email sent successfully!");
            } else {
                System.out.println("Failed to send the email. Check logs for details.");
            }

            return new ResponseEntity<>(schuhe.get(), HttpStatus.OK);
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
                sendMieterEmail(schuheId, mietdauerBis);
                sendVermieterEmail(schuheId, mieterId, mietdauerVon, mietdauerBis);
                return new ResponseEntity<>(schuhe.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        private void sendMieterEmail(String schuheId, Date mietdauerBis) {
            Mail mieterMail = new Mail();
            mieterMail.setTo("tharsana18@outlook.de");
            mieterMail.setSubject("Schuhe gemietet");
            mieterMail.setMessage("Du hast die Schuhe-ID: " + schuheId + " bis zum " + mietdauerBis +
                    " gemietet. Falls du Fragen hast, kannst du uns jederzeit kontaktieren");
            MailService mailService = new MailService();
            boolean isMailSent = mailService.sendMail(mieterMail);
        
            if (isMailSent) {
                System.out.println("Email to Mieter sent successfully!");
            } else {
                System.out.println("Failed to send the email to Mieter. Check logs for details.");
            }
        }
        
        private void sendVermieterEmail(String schuheId, String mieterId, Date mietdauerVon, Date mietdauerBis) {
            Mail vermieterMail = new Mail();
            vermieterMail.setTo("tharsana.jegatheeswaran@gmail.com");
            vermieterMail.setSubject("Schuhe vermietet");
            vermieterMail.setMessage("Die Schuhe-ID: " + schuheId + " wurde an Mieter-ID: " + mieterId +
                    " vermietet. Mietdauer von " + mietdauerVon + " bis " + mietdauerBis);
            MailService mailService = new MailService();
            boolean isMailSent = mailService.sendMail(vermieterMail);
        
            if (isMailSent) {
                System.out.println("Email to Vermieter sent successfully!");
            } else {
                System.out.println("Failed to send the email to Vermieter. Check logs for details.");
            }
        }
}
