package io.celsoagra.finance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CoinbaseDTO {

    private String id;
    
    public static CoinbaseDTO newInstance(String id) {
        return new CoinbaseDTO(id);
    }
}
