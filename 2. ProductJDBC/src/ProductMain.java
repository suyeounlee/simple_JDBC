import java.util.ArrayList;
import java.util.Scanner;

//project right click -> properties -> Library -> ClassPath -> Add External JARs -> ojdbc7.jar file
public class ProductMain {

	Scanner sc = new Scanner(System.in);
	ProductDao pdao = new ProductDao();
	
	ProductMain() {
		init();
	}
	
	
	private void init() {
		while(true) {
			System.out.println("===== Menu =====");
			System.out.println("1. Display all products");
			System.out.println("2. Display products with ID");
			System.out.println("3. Display products with category");
			System.out.println("4. Update Products");
			System.out.println("5. Product delete");
			System.out.println("6. Product add");
			System.out.println("7. Program exit");
			System.out.print("Enter number >>");
			int menu = sc.nextInt();
			
			switch(menu) {
			case 1:
				ArrayList<ProductBean> list = pdao.getAllProducts();
				showProducts(list);
				break;
			case 2:
				getProductsById();
				break; 
			case 3:
				getProductsByCategory();
				break;
			case 4:
				deleteData();
				break;
			case 5:
				updateData();
				break;
			case 6:
				insertData();
				break;
			case 7:
				System.out.println("Program exits...");
				System.exit(0);
			default: System.out.println("Only numbers 1 to 7 can be entered.");
			}
		}
	} //init



	private void updateData() {
		System.out.print("Enter the ID you want to update: ");
		int id = sc.nextInt();
		
		System.out.print("Enter new name: ");
		String name = sc.next();
		
		System.out.print("Enter new stock: ");
		int stock = sc.nextInt();
		
		System.out.print("Enter new price: ");
		int price = sc.nextInt();
		
		System.out.print("Enter new category: ");
		String category = sc.next();
		
		System.out.print("Enter new date:(YYYY-MM-DD) ");
		String date = sc.next();
		
		ProductBean pb = new ProductBean(id, name, stock, price, category, date);
		int cnt = pdao.updateData(pb);
		if(cnt == -1) {
			System.out.println("Update failure");
		}else  {
			System.out.println("Update success");
		}
		
	}


	private void deleteData() {
		System.out.print("Enter the ID you want to delete: ");
		int id = sc.nextInt();
		int cnt = pdao.deleteData(id);
		if(cnt == -1) {
			System.out.println("Delete failure");
		} else {
			System.out.println("Delete success");
		}
		
	}

	private void insertData() {
		System.out.println("ID will be entered by sequence");
		System.out.print("Enter the name: ");
		String name = sc.next();
		
		System.out.print("Enter the stock: ");
		int stock = sc.nextInt();
		
		System.out.print("Enter the price: ");
		int price = sc.nextInt();
		
		System.out.print("Enter the category: ");
		String category = sc.next();
		
		System.out.print("Enter the date:(YYYY-MM-DD) ");
		String date = sc.next();
		
		
		ProductBean pb = new ProductBean(0, name, stock, price, category, date);
		int cnt = pdao.insertData(pb);
		if(cnt == -1) {
			System.out.println("failure in SQL execution");
		}else {
			System.out.println("Update success");
		}
		
	}

	private void getProductsByCategory() {
		System.out.print("Enter the category you are looking for: ");
		String cate = sc.next();
		ArrayList<ProductBean> list = pdao.getProductsByCategory(cate);
		showProducts(list);
	}

	private void getProductsById() {
		System.out.print("Enter the ID you are looking for: ");
		int id = sc.nextInt();
		ArrayList<ProductBean> list = pdao.getProductById(id);
		showProducts(list);
	}

	private void showProducts(ArrayList<ProductBean> list) {
		System.out.println("Id\tName\tStock\tprice\tCategory\tInputDate");
		for(ProductBean pb : list) {
			System.out.println(pb.getId()+"\t"+pb.getName()+"\t"+pb.getStock()+"\t"+pb.getPrice()+"\t"+pb.getCategory()+"\t"+pb.getInputdate());
		}		
	}

	public static void main(String[] args) {

		new ProductMain();
		
	
	}

}
