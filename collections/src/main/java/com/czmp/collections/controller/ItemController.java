package com.czmp.collections.controller;

import com.czmp.collections.dto.CollectionDTO;
import com.czmp.collections.dto.ItemDTO;
import com.czmp.collections.dto.MessageResponseDTO;
import com.czmp.collections.dto.TagDTO;
import com.czmp.collections.model.EndUser;
import com.czmp.collections.model.Item;
import com.czmp.collections.model.ItemCollection;
import com.czmp.collections.model.Tag;
import com.czmp.collections.repository.CollectionRepository;
import com.czmp.collections.repository.EndUserRepository;
import com.czmp.collections.repository.ItemRepository;
import com.czmp.collections.repository.TagRepository;
import com.czmp.collections.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.init.RepositoriesPopulatedEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import javax.naming.NoPermissionException;
import javax.servlet.http.HttpServlet;
import java.security.Principal;
import java.util.*;

@RestController
@CrossOrigin("*")
public class ItemController {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    CollectionRepository collectionRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    EndUserRepository endUserRepository;
    @Autowired
    ItemService itemService;

    @GetMapping(value ="/item/all")
    public ResponseEntity<List<Item>> getAll(){
        return ResponseEntity.ok(itemRepository.findAll());
    }

    @GetMapping(value ="/item/find/id={id}")
    public ResponseEntity<?> getById(@PathVariable long id){
        Optional<Item> item = itemRepository.findById(id);
        if(item.isPresent()){
            return ResponseEntity.ok(item.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDTO("Item not found"));
        }
    }

    @GetMapping(value ="/item/find/name={name}")
    public ResponseEntity<?> getByName(@PathVariable String name){
        Optional<Item> item = itemRepository.findByName(name);
        if(item.isPresent()){
            return ResponseEntity.ok(item.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDTO("Item not found"));
        }
    }

    @PostMapping(value ="/item/save/collection={id}")
    public ResponseEntity<?> save(Principal principal, @PathVariable long id, @RequestBody ItemDTO itemDTO) {
        Optional<ItemCollection> collection = collectionRepository.findById(id);
        Optional<EndUser> user = endUserRepository.findByUsername(principal.getName());
        if(!collection.isPresent()){
            return new ResponseEntity<>(new MessageResponseDTO("No collection with id " + id), HttpStatus.NOT_FOUND);
        }
        if(!user.isPresent()){
            return new ResponseEntity<>(new MessageResponseDTO("Your identity could not be confirmed"), HttpStatus.UNAUTHORIZED);
        }
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setStatus(itemDTO.getStatus());
        item.setCollection(collection.get());
        for(String tagName : itemDTO.getTags()){
            Optional<Tag> tag = tagRepository.findByName(tagName);
            tag.ifPresent(tagE -> item.getTags().add(tagE));
        }
        item.setLikes(new ArrayList<>());

        try{
            itemService.userAddsItemToCollection(user.get(), item, collection.get());
        } catch (NoPermissionException e){
            return new ResponseEntity<>(new MessageResponseDTO("You don't have permission to add items to this collection"), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new MessageResponseDTO("Item added successfully"), HttpStatus.OK);
    }

    @PostMapping(value ="/item/update")
    public ResponseEntity<?> update(Principal principal, @RequestBody ItemDTO itemDTO) {
        Optional<EndUser> user = endUserRepository.findByUsername(principal.getName());
        Optional<Item> item = itemRepository.findById(itemDTO.getId());
        if(user.isEmpty()){
            return new ResponseEntity<>(new MessageResponseDTO("Your identity could not be confirmed"), HttpStatus.UNAUTHORIZED);
        }
        if(item.isEmpty()){
            return new ResponseEntity<>(new MessageResponseDTO("Item not found"), HttpStatus.NOT_FOUND);
        }
        item.get().setName(itemDTO.getName());
        item.get().setDescription(itemDTO.getDescription());
        item.get().setStatus(itemDTO.getStatus());
        item.get().getTags().clear();
        for(String tagName : itemDTO.getTags()){
            Optional<Tag> tag = tagRepository.findByName(tagName);
            tag.ifPresent(tagE -> item.get().getTags().add(tagE));
        }

        try{
            itemService.userUpdatesItem(user.get(), item.get());
        } catch (NoPermissionException e){
            return new ResponseEntity<>(new MessageResponseDTO("You don't have permission to edit this item"), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new MessageResponseDTO("Item edited successfully"), HttpStatus.OK);
    }

    @PostMapping(value ="/item/delete")
    public ResponseEntity<?> delete(Principal principal, @RequestBody Long id) {
        Optional<EndUser> user = endUserRepository.findByUsername(principal.getName());
        Optional<Item> item = itemRepository.findById(id);
        if(user.isEmpty()){
            return new ResponseEntity<>(new MessageResponseDTO("Your identity could not be confirmed"), HttpStatus.UNAUTHORIZED);
        }
        if(item.isEmpty()){
            return new ResponseEntity<>(new MessageResponseDTO("Item not found"), HttpStatus.NOT_FOUND);
        }

        try{
            itemService.userDeletesItem(user.get(), item.get());
        } catch (NoPermissionException e){
            return new ResponseEntity<>(new MessageResponseDTO("You don't have permission to delete this item"), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new MessageResponseDTO("Item deleted successfully"), HttpStatus.OK);
    }

    @GetMapping(value ="/item/search")
    public ResponseEntity<?> searchByName(@RequestParam Map<String,String> params){
        String name = params.get("name");
        String tagName = params.get("tag");
        if(name == null) {
            name = "";
        }
        if(tagName == null){
            return ResponseEntity.ok(itemRepository.findByNameLike("%" + name + "%"));
        }
        Optional<Tag> tag = tagRepository.findByName(tagName);
        if(tag.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<Item>());
        }
        return ResponseEntity.ok(itemRepository.findByNameLikeAndTags("%"+name+"%", tag.get()));
    }

}

