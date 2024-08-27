package com.waracle.cakemgr.repository;

import com.waracle.cakemgr.entity.CakeEntity;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)//integrates the Spring test context framework into the JUnit 5 Jupiter programming model.
@DataJpaTest//will only load the Spring Data JPA slice of the Spring context. This integration test as an external database is used
public class CakeRepositoryTest {

    @Autowired
    private CakeRepository cakeRepository;

    @AfterEach
    public void tearDown() {
        cakeRepository.deleteAll();
    }

    @Test
    public void givenGetAllCakesShouldReturnListOfAllCakes(){
        CakeEntity cake1 = CakeEntity.builder().id(1).title("Lemon cheesecake").description("A cheesecake made of lemon").imageURL("https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg").build();
        CakeEntity cake2 = CakeEntity.builder().id(2).title("victoria sponge").description("sponge with jam").imageURL("http://www.bbcgoodfood.com/sites/bbcgoodfood.com/files/recipe_images/recipe-image-legacy-id--1001468_10.jpg").build();
        cakeRepository.save(cake1);
        cakeRepository.save(cake2);

        List<CakeEntity> cakeList = cakeRepository.findAll();
        assertEquals("victoria sponge", cakeList.get(1).getTitle());
    }

    @Test
    public void givenGetAllCakesShouldReturnEmptyList(){
        List<CakeEntity> cakeList = cakeRepository.findAll();
        assertTrue(cakeList.isEmpty());
    }

    @Test
    public void givenGetCakeWithIDShouldReturnSingleCake(){
        CakeEntity cake1 = CakeEntity.builder().title("Lemon cheesecake").description("A cheesecake made of lemon").imageURL("https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg").build();
        CakeEntity savedCake = cakeRepository.save(cake1);

        CakeEntity cake = cakeRepository.getReferenceById(savedCake.getId());
        assertEquals("Lemon cheesecake", cake.getTitle());
    }

    @Test
    public void givenGetCakeWithNonIDShouldThrowEntityNotFoundException(){
        CakeEntity cake = cakeRepository.getReferenceById(1000);
        assertThrows(EntityNotFoundException.class, cake::getTitle);
    }
}
