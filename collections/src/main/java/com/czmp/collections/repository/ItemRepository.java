package com.czmp.collections.repository;


import com.czmp.collections.model.Item;
import com.czmp.collections.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByName(String name);
    List<Item> findByNameLike(String name);
    List<Item> findByNameLikeAndTags(String name, Tag tags);
    List<Item> findFirst50ByOrderByCreatedOnDesc();


}

