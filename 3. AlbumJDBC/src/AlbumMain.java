import java.util.ArrayList;
import java.util.Scanner;

public class AlbumMain {

	AlbumDao adao = new AlbumDao();
	Scanner sc = new Scanner(System.in);

	AlbumMain() {
		init();
	}

	private void init() {

		while(true) {
			System.out.println("========== Menu ==========");
			System.out.println("1. Display all albums");
			System.out.println("2. Search");
			System.out.println("3. Search with price range");
			System.out.println("4. Album update");
			System.out.println("5. Album delete");
			System.out.println("6. Album add");
			System.out.println("7. Order albums");
			System.out.println("8. Program Exit");
			System.out.print("Select menu : ");

			int menu = sc.nextInt();

			switch(menu) {
			case 1: 
				ArrayList<AlbumBean> list = adao.getAllAlbums();
				showAlbums(list);
				break;
			case 2: 
				albumsBySearch();
				break;
			case 3: 
				getAlbumsByRange();
				break;
			case 4: 
				updateData();
				break;
			case 5: 
				deleteData();
				break;
			case 6: 
				insertData();
				break;
			case 7: 
				align();
				break;
			case 8: 
				System.out.println("Program exits...");
				System.exit(0);
			default: System.out.println("Enter only 1~8");
			}

		}
	}
	private void align() {
		System.out.println("Please select items to align");
		System.out.print("1.Num	2.Title 	3.Singer	>> ");
		int align_num = sc.nextInt();
		String colName = null;
		switch(align_num) {
		case 1: 
			colName="num";
			break;
		case 2: 
			colName="song";
			break;
		case 3: 
			colName="singer";
			break;
		default: System.out.println("Enter the number only 1~3.");
			return;
		}

		System.out.println("Choose a aligh method");
		System.out.print("1.Ascending order	2.Descending order	 >>");
		String way = null;

		int aligh_method = sc.nextInt();
		switch(aligh_method) { 
		case 1: 
			way="asc";
			break;
		case 2: 
			way="desc";
			break;
		default: System.out.println("Enter the number 1 or 2.");
			return;
		}

		ArrayList<AlbumBean> list = adao.align(colName, way);
		showAlbums(list);
	}

	private void deleteData() {
		System.out.print("Enter the number you want to delete: ");
		int num = sc.nextInt();
		int cnt = adao.deleteData(num);
		if(cnt == -1) {
			System.out.println("Delete failure");
		} else {
			System.out.println("Delete success");
		}
	}

	private void updateData() {
		System.out.print("Enter the number you want to update: ");
		int num = sc.nextInt();

		System.out.print("Enter new title: ");
		String song = sc.next();

		System.out.print("Enter new singer: ");
		String singer = sc.next();

		System.out.print("Enter new company: ");
		String company = sc.next();

		System.out.print("Enter new price: ");
		int price = sc.nextInt();

		System.out.print("Enter new release date: ");
		String release_date = sc.next();


		AlbumBean ab = new AlbumBean();
		ab.setNum(num);
		ab.setSong(song);
		ab.setSinger(singer);
		ab.setCompany(company);
		ab.setPrice(price);
		ab.setRelease_date(release_date);

		int cnt = adao.updataData(ab);
		if(cnt <=0 ) {
			System.out.println("update failure");
		} else {
			System.out.println("update success");
		}
	} //updateData

	private void  insertData() {
		System.out.println("Numbers will be entered in sequence");

		System.out.print("Enter song title: ");
		String song = sc.next();

		System.out.print("Enter singer: ");
		String singer = sc.next();

		System.out.print("Enter company: ");
		String company = sc.next();

		System.out.print("Enter price: ");
		int price = sc.nextInt();

		System.out.print("Enter release date: ");
		String release_date = sc.next();


		AlbumBean ab = new AlbumBean();
		ab.setSong(song);
		ab.setSinger(singer);
		ab.setCompany(company);
		ab.setPrice(price);
		ab.setRelease_date(release_date);

		int cnt = adao.insertData(ab);
		if(cnt <=0 ) {
			System.out.println("insert failure");
		} else {
			System.out.println("insert success");
		}

	}

	private void getAlbumsByRange() { //order by price desc, singer asc
		System.out.print("Enter start range: ");
		int from = sc.nextInt();

		System.out.print("Enter last range: ");
		int to = sc.nextInt();


		ArrayList<AlbumBean> list = adao.getAlbumsByRange(from, to);
		showAlbums(list);

	}

	private void albumsBySearch() {
		System.out.println("Search by 1. Song	2. Singer	3. Company");
		System.out.print("Select option: ");
		int option = sc.nextInt();

		String colName;

		switch(option) {
		case 1:
			colName="song";
			System.out.print("Enter the song: ");
			break;
		case 2: 
			colName="singer";
			System.out.print("Enter the singer: ");
			break;
		case 3: 
			colName="company";
			System.out.print("Enter the company: ");
			break;
		default: System.out.println("Enter only 1~3.");
		return; //if wrong input entered
		}

		String search_word = sc.next();
		ArrayList<AlbumBean> list = adao.albumsBySearch(colName, search_word);
		showAlbums(list);



	}//albumsBySearch()

	private void showAlbums(ArrayList<AlbumBean> list) {
		System.out.println("Num\tSong\t\tSinger\tCompany\tPrice\tRelease_date");
		for(AlbumBean ab : list) {
			System.out.println(ab.getNum() + "\t" + ab.getSong()+ "\t" +ab.getSinger()+ "\t" + ab.getCompany()+ "\t" + ab.getPrice()+ "\t" + ab.getRelease_date());
		}

	} //showAlbums

	public static void main(String[] args) {

		new AlbumMain();
	}

}
