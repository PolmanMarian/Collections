package com.czmp.collections.service;

import com.czmp.collections.model.EndUser;
import com.czmp.collections.model.Item;
import com.czmp.collections.model.ItemCollection;
import com.czmp.collections.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public void userAddsItemToCollection(EndUser user, Item item, ItemCollection collection) throws NoPermissionException {
        if(!Objects.equals(collection.getEndUser().getId(), user.getId())){
            throw new NoPermissionException();
        }
        item.setCollection(collection);
        item.setCreatedOn(new Date());
        itemRepository.save(item);
    }

    public void userUpdatesItem(EndUser user, Item item) throws NoPermissionException{
        if(user.getCollections()
                .stream()
                .noneMatch(collection ->
                    collection.getId().equals(item.getCollection().getId())
                )
        ){
            throw new NoPermissionException();
        }
        itemRepository.save(item);
    }

    public void userDeletesItem(EndUser user, Item item) throws NoPermissionException{
        if(user.getCollections()
                .stream()
                .noneMatch(collection ->
                        collection.getId().equals(item.getCollection().getId())
                )
        ){
            throw new NoPermissionException();
        }
        itemRepository.delete(item);
    }

    public List<Item> getUserFeed(EndUser endUser){
        //itemRepository.fin
        return null;
    }


}
