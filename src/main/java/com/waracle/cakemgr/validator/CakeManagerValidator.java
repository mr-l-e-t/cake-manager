package com.waracle.cakemgr.validator;

import com.google.common.base.Strings;
import com.waracle.cakemgr.dto.CakeDTO;
import com.waracle.cakemgr.exception.CakeManagerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CakeManagerValidator {

    public Optional<Void> validateCake(CakeDTO cakeDTO, CakeAction cakeAction) throws CakeManagerException {
        StringBuilder errorMessage = new StringBuilder();
        if (cakeAction == CakeAction.CREATE && cakeDTO.getId() != null ) {
                log.error("No Id should be present when creating a cake object. ");
                errorMessage.append("No Id should be present when creating a cake object. ");
        } else if (cakeAction == CakeAction.UPDATE && cakeDTO.getId() == null) {
            log.error("Id is required. ");
            errorMessage.append("Id is required. ");
        }

        if (Strings.isNullOrEmpty(cakeDTO.getTitle())) {
            log.error("Title is required. ");
            errorMessage.append("Title is required. ");
        }
        if (Strings.isNullOrEmpty(cakeDTO.getDescription())) {
            log.error("Description is required. ");
            errorMessage.append("Description is required. ");
        }
        if (Strings.isNullOrEmpty(cakeDTO.getImageURL())) {
            log.error("ImageUrl is required. ");
            errorMessage.append("ImageUrl is required. ");
        }

        if (!errorMessage.isEmpty()) {
            throw new CakeManagerException(errorMessage.toString());
        }
        return Optional.empty();
    }
}
