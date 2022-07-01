package it.polimi.ingsw.model;

/**
 * value and movements for every assistant card
 */
public enum Assistant {
    TIGER(1,1),
    OSTRICH(2,1),
    ARCHER_CAT(3,2),
    EAGLE(4,2),
    FOX(5,3),
    SNAKE(6,3),
    OCTOPUS(7,4),
    DOG(8,4),
    ELEPHANT(9,5),
    TURTLE(10,5);


    private final int value;
    private final int movements;


    Assistant(int value, int movements){
        this.value = value;
        this.movements = movements;
    }

    public int getValue(){
        return this.value;
    }

    public int getMovements(){
        return this.movements;
    }

}