package com.example.demo.service;

import com.example.demo.model.Library;
import com.example.demo.repository.LibraryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {

    private final LibraryRepository libraryRepository;

    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;

    }

    public Library createLibrary(Library library){
       return libraryRepository.save(library);
    }

    public List<Library> fetchAllLibraries(){
        return libraryRepository.findAll();
    }

    public Library fetchLibraryById(Long id){
        return libraryRepository.findById(id).get();
    }

    public void deleteLibraryById(Long id){
        libraryRepository.deleteById(id);
    }
}
