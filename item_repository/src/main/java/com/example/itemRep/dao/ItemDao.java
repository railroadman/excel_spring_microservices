package com.example.itemRep.dao;

import com.example.itemRep.entity.ItemEntity;
import com.example.itemRep.tools.ItemFilter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemDao {
      public List<Long> save (List<ItemEntity> items);
      public List<Long> saveMerge(List<ItemEntity> items);
      public List getItems(ItemFilter filter);
      public Integer getItemsCount(ItemFilter filter);
}
