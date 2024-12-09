package ch.zhaw.shoerental.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document; 


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Document("mieter")
public class Mieter {
    @Id
    private String mieterId;
    @NonNull
    private String name;
    @NonNull
    private String email;
     
    private String telefonnummer;
    
 
    private String adresse;

    private String plz;
 
    private String ort;

    public Mieter(@NonNull String name, 
    @NonNull String email, 
    String telefonnummer,
    String adresse, 
    String plz, 
    String ort) {
this.name = name;
this.email = email;
this.telefonnummer = telefonnummer;
this.adresse = adresse;
this.plz = plz;
this.ort = ort;
}
}
