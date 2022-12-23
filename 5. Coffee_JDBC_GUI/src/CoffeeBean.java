
public class CoffeeBean {
	private int id;
	private String name;
	private String origin;
	private int price;
	private String tasting;
	private String roasting;
	
	public CoffeeBean() {
		super();
	}

	public CoffeeBean(int id, String name, String origin, int price, String tasting, String roasting) {
		super();
		this.id = id;
		this.name = name;
		this.origin = origin;
		this.price = price;
		this.tasting = tasting;
		this.roasting = roasting;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getTasting() {
		return tasting;
	}

	public void setTasting(String tasting) {
		this.tasting = tasting;
	}

	public String getRoasting() {
		return roasting;
	}

	public void setRoasting(String roasting) {
		this.roasting = roasting;
	}
	

}
