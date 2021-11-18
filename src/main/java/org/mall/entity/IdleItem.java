package org.mall.entity;

import lombok.Data;

import java.util.List;

@Data
public class IdleItem {
   private String itemID;
   private String uid;
   private String categoryID;
   private String itemName;
   private String description;
   private Double originalPrice;
   private Double price;
   private Integer transactionMode;
   private Integer itemStatus;
   private List<String> itemImageUrls;
}
