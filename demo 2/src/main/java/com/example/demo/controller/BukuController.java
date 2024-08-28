package com.example.demo.controller;

import com.example.demo.model.Buku;
import com.example.demo.repository.BukuRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/buku")
public class BukuController {

    private final BukuRepository bukuRepository;

    public BukuController(BukuRepository bukuRepository) {
        this.bukuRepository = bukuRepository;
    }

    @GetMapping
    public List<Buku> getAllBuku() {
        return bukuRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Buku> getBukuById(@PathVariable Long id) {
        Optional<Buku> buku = bukuRepository.findById(id);
        return buku.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Buku> createBuku(@RequestBody Buku buku) {
        if (buku.getJudul() == null || buku.getJudul().isEmpty() ||
                buku.getPenulis() == null || buku.getPenulis().isEmpty() ||
                buku.getTahunTerbit() == null || buku.getTahunTerbit() <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Buku savedBuku = bukuRepository.save(buku);
        return new ResponseEntity<>(savedBuku, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Buku> updateBuku(@PathVariable Long id, @RequestBody Buku buku) {
        Optional<Buku> existingBuku = bukuRepository.findById(id);
        if (existingBuku.isPresent()) {
            Buku updatedBuku = existingBuku.get();
            updatedBuku.setJudul(buku.getJudul());
            updatedBuku.setPenulis(buku.getPenulis());
            updatedBuku.setTahunTerbit(buku.getTahunTerbit());
            bukuRepository.save(updatedBuku);
            return ResponseEntity.ok(updatedBuku);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuku(@PathVariable Long id) {
        if (bukuRepository.existsById(id)) {
            bukuRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
