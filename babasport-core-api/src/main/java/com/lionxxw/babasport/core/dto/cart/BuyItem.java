package com.lionxxw.babasport.core.dto.cart;


import com.lionxxw.babasport.core.dto.product.Sku;

/**
 * <p>Description: 购物项 </p>
 *
 * @author wangxiang
 * @date 16/6/12 上午9:41
 * @version 1.0
 */
public class BuyItem {

	//Sku
	private Sku sku;
	//数量
	private int amount =1;
	public Sku getSku() {
		return sku;
	}
	public void setSku(Sku sku) {
		this.sku = sku;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + ((sku == null) ? 0 : sku.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass()) 
			return false;
		BuyItem other = (BuyItem) obj;
		if (sku == null) {
			if (other.sku != null)
				return false;
		} else if (!sku.getId().equals(other.sku.getId()))
			return false;
		return true;
	}
}
