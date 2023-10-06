package com.waracle.cakemgr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.dto.CakeDTO;
import com.waracle.cakemgr.dto.ErrorDTO;
import com.waracle.cakemgr.entity.CakeEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

public class TestUtils {

    public static final CakeEntity CAKE_ENTITY = CakeEntity.builder().id(1).title("Lemon cheesecake").description("A cheesecake made of lemon").imageURL("https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg").build();
    public static final CakeEntity CAKE_ENTITY_WITH_NO_ID = CakeEntity.builder().title("Lemon cheesecake").description("A cheesecake made of lemon").imageURL("https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg").build();
    public static final CakeDTO CAKE_DTO = CakeDTO.builder().id(1).title("Lemon cheesecake").description("A cheesecake made of lemon").imageURL("https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg").build();
    public static final CakeDTO CAKE_DTO_WITH_NO_ID = CakeDTO.builder().title("Lemon cheesecake").description("A cheesecake made of lemon").imageURL("https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg").build();

    public static final CakeDTO CAKE_DTO_WITH_NO_TITLE = CakeDTO.builder().description("A cheesecake made of lemon").imageURL("https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg").build();
    public static final List<CakeDTO> TWO_CAKE_LIST_WITH_ID = List.of(
            CakeDTO.builder().id(1).title("Lemon cheesecake").description("A cheesecake made of lemon").imageURL("https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg").build(),
            CakeDTO.builder().id(2).title("victoria sponge").description("sponge with jam").imageURL("http://www.bbcgoodfood.com/sites/bbcgoodfood.com/files/recipe_images/recipe-image-legacy-id--1001468_10.jpg").build()
    );

    public static final List<CakeEntity> TWO_CAKE_ENTITY_LIST_WITH_ID = List.of(
            CakeEntity.builder().id(1).title("Lemon cheesecake").description("A cheesecake made of lemon").imageURL("https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg").build(),
            CakeEntity.builder().id(2).title("victoria sponge").description("sponge with jam").imageURL("http://www.bbcgoodfood.com/sites/bbcgoodfood.com/files/recipe_images/recipe-image-legacy-id--1001468_10.jpg").build()
    );

    public static String getTwoCakeListAsJson() throws JsonProcessingException {
        return TestUtils.asJsonString(TWO_CAKE_LIST_WITH_ID);
    }
    public static String getSingleCakeAsJson() throws JsonProcessingException {
        return TestUtils.asJsonString(CAKE_DTO);
    }
    public static String getNotFoundErrorPayloadAsJson() throws JsonProcessingException {
        return asJsonString(ErrorDTO.builder().code(HttpStatus.NOT_FOUND.toString()).message("No cake found with that id").build());
    }
    public static String getBadRequestErrorPayloadAsJson() throws JsonProcessingException {
        return asJsonString(ErrorDTO.builder().code(HttpStatus.BAD_REQUEST.toString()).message("The provided value 'aaa' is the incorrect type for the 'id' parameter.").build());
    }

    public static String getInternalServerErrorValidationErrorPayloadAsJson() throws JsonProcessingException {
        return asJsonString(ErrorDTO.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.toString()).message("Title is required. ").build());
    }

    public static String getInternalServerErrorJson() throws JsonProcessingException {
        return asJsonString(ErrorDTO.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.toString()).message("We have encountered an error. Please try again later.").build());
    }
    public static String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
