package com.example.demo.controller;


import com.example.demo.model.Library;
import com.example.demo.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryService libraryService;

    @PostMapping()
    public ResponseEntity<Library> createLibrary(@RequestBody Library library){
        return ResponseEntity.ok(libraryService.createLibrary(library));
    }

    @GetMapping()
    public ResponseEntity<List<Library>> getAllLibrary(){
        return ResponseEntity.ok(libraryService.fetchAllLibraries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Library> getLibraryById(@PathVariable Long id){
        return ResponseEntity.ok(libraryService.fetchLibraryById(id));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteLibrary(@PathVariable Long id){
         libraryService.deleteLibraryById(id);
    }

}
