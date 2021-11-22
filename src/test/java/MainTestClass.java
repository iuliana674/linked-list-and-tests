import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainTestClass {
    static LinkedListImpl<Student> studentLinkedList = new LinkedListImpl<>();

    static{
        studentLinkedList.add(new Student("St1",LocalDate.of(2008, 5, 20), "details"));
        studentLinkedList.add(new Student("St2",LocalDate.of(1999, 9, 9), "details"));
    }

    @Test
    @DisplayName("Check size")
    @Order(1)
    void checkListSize(){
        assertEquals(2, studentLinkedList.size(), "Something wrong!");
    }

    @Test
    @DisplayName("Check if empty")
    @Order(2)
    void checkIfListIsEmpty(){
        assertFalse(studentLinkedList.isEmpty(), "Linked list is empty.");
    }

    @Test
    @DisplayName("Get elements")
    @Order(3)
    void getAndCheckListElementsAtDifferentIndexes(){
        assertAll("Get element",
                () -> {
                    Throwable e = assertThrows(IndexOutOfBoundsException.class, () -> studentLinkedList.get(100));
                    assertEquals("Please check the index!", e.getMessage());
                },
                () -> assertEquals(newStudents().get(0), studentLinkedList.get(0), "Retrieved other element.")
        );
    }

    @ParameterizedTest
    @MethodSource(value = "newStudents")
    @DisplayName("Contains elements")
    @Order(4)
    void checkIfContainsCertainStudent(Student student){
        assertTrue(studentLinkedList.contains(student), "Not contained!");
    }

    @Test
    @DisplayName("Check if a element gets added")
    @Order(5)
    void checkIfElementsGetAdded(){
        Student student = otherStudents().get(0);
        assertTrue(studentLinkedList.add(student), "Some error occurred during adding");
        Throwable exception = assertThrows(IndexOutOfBoundsException.class,
                () -> studentLinkedList.add(100, student));
        assertEquals("Please check the index!", exception.getMessage());
    }

    @Test
    @DisplayName("Remove a certain object")
    @Order(6)
    void checkIfObjectGetsRemovedFromList(){
        Student newStudent = new Student("st3",
                LocalDate.of(1999, 11, 22), "details");
        studentLinkedList.add(0, newStudent);
        studentLinkedList.remove(newStudent);
        assertFalse(studentLinkedList.contains(newStudent), "Element still in list");
    }

    @Test
    @DisplayName("Iterating over list")
    @Order(7)
    void checkCorrectOrderOfIteration(){
        Iterator<Student> iterator = studentLinkedList.iterator();
        List<Student> students = newStudents();
        assertEquals(students.get(0), iterator.next(), "Wrong order of iteration!");
        assertEquals(students.get(1), iterator.next(), "Wrong order of iteration!");
    }

    @Test
    @DisplayName("Add between")
    @Order(8)
    void checkIfElementGetsAddedAtCertainPosition(){
        Student student = otherStudents().get(1);
        studentLinkedList.add(2, student);
        assertEquals(4, studentLinkedList.size(), "Element not added.");
        assertEquals(student, studentLinkedList.get(2), "Inserted at wrong position");
        studentLinkedList.add(studentLinkedList.size(), student);
        int size = studentLinkedList.size();
        assertEquals(5, size, "Element not added.");
        assertEquals(student, studentLinkedList.get(size - 1), "Inserted at wrong position");
    }

    @Test
    @DisplayName("Deleting an element")
    @Order(9)
    void checkIfElementGetsRemovedFromCertainIndex(){
        Student student = studentLinkedList.remove(0);
        assertAll("Student data",
                () -> assertEquals("St1", student.getName()),
                () -> assertEquals(LocalDate.of(2008, 5, 20), student.getDateOfBirth()),
                () -> assertEquals("details", student.getDetails())
        );
    }

    @Test
    @DisplayName("To Object array representation")
    @Order(10)
    void checkIfGetsConvertedToArray(){
        Object[] o = studentLinkedList.toArray();
        assertAll("Array representation",
                () -> assertInstanceOf(Object[].class, o),
                () -> assertEquals(o.length, studentLinkedList.size())
        );
    }

    @Test
    @DisplayName("To Student array representation")
    @Order(11)
    void checkIfGetsConvertedToStudentArray(){
        Student[] s = studentLinkedList.toArray(new Student[0]);
        assertAll("Student array representation",
                () -> assertInstanceOf(Student[].class, s),
                () -> assertEquals(s.length, studentLinkedList.size())
        );
        Student[] s1 = studentLinkedList.toArray(new Student[10]);
        assertAll("Student array representation",
                () -> assertInstanceOf(Student[].class, s),
                () -> assertEquals(s.length, studentLinkedList.size())
        );
    }

    @Test
    @DisplayName("Set at certain position")
    @Order(12)
    void checkIfStudentGetsSetAtCertainPosition(){
        Student student = new Student("st6",
                LocalDate.of(2010, 11, 30), "details");
        studentLinkedList.set(0, student);
        assertEquals(student, studentLinkedList.get(0), "Wrong insertion!");
    }

    @Test
    @DisplayName("Index of")
    @Order(13)
    void checkIndexOf(){
        Student student = otherStudents().get(0);
        assertEquals(2, studentLinkedList.indexOf(student), "Wrong index");
        assertEquals(-1, studentLinkedList.indexOf(otherStudents().get(2)), "Wrong index");
    }

    @Test
    @DisplayName("Last index of")
    @Order(14)
    void checkLastIndexOf(){
        Student student = otherStudents().get(1);
        assertEquals(3, studentLinkedList.lastIndexOf(student), "Wrong index");
        assertEquals(-1, studentLinkedList.indexOf(otherStudents().get(2)), "Wrong index");
    }

    @Test
    @DisplayName("Add all")
    @Order(15)
    void checkIfAllGetAdded(){
        studentLinkedList.addAll(newStudents());
        assertEquals(6, studentLinkedList.size(), "Wrong size!");
    }

    @Test
    @DisplayName("Get sublist")
    @Order(16)
    void checkSubListSize(){
        List<Student> students = studentLinkedList.subList(0, 6);
        assertEquals(6, students.size(), "Check sublist size!");
    }

    @Test
    @DisplayName("Get sublist")
    @Order(17)
    void checkListIterator(){
        ListIterator<Student> listIterator = studentLinkedList.listIterator();
        listIterator.next();
        assertEquals(otherStudents().get(1), listIterator.next(), "Wrong iteration order!");
    }

    @Test
    @DisplayName("Clear list")
    @Order(18)
    void checkListIteratorFromIndex(){
        ListIterator<Student> listIterator = studentLinkedList.listIterator(1);
        listIterator.next();
        assertEquals(otherStudents().get(0), listIterator.next(), "Wrong iteration order!");
    }

    @Test
    @DisplayName("Clear list")
    @Order(18)
    void checkIfGetsCleared(){
        studentLinkedList.clear();
        assertEquals(0, studentLinkedList.size(), "Clear operation failed");
    }

    static List<Student> otherStudents(){
        return List.of(
                new Student("st4", LocalDate.of(2001, 1, 1), "details"),
                new Student("st5", LocalDate.of(2010, 11, 22), "details"),
                new Student("st7", LocalDate.of(2002, 8, 3), "details")
        );
    }

    static List<Student> newStudents(){
        return List.of(
                new Student("St1", LocalDate.of(2008, 5, 20), "details"),
                new Student("St2", LocalDate.of(1999, 9, 9), "details")
        );
    }
}
