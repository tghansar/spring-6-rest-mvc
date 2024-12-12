package com.example.spring6restmvc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2024/10/10, Thu, 09:09
 **/

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Value Not Found")
public class NotFoundException extends RuntimeException {
    public NotFoundException() {
    }
}
