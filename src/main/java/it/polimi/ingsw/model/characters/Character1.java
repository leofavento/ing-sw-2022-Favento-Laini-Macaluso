package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Character;
import it.polimi.ingsw.model.Student;
import java.util.ArrayList;


//Characters that affect Students

public class Character1 extends Character{

    private ArrayList<Student> students;
    private Type2 name;


    public Character1(Type2 t){
        this.name=t;
        setInitialCost(t.getCost());
        students=new ArrayList<>();

    }

    public void addStudent(Student s){
        students.add(s);
    }

    public Student takeStudent(int n){
        Student t = students.get(n);
        students.remove(n);
        return t;
    }

}
