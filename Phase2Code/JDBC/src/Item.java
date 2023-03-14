
public class Item {
	String ref;
	Double price;
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Item [ref=" + ref + ", price=" + price + "]";
	}
	

}
