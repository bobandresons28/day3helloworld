package com.example.demo.controller;

import com.example.demo.model.Entities;
import com.example.demo.repository.EntitiesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/entities")
public class EntitiesController {

    private final EntitiesRepository entitiesRepository;

    public EntitiesController(EntitiesRepository entitiesRepository) {
        this.entitiesRepository = entitiesRepository;
    }

    @GetMapping
    public List<Entities> getAllEntities() {
        return entitiesRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entities> getEntitiesById(@PathVariable Long id) {
        Optional<Entities> entities = entitiesRepository.findById(id);
        return entities.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Entities> createEntities(@RequestBody Entities entities) {
        if (entities.getNama() == null || entities.getNama().isEmpty() ||
                entities.getDeskripsi() == null || entities.getDeskripsi().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Entities savedEntities = entitiesRepository.save(entities);
        return new ResponseEntity<>(savedEntities, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entities> updateEntities(@PathVariable Long id, @RequestBody Entities entities) {
        Optional<Entities> existingEntities = entitiesRepository.findById(id);
        if (existingEntities.isPresent()) {
            Entities updatedEntities = existingEntities.get();
            updatedEntities.setNama(entities.getNama());
            updatedEntities.setDeskripsi(entities.getDeskripsi());
            entitiesRepository.save(updatedEntities);
            return ResponseEntity.ok(updatedEntities);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntities(@PathVariable Long id) {
        if (entitiesRepository.existsById(id)) {
            entitiesRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
