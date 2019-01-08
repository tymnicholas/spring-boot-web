package springboot.spring_boot_web.model;

public class User {
	public String getName() {
        return name;
    }
    public void setname(String name) {
        this.name = name;
    }
    public float getSalary() {
        return salary;
    }
    public void setSalary(float salary) {
        this.salary = salary;
    }

    private String name;
    private float salary;
 
    public User(){
    }
    public User(String name, float salary){
        this.name = name;
        this.salary = salary;
    }
	
}
