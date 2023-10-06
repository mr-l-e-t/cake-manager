package com.waracle.cakemgr.validator;

import com.waracle.cakemgr.TestUtils;
import com.waracle.cakemgr.dto.CakeDTO;
import com.waracle.cakemgr.exception.CakeManagerException;
import com.waracle.cakemgr.service.CakeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CakeManagerValidatorTest {

    @Autowired
    @InjectMocks //the annotation will initialize the CakeManagerValidator object with the CakeRepository mock
    private CakeManagerValidator cakeManagerValidator;

    @Test
    public void givenFullCakeDTOThenReturnEmptyOptional() {
        CakeDTO fullCake = TestUtils.CAKE_DTO;
        Optional<Void> isValid = cakeManagerValidator.validateCakeCreation(fullCake);
        assertNotNull(isValid);
    }

    @Test
    public void givenCakeWithNoTitleThenThrowCakeManagerException(){
        CakeDTO cakeWithNoTitle = CakeDTO.builder().description("A cheesecake made of lemon").imageURL("https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg").build();

        CakeManagerException exception = assertThrows(CakeManagerException.class,() -> cakeManagerValidator.validateCakeCreation(cakeWithNoTitle));
        assertThat(exception.getMessage()).isEqualTo("Title is required. ");
    }

    @Test
    public void givenCakeWithNoDescriptionThenThrowCakeManagerException(){
        CakeDTO cakeWithNoDescription = CakeDTO.builder().id(1).title("Lemon cheesecake").imageURL("https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg").build();

        CakeManagerException exception = assertThrows(CakeManagerException.class,() -> cakeManagerValidator.validateCakeCreation(cakeWithNoDescription));
        assertThat(exception.getMessage()).isEqualTo("Description is required. ");
    }

    @Test
    public void givenCakeWithNoImageURLThenThrowCakeManagerException(){
        CakeDTO cakeWithNoDescription = CakeDTO.builder().id(1).title("Lemon cheesecake").description("A cheesecake made of lemon").build();

        CakeManagerException exception = assertThrows(CakeManagerException.class,() -> cakeManagerValidator.validateCakeCreation(cakeWithNoDescription));
        assertThat(exception.getMessage()).isEqualTo("ImageUrl is required. ");
    }
}
