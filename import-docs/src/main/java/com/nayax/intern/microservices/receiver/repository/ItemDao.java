package com.nayax.intern.microservices.receiver.repository;


import com.nayax.intern.microservices.receiver.entity.ItemEntity;
import com.nayax.intern.microservices.receiver.utils.ItemFilter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemDao {
      public List<Long> save (List<ItemEntity> items);
      public List<Long> saveMerge(List<ItemEntity> items);
      public List getItems(ItemFilter filter);
      public Integer getItemsCount(ItemFilter filter);
}
