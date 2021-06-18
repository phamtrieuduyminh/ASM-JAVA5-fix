package edu.poly.shop.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
private Long productId;
private String name;
private int quatity;
private double unitPrice;
}
