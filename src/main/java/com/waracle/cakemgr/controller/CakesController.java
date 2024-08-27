package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.dto.CakeDTO;
import com.waracle.cakemgr.dto.ErrorDTO;
import com.waracle.cakemgr.service.CakeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cakes")
@Tag(name = "Cakes Manager", description = "Rest API for Cakes Manager.")
public class CakesController {

    private final CakeService cakeService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Retrieve list of all cakes")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = CakeDTO.class))))
    @ApiResponse(responseCode = "400", description = "Bad Request.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    public ResponseEntity<List<CakeDTO>> getCakes(){
        log.info("retrieving cakes");
        return ResponseEntity.ok(cakeService.getAllCakes());
    }
}