import java.time.LocalDate;
import java.util.Objects;

public class Student implements Comparable<Student>
{
    private String name;
    private LocalDate dateOfBirth;
    private String details;

    public Student() {
    }

    public Student(String name, LocalDate dateOfBirth, String details) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.details = details;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getName() { return name; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }

    public String getDetails() { return details; }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Student) &&
                this.name.equals(((Student) o).getName())
                && this.dateOfBirth.isEqual(((Student) o).getDateOfBirth());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dateOfBirth);
    }

    @Override
    public String toString() {
        return name + " | " + dateOfBirth + " | " + details;
    }

    @Override
    public int compareTo(Student o) {
        int comp = this.name.compareTo(o.getName());
        return comp == 0 ? this.dateOfBirth.compareTo(o.getDateOfBirth()) : comp;
    }
}
