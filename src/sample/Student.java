package sample;

public class Student {
    private final String name;
    private final String city;
    private final Integer id;

    public Student(Integer id, String name, String city) {
        this.name = name;
        this.city = city;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public Integer getId() {
        return id;
    }
}
