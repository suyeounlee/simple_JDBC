import java.util.ArrayList;
import java.util.Scanner;

public class PersonMain {
	PersonDao pdao = new PersonDao();

	Scanner sc = new Scanner(System.in);

	PersonMain() {
		System.out.println("PersonMain() Constructor");
		init();

	}

	public void init() {
		// 
		System.out.println("init");

		while(true) {
			System.out.println("Select choice");
			System.out.println("1. Display all");
			System.out.println("2. Display by name");
			System.out.println("3. Update");
			System.out.println("4. Delete");
			System.out.println("5. Add");
			System.out.println("6. Exit program");
			System.out.print(">> enter a number: ");

			int menu = sc.nextInt();

			switch(menu) {
			case 1: 
				ArrayList<PersonBean> list = pdao.getAllPerson();
				showPerson(list);
				break;
			case 2: 
				findGender();
				break;
			case 3: 
				updateData();
				break;
			case 4: 
				deleteData();
				break;
			case 5: 
				insertData();
				break;
			case 6:
				System.out.println("Program exits.");
				System.exit(0);
			default: System.out.println("Enter only 1~6 number");
			}
		}
	} //init

	private void deleteData() {
		System.out.println("Enter the number you want to delete");
		int num = sc.nextInt();
		
		pdao.deleteData(num);
	}

	private void updateData() {
		System.out.println("Enter the number you want to update");
		int num = sc.nextInt();
		
		System.out.print("Enter new name: ");
		String name = sc.next();
		System.out.print("Enter new age: ");
		int age = sc.nextInt();
		System.out.print("Enter new gender: ");
		String gender = sc.next();
		
		PersonBean bean = new PersonBean(num,name,age,gender);
		pdao.updateData(bean);
	}

	private void insertData() {
		System.out.println("Num will be entered by sequence.");
		System.out.print("Enter name: ");
		String name = sc.next();
		System.out.print("Enter age: ");
		int age = sc.nextInt();
		System.out.print("Enter gender: ");
		String gender = sc.next();
		
		PersonBean bean = new PersonBean(0,name,age,gender);
		pdao.insertData(bean);
	}

	public void findGender() {
		System.out.println("Enter a gender to find: ");
		String gender = sc.next();
		ArrayList<PersonBean> list = pdao.findGender(gender);
		showPerson(list); //only Gender

	}
	public void showPerson(ArrayList<PersonBean> list) {
		System.out.println("num\tname\tage\tgender");

		for(int i=0; i<list.size(); i++) { //for(PersonBean pb : list) 
			PersonBean pb = list.get(i);
			String result = pb.getNum()+"\t"+pb.getName()+"\t"+pb.getAge()+"\t"+pb.getGender();
			System.out.println(result);
			//System.out.println(list.get(i).getNum()  + "\t" + list.get(i).getName() + "\t" + list.get(i).getAge() + "\t" + list.get(i).getGender());
		}
	}//show

	public static void main(String[] args) {

		new PersonMain();

	}

}
