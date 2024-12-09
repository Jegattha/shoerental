package ch.zhaw.shoerental.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class SchuheStateChangeDTO {
    private String schuheId;
    private String mieterId;
    Date mietdauerVon;
    Date mietdauerBis;
}
