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
    public void givenValidateCakeCreationWithFullCakeDTOThenReturnEmptyOptional() {
        CakeDTO fullCake = TestUtils.CAKE_DTO_WITH_NO_ID;
        Optional<Void> isValid = cakeManagerValidator.validateCake(fullCake, CakeAction.CREATE);
        assertNotNull(isValid);
    }

    @Test
    public void givenValidateCakeCreationWithCakeIDThenThrowCakeManagerException(){
        CakeManagerException exception = assertThrows(CakeManagerException.class,() -> cakeManagerValidator.validateCake(TestUtils.CAKE_DTO, CakeAction.CREATE));
        assertThat(exception.getMessage()).isEqualTo("No Id should be present when creating a cake object. ");
    }

    @Test
    public void givenValidateCakeCreationWithCakeWithNoTitleThenThrowCakeManagerException(){
        CakeDTO cakeWithNoTitle = TestUtils.CAKE_DTO_WITH_NO_TITLE;

        CakeManagerException exception = assertThrows(CakeManagerException.class,() -> cakeManagerValidator.validateCake(cakeWithNoTitle, CakeAction.CREATE));
        assertThat(exception.getMessage()).isEqualTo("Title is required. ");
    }

    @Test
    public void givenValidateCakeCreationWithCakeWithNoDescriptionThenThrowCakeManagerException(){
        CakeDTO cakeWithNoDescription = CakeDTO.builder().title("Lemon cheesecake").imageURL("https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg").build();

        CakeManagerException exception = assertThrows(CakeManagerException.class,() -> cakeManagerValidator.validateCake(cakeWithNoDescription, CakeAction.CREATE));
        assertThat(exception.getMessage()).isEqualTo("Description is required. ");
    }

    @Test
    public void givenValidateCakeCreationWithCakeWithNoImageURLThenThrowCakeManagerException(){
        CakeDTO cakeWithNoDescription = CakeDTO.builder().title("Lemon cheesecake").description("A cheesecake made of lemon").build();

        CakeManagerException exception = assertThrows(CakeManagerException.class,() -> cakeManagerValidator.validateCake(cakeWithNoDescription, CakeAction.CREATE));
        assertThat(exception.getMessage()).isEqualTo("ImageUrl is required. ");
    }

    @Test
    public void givenUpdateCakeWithNoIdThenThrowCakeManagerException(){
        CakeManagerException exception = assertThrows(CakeManagerException.class,() -> cakeManagerValidator.validateCake(TestUtils.CAKE_DTO_WITH_NO_ID, CakeAction.UPDATE));
        assertThat(exception.getMessage()).isEqualTo("Id is required. ");
    }

}
