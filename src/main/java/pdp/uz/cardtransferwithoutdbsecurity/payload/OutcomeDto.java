package pdp.uz.cardtransferwithoutdbsecurity.payload;

import lombok.Data;

@Data
public class OutcomeDto {

    private Integer fromCardId;

    private Integer toCardId;

    private double amount;

    private double commissionPercent;
}
