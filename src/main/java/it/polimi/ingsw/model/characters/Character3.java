package it.polimi.ingsw.model.characters;


import it.polimi.ingsw.model.Character;

// Characters that affect influence

public class Character3 extends Character {
        private Type1 name;

        public Character3(Type1 t){
                this.name=t;
                setInitialCost(t.getCost());
        }

        }
