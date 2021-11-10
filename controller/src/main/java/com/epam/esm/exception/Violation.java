package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ivan Velichko
 * @date 09.11.2021 13:28
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Violation {

    private String fieldName;

    private String message;
}
