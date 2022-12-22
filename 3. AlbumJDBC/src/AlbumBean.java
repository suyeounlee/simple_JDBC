
public class AlbumBean {
	private int num;
	private String song;
	private String singer;
	private String company;
	private int price;
	private String release_date;
	
	public AlbumBean() {
	}
	
	public AlbumBean(int num, String song, String singer, String company, int price, String release_date) {
		super();
		this.num = num;
		this.song = song;
		this.singer = singer;
		this.company = company;
		this.price = price;
		this.release_date = release_date;
	}


	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getSong() {
		return song;
	}
	public void setSong(String song) {
		this.song = song;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getRelease_date() {
		return release_date;
	}
	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}
	
	
}
