
public class PersonBean {
	private int num;
	private String name;
	private int age;
	private String gender;
	
	public PersonBean() {
		
	}
	public PersonBean(int num, String name, int age, String gender) {
		super();
		this.num = num;
		this.name = name;
		this.age = age;
		this.gender = gender;
	
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	
	
}
