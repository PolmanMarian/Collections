package com.czmp.collections.controller;

import com.czmp.collections.dto.EndUserDTO;
import com.czmp.collections.dto.ItemDTO;
import com.czmp.collections.dto.MessageResponseDTO;
import com.czmp.collections.model.*;
import com.czmp.collections.repository.EndUserRepository;
import com.czmp.collections.repository.ItemRepository;
import com.czmp.collections.repository.NotificationRepository;
import com.czmp.collections.service.EndUserService;
import com.czmp.collections.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import javax.naming.NoPermissionException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class EndUserController {
    @Autowired
    EndUserRepository endUserRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    NotificationService notificationService;
    @Autowired
    EndUserService endUserService;

    @GetMapping(value ="/user/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(endUserRepository.findAll());
    }

    @RequestMapping(value = "/user/self", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getSelf(Principal principal) {
        Optional<EndUser> user = endUserRepository.findByUsername(principal.getName());
        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDTO("No user found!"));
        }
    }
    @GetMapping(value ="/user/find/id={id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Optional<EndUser> user = endUserRepository.findById(id);
        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDTO("User not found!"));
        }
    }

    @GetMapping(value ="/user/find/username={username}")
    public ResponseEntity<?> getByUsername(@PathVariable String username){
        Optional<EndUser> user = endUserRepository.findByUsername(username);
        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDTO("User not found!"));
        }
    }

    @PostMapping(value ="/user/like-item/id={id}")
    public ResponseEntity<?> likeItem(Principal principal, @PathVariable Long id){
        Optional<EndUser> user = endUserRepository.findByUsername(principal.getName());
        Optional<Item> item = itemRepository.findById(id);
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponseDTO("Your identity could not be confirmed"));
        }
        if(item.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDTO("Item not found!"));
        }
        if(user.get().getLikedItems().stream().anyMatch(item1 -> item1.getId().equals(item.get().getId()))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponseDTO("Item already liked!"));
        }
        user.get().getLikedItems().add(item.get());
        endUserRepository.save(user.get());
        notificationService.likeNotification(item.get().getCollection().getEndUser(), user.get(), item.get());
        return ResponseEntity.ok(new MessageResponseDTO("Item liked!"));
    }

    @PostMapping(value ="/user/unlike-item/id={id}")
    public ResponseEntity<?> unlikeItem(Principal principal, @PathVariable Long id){
        Optional<EndUser> user = endUserRepository.findByUsername(principal.getName());
        Optional<Item> item = itemRepository.findById(id);
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponseDTO("Your identity could not be confirmed"));
        }
        if(item.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponseDTO("Item not found!"));
        }
        user.get().getLikedItems().removeIf(item1 -> id.equals(item1.getId()));
        endUserRepository.save(user.get());
        return ResponseEntity.ok(new MessageResponseDTO("Item unliked if it was previously liked!"));
    }

    @GetMapping(value ="/user/liked-items")
    public ResponseEntity<?> getLiked(Principal principal){
        Optional<EndUser> user = endUserRepository.findByUsername(principal.getName());
        if(user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponseDTO("Your identity could not be confirmed"));
        }
        return ResponseEntity.ok(user.get().getLikedItems());
    }

    @GetMapping(value ="/user/feed")
    public ResponseEntity<?> getFeed(Principal principal){
        Optional<EndUser> user = endUserRepository.findByUsername(principal.getName());
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponseDTO("Your identity could not be confirmed"));
        }
        List<Item> items = endUserService.getFeed(user.get());

        return ResponseEntity.ok(items);
    }
}
