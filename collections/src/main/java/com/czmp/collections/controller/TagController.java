package com.czmp.collections.controller;

import com.czmp.collections.dto.MessageResponseDTO;
import com.czmp.collections.dto.TagDTO;
import com.czmp.collections.model.Tag;
import com.czmp.collections.repository.EndUserRepository;
import com.czmp.collections.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
public class TagController  {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private EndUserRepository endUserRepository;

    @GetMapping(value = "/tag/all")
    ResponseEntity<?> getAll(){
        return ResponseEntity.ok(tagRepository.findAll());
    }

    @GetMapping(value ="/tag/find/id={id}")
    public ResponseEntity<?> getByName(@PathVariable long id){
        Optional<Tag> tag = tagRepository.findById(id);
        if(tag.isPresent()){
            return ResponseEntity.ok(tag.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDTO("Tag not found"));
        }
    }
    @GetMapping(value ="/tag/find/name={name}")
    public ResponseEntity<?> getByName(@PathVariable String name){
        Optional<Tag> tag = tagRepository.findByName(name);
        if(tag.isPresent()){
            return ResponseEntity.ok(tag.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDTO("Tag not found"));
        }
    }
    @PostMapping(value ="/tag/save")
    public ResponseEntity<?> save(@RequestBody TagDTO tagDTO) {
        if(tagRepository.findByName(tagDTO.getName()).isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponseDTO("Tag already exists"));
        }
        Tag tag = new Tag();
        tag.setName(tagDTO.getName());

        tagRepository.save(tag);
        return new ResponseEntity<>(new MessageResponseDTO("Tag added successfully"), HttpStatus.OK);
    }

    @PostMapping(value ="/tag/delete")
    public ResponseEntity<?> delete(@RequestBody Long id) {
        tagRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponseDTO("Deleted tag successfully"));
    }

    @PostMapping(value ="/tag/delete/name={name}")
    public ResponseEntity<?> delete(@PathVariable String name) {
        tagRepository.deleteByName(name);
        return ResponseEntity.ok(new MessageResponseDTO("Deleted tag successfully"));
    }

}
