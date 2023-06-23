package com.czmp.collections.controller;

import com.czmp.collections.dto.CollectionDTO;
import com.czmp.collections.dto.ItemDTO;
import com.czmp.collections.dto.MessageResponseDTO;
import com.czmp.collections.model.EndUser;
import com.czmp.collections.model.Item;
import com.czmp.collections.model.ItemCollection;
import com.czmp.collections.model.Tag;
import com.czmp.collections.repository.CollectionRepository;
import com.czmp.collections.repository.EndUserRepository;
import com.czmp.collections.repository.TagRepository;
import com.czmp.collections.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NoPermissionException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class CollectionController {
    @Autowired
    CollectionRepository collectionRepository;
    @Autowired
    EndUserRepository endUserRepository;
    @Autowired
    CollectionService collectionService;

    @Autowired
    TagRepository tagRepository;

    @GetMapping(value ="/collection/all")
    public List<ItemCollection> getAll() {
        return collectionRepository.findAll();
    }

    @GetMapping(value="collection/find/id={id}")
    public @ResponseBody ResponseEntity<?> getById(@PathVariable long id){
        Optional<ItemCollection> collection = collectionRepository.findById(id);
        if(collection.isPresent()) {
            return ResponseEntity.ok(collection.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDTO("Collection not found!"));
    }

    @GetMapping(value="collection/find/name={name}")
    public @ResponseBody ResponseEntity<?> getByName(@PathVariable String name){
        Optional<ItemCollection> collection = collectionRepository.findByName(name);
        if(collection.isPresent()) {
            return ResponseEntity.ok(collection.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDTO("Collection not found!"));
    }

    @PostMapping(value ="/collection/save")
    @ResponseBody
    public ResponseEntity<?> save(Principal principal, @RequestBody CollectionDTO collectionDTO) {
        ItemCollection collection = new ItemCollection();
        collection.setName(collectionDTO.getName());
        collection.setDescription( collectionDTO.getDescription());
        for(String tagName : collectionDTO.getTags()){
            Optional<Tag> tag = tagRepository.findByName(tagName);
            tag.ifPresent(tagE -> collection.getTags().add(tagE));
        }
        Optional<EndUser> user = endUserRepository.findByUsername(principal.getName());
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponseDTO("Your identity could not be confirmed!"));
        }
        collectionService.userAddCollection(user.get(), collection);
        return new ResponseEntity<>(new MessageResponseDTO("Collection added successfully to " + user.get().getUsername()), HttpStatus.OK);
    }

    @PostMapping(value ="/collection/update")
    public ResponseEntity<?> update(Principal principal, @RequestBody CollectionDTO collectionDTO) {
        Optional<EndUser> user = endUserRepository.findByUsername(principal.getName());
        Optional<ItemCollection> collection = collectionRepository.findById(collectionDTO.getId());
        if(user.isEmpty()){
            return new ResponseEntity<>(new MessageResponseDTO("Your identity could not be confirmed"), HttpStatus.UNAUTHORIZED);
        }
        if(collection.isEmpty()){
            return new ResponseEntity<>(new MessageResponseDTO("Collection not found"), HttpStatus.NOT_FOUND);
        }
        collection.get().setName(collectionDTO.getName());
        collection.get().setDescription(collectionDTO.getDescription());
        collection.get().getTags().clear();
        for(String tagName : collectionDTO.getTags()){
            Optional<Tag> tag = tagRepository.findByName(tagName);
            tag.ifPresent(tagE -> collection.get().getTags().add(tagE));
        }

        try{
            collectionService.userUpdatesCollection(user.get(), collection.get());
        } catch (NoPermissionException e){
            return new ResponseEntity<>(new MessageResponseDTO("You don't have permission to edit this item"), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new MessageResponseDTO("Item edited successfully"), HttpStatus.OK);
    }


    @PostMapping(value ="/collection/delete")
    public ResponseEntity<?> delete(Principal principal, @RequestBody Long id) {
        Optional<EndUser> user = endUserRepository.findByUsername(principal.getName());
        Optional<ItemCollection> collection = collectionRepository.findById(id);
        if(user.isEmpty()){
            return new ResponseEntity<>(new MessageResponseDTO("Your identity could not be confirmed"), HttpStatus.UNAUTHORIZED);
        }
        if(collection.isEmpty()){
            return new ResponseEntity<>(new MessageResponseDTO("Collection not found"), HttpStatus.NOT_FOUND);
        }
        try{
            collectionService.userDeletesItem(user.get(), collection.get());
        } catch (NoPermissionException e){
            return new ResponseEntity<>(new MessageResponseDTO("You don't have permission to delete this collection"), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new MessageResponseDTO("Item deleted successfully"), HttpStatus.OK);
    }

    @GetMapping(value ="/collection/search")
    public ResponseEntity<?> searchByName(@RequestParam Map<String,String> params){
        String name = params.get("name");
        String tagName = params.get("tag");
        if(name == null) {
            name = "";
        }
        if(tagName == null){
            return ResponseEntity.ok(collectionRepository.findByNameLike("%" + name + "%"));
        }
        Optional<Tag> tag = tagRepository.findByName(tagName);
        if(tag.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<Item>());
        }
        return ResponseEntity.ok(collectionRepository.findByNameLikeAndTags("%"+name+"%", tag.get()));
    }

}
