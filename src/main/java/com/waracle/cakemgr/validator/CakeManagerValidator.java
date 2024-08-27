package com.waracle.cakemgr.validator;

import com.google.common.base.Strings;
import com.waracle.cakemgr.dto.CakeDTO;
import com.waracle.cakemgr.exception.CakeManagerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Optional;

/**
 * I have decided not to implement <a href="https://docs.spring.io/spring-framework/reference/core/validation/validator.html">Spring Validator</a>
 * since creating a custom validator gives me more freedom to customise the error message and the format of the error object returned.
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class CakeManagerValidator {
    public void validate(CakeDTO cakeDTO, CakeAction cakeAction) throws CakeManagerException {
        StringBuilder errorMessage = new StringBuilder();
        if (cakeAction == CakeAction.CREATE && cakeDTO.id() != null ) {
            errorMessage.append("No Id should be present when creating a cake object. ");
        }
        if (cakeAction == CakeAction.UPDATE && cakeDTO.id() == null ) {
            errorMessage.append("Id is required. ");
        }
        if (Strings.isNullOrEmpty(cakeDTO.title())) {
            errorMessage.append("Title is required. ");
        }
        if (Strings.isNullOrEmpty(cakeDTO.description())) {
            errorMessage.append("Description is required. ");
        }
        if (Strings.isNullOrEmpty(cakeDTO.imageURL())) {
            errorMessage.append("ImageUrl is required. ");
        }

        if (!errorMessage.isEmpty()) {
            throw new CakeManagerException(errorMessage.toString());
        }
    }
}
