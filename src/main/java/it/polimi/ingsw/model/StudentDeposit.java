package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.StudentNotExistingException;

public interface StudentDeposit {

    void addStudent(Color color);

    void extractStudent(Color color) throws StudentNotExistingException;
}
