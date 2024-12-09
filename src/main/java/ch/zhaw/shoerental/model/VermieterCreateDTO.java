package ch.zhaw.shoerental.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Getter
public class VermieterCreateDTO {
       

    private String name;
    private String email;
    private String telefonnummer;
    private String adresse;
    private String plz;
    private String ort;
}
