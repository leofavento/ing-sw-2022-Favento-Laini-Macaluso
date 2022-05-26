package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Bag implements Serializable {

    private final ArrayList<Color> students;

    public Bag(){
        students = new ArrayList<>();
    }

    /**
     * method used to fill the bag in the setup phase
     * @param studentsNum the number of students for each color
     */
    public void refill(int studentsNum){
        for (int i=0; i < studentsNum; i++) {
            students.addAll(Arrays.asList(Color.values()));
        }
        Collections.shuffle(students);
    }

    /**
     *
     * @return the number of students left in the bag
     */
    public int getStudentsLeft() {
        return students.size();
    }

    /**
     *
     * @param student the student to add back in the bag
     */
    public void addStudent(Color student) {
        students.add(student);
        Collections.shuffle(students);
    }

    /**
     *
     * @return a random student from the bag
     */
    public Color drawStudent() {
        Color t = students.get(0);
        students.remove(0);
        return t;
    }
}
