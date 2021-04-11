package pdp.uz.cardtransferwithoutdbsecurity.payload;

import lombok.Data;

import java.util.Date;

@Data
public class CardDto {

    private String cardNumber;

    private Date expiredDate;
}
