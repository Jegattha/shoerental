package ch.zhaw.shoerental.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor // Fügt einen Konstruktor mit allen Feldern hinzu
@Getter
@Setter
public class MieterCreateDTO {

    private String name;
    private String email;
    private String telefonnummer;
    private String adresse;
    private String plz;
    private String ort;
}





/*package ch.zhaw.shoerental.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter

public class MieterCreateDTO {
    
 
    private String name;
    private String email;
    
    private String telefonnummer;
    
 
    private String adresse;

    private String plz;
 
    private String ort;
}

*/