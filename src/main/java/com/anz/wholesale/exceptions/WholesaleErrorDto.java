package com.anz.wholesale.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class WholesaleErrorDto {
    private  String message;
    private  String details;
}
