package org.mall.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.mall.entity.IdleItem;

import java.util.List;

public interface IdleItemMapper {
   @Select("SELECT itemID, uid, categoryID, itemName, description, originalPrice, price, transactionMode, itemStatus FROM idle_items")
   @Results(id = "userMap", value = {
           @Result(property = "itemID", column = "itemID", jdbcType = JdbcType.CHAR, id = true),
           @Result(property = "uid", column = "uid", jdbcType = JdbcType.CHAR),
           @Result(property = "categoryID", column = "categoryID", jdbcType = JdbcType.CHAR),
           @Result(property = "itemName", column = "itemName", jdbcType = JdbcType.VARCHAR),
           @Result(property = "description", column = "description", jdbcType = JdbcType.VARCHAR),
           @Result(property = "originalPrice", column = "originalPrice", jdbcType = JdbcType.DECIMAL),
           @Result(property = "price", column = "price", jdbcType = JdbcType.DECIMAL),
           @Result(property = "transactionMode", column = "transactionMode", jdbcType = JdbcType.INTEGER),
           @Result(property = "itemStatus", column = "itemStatus", jdbcType = JdbcType.INTEGER),
   })
   List<IdleItem> findAllItems();
}
