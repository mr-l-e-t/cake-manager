package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.dto.CakeDTO;
import com.waracle.cakemgr.dto.ErrorDTO;
import com.waracle.cakemgr.service.CakeService;
import com.waracle.cakemgr.validator.CakeAction;
import com.waracle.cakemgr.validator.CakeManagerValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cake")
@Tag(name = "Cake Manager", description = "Rest API for Cake Manager.")
public class CakeController {

    private final CakeService cakeService;

    private final CakeManagerValidator cakeManagerValidator;

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Retrieve single cake")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = CakeDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "404", description = "Not Found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    public ResponseEntity<CakeDTO> getCake(@PathVariable() Integer id){
        log.info("retrieve single cake with id: {}", id);
        return ResponseEntity.ok(cakeService.getCake(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Create a single cake cake")
    @ApiResponse(responseCode = "201", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = CakeDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "500", description = "Server Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    public ResponseEntity<CakeDTO> createCake(@Parameter(description = "cake to create", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = CakeDTO.class)
    )) @Valid @RequestBody CakeDTO cakeToCreate) {
        log.info("processing create cake request : {}", cakeToCreate);
        cakeManagerValidator.validateCake(cakeToCreate, CakeAction.CREATE);
        return new ResponseEntity<>(cakeService.save(cakeToCreate), HttpStatus.CREATED);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Update values in a single cake cake")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = CakeDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "404", description = "Not Found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "500", description = "Server Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    public ResponseEntity<CakeDTO> updateCake(@Parameter(description = "cake to update", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = CakeDTO.class)
    )) @Valid @RequestBody CakeDTO cakeToUpdate) {
        log.info("processing update cake request : {}", cakeToUpdate);
        cakeManagerValidator.validateCake(cakeToUpdate, CakeAction.UPDATE);
        return new ResponseEntity<>(cakeService.update(cakeToUpdate), HttpStatus.OK);
    }
}
