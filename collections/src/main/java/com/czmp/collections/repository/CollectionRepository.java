package com.czmp.collections.repository;

import com.czmp.collections.model.ItemCollection;
import com.czmp.collections.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionRepository extends JpaRepository<ItemCollection, Long> {
    Optional<ItemCollection> findByName(String name);

    List<ItemCollection> findByNameLike(String s);

    List<ItemCollection> findByNameLikeAndTags(String s, Tag tag);
}
