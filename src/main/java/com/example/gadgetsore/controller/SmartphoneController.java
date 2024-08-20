package com.example.gadgetsore.controller;

import com.example.gadgetsore.entity.Smartphone;
import com.example.gadgetsore.repository.SmartphoneRepository;
import com.example.gadgetsore.service.SmartphoneService;
import com.example.gadgetsore.util.SmartphoneFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Tag(name = "Smartphone", description = "CRUD operations with Smartphone")
@RestController
@RequestMapping("/smartphone")
public class SmartphoneController {

    private final SmartphoneService smartphoneService;

    private final SmartphoneRepository smartphoneRepository;

    public SmartphoneController(SmartphoneRepository smartphoneRepository, SmartphoneService smartphoneService) {
        this.smartphoneRepository = smartphoneRepository;
        this.smartphoneService = smartphoneService;
    }

    @Operation(summary = "Selected smartphones", description = "Returns the collection of smartphones according to the specified characteristics")
    @ApiResponse(responseCode = "200", description = "All ok")
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @GetMapping
    public Collection<Smartphone> getMatchingSmartphone(@RequestBody Optional<SmartphoneFilter> smartphoneFilter) {
        smartphoneFilter.ifPresent(filer -> filer.getListBrand().forEach(System.out::println));
        return smartphoneService.getMatchingSmartphone(smartphoneFilter);
    }

    @Operation(summary = "Get smartphone by ID", description = "Returns the smartphone by the specified id")
    @ApiResponse(responseCode = "200", description = "All ok")
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @GetMapping("/{id}")
    public Smartphone findById(@PathVariable("id") Smartphone smartphone) {
        return smartphone;
    }


    @Operation(summary = "Delete by ID", description = "Deletes the smartphone by the specified id from the database")
    @ApiResponse(responseCode = "204", description = "Тo content")
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteById(@PathVariable Long id){

        if(!smartphoneRepository.existsById(id)){
            ResponseEntity.notFound();
        }

        smartphoneRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update by ID", description = "Makes changes to the database for the specified smartphone")
    @ApiResponse(responseCode = "200", description = "ok")
    @ApiResponse(responseCode = "400", description = "bad request")
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Smartphone smartphone){

        if(id == null || !id.equals(smartphone.getId()))
            ResponseEntity.badRequest();

        if(!smartphoneRepository.existsById(id))
            ResponseEntity.notFound();

        smartphoneRepository.save(smartphone);

        return ResponseEntity.ok().body(smartphone);
    }

    @Operation(summary = "Create", description = "Adds a new smartphone to the database")
    @ApiResponse(responseCode = "201", description = "created")
    @ApiResponse(responseCode = "400", description = "bad request")
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @PostMapping
    public ResponseEntity<Smartphone> create(@RequestBody Smartphone smartphone){

        Smartphone newSmartphone = smartphoneRepository.save(Objects.requireNonNull(smartphone));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newSmartphone.getId())
                .toUri();

        return ResponseEntity.created(uri).body(newSmartphone);
    }

    @GetMapping("/filter")
    public SmartphoneFilter getFilter() {
        List<Smartphone> allSmartphones = smartphoneRepository.findAll();
        SmartphoneFilter smartphoneFilter = new SmartphoneFilter();

        allSmartphones.forEach(smartphone -> {
            if(smartphone.getBrand() != null){
                smartphoneFilter.getListBrand().add(smartphone.getBrand());
            }

            if(smartphone.getModel() != null){
                smartphoneFilter.getListModel().add(smartphone.getModel());
            }

            if(smartphone.getMemory() != null){
                smartphoneFilter.getListMemory().add(smartphone.getMemory());
            }

            if(smartphone.getStorage() != null){
                smartphoneFilter.getListStorage().add(smartphone.getStorage());
            }

            if(smartphone.getColor() != null){
                smartphoneFilter.getListColor().add(smartphone.getColor());
            }
        });

        return smartphoneFilter;
    }

    @PostMapping("/filter")
    public Collection<Smartphone> filterSmartphone(@RequestBody Optional<SmartphoneFilter> smartphoneFilter) {
        return smartphoneService.getMatchingSmartphone(smartphoneFilter);
    }

}
