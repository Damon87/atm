package com.atm.data.dto.card_transaction;

import com.atm.data.objects.Range;
import lombok.Data;

@Data
public class SearchTransactionDTO {
    private String cardNumber;

    private Range<Long> dateRangeInMilliseconds;
}
