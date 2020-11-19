package sample;

    //simple class so that I can create students
    // with the information extracted from the database
public class Student {
    private final String name;
    private final String city;
    private final Integer id;
        //I chose to use array lists for courses and grades
        //since they are extracted in the same method
        //the grades will have the same index as the course

    public Student(Integer id, String name, String city) {
        this.name = name;
        this.city = city;
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public String getName(){return name; }

    public String getCity() {return city;}


        //I override the toString() method
        //this gives me the possibility to decide what is write in my student ComboBox
    @Override
    public String toString() {
        return id+": "+name;
    }
}
