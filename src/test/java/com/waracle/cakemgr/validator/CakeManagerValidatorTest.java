package com.waracle.cakemgr.validator;

import com.waracle.cakemgr.TestUtils;
import com.waracle.cakemgr.dto.CakeDTO;
import com.waracle.cakemgr.exception.CakeManagerException;
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
    public void givenValidateCakeCreationWithFullCakeDTOThenWorkAsExpected() {
        CakeDTO fullCake = TestUtils.CAKE_DTO_WITH_NO_ID;
        cakeManagerValidator.validate(fullCake, CakeAction.CREATE);
    }

    @Test
    public void givenValidateCakeCreationWithCakeIDThenThrowCakeManagerException(){
        CakeManagerException exception = assertThrows(CakeManagerException.class,() -> cakeManagerValidator.validate(TestUtils.CAKE_DTO, CakeAction.CREATE));
        assertThat(exception.getMessage()).isEqualTo("No Id should be present when creating a cake object. ");
    }

    @Test
    public void givenValidateCakeCreationWithCakeWithNoTitleThenThrowCakeManagerException(){
        CakeDTO cakeWithNoTitle = TestUtils.CAKE_DTO_WITH_NO_ID_NO_TITLE;

        CakeManagerException exception = assertThrows(CakeManagerException.class,() -> cakeManagerValidator.validate(cakeWithNoTitle, CakeAction.CREATE));
        assertThat(exception.getMessage()).isEqualTo("Title is required. ");
    }

    @Test
    public void givenValidateCakeCreationWithCakeWithNoDescriptionThenThrowCakeManagerException(){
        CakeDTO cakeWithNoDescription = TestUtils.CAKE_DTO_WITH_NO_ID_NO_DESCRIPTION;

        CakeManagerException exception = assertThrows(CakeManagerException.class,() -> cakeManagerValidator.validate(cakeWithNoDescription, CakeAction.CREATE));
        assertThat(exception.getMessage()).isEqualTo("Description is required. ");
    }

    @Test
    public void givenValidateCakeCreationWithCakeWithNoImageURLThenThrowCakeManagerException(){
        CakeDTO cakeWithNoUrl = TestUtils.CAKE_DTO_WITH_NO_ID_NO_URL;

        CakeManagerException exception = assertThrows(CakeManagerException.class,() -> cakeManagerValidator.validate(cakeWithNoUrl, CakeAction.CREATE));
        assertThat(exception.getMessage()).isEqualTo("ImageUrl is required. ");
    }

    @Test
    public void givenUpdateCakeWithNoIdThenThrowCakeManagerException(){
        CakeManagerException exception = assertThrows(CakeManagerException.class,() -> cakeManagerValidator.validate(TestUtils.CAKE_DTO_WITH_NO_ID, CakeAction.UPDATE));
        assertThat(exception.getMessage()).isEqualTo("Id is required. ");
    }
}
