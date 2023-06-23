package com.czmp.collections.service;

import com.czmp.collections.model.Item;
import com.czmp.collections.repository.ItemRepository;
import com.czmp.collections.model.EndUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class EndUserService {
    @Autowired
    ItemRepository itemRepository;
    public List<Item> getFeed(EndUser user) {
        List<Item> items = itemRepository.findFirst50ByOrderByCreatedOnDesc();
        items.sort(Comparator.comparingInt(o -> - o.getLikes().size()));
        items = items.stream().limit(8).toList();
        return items;
    }
}
