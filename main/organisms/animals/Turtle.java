package main.organisms.animals;

import main.organisms.*;
import main.world.World;

public class Turtle extends Animal
{

    public Turtle(int x, int y, World world)
    {
        this.name = "Turtle";
        this.iconPath = "../../assets/turtle.png";

        this.positionX = x;
        this.positionY = y;

        this.initiative = 1;
        this.age = 0;

        this.strength = 2;

        this.isDead = false;

        this.myWorld = world;
    }


    @Override
    public void attacked(Organism attacker)
    {
        if (attacker instanceof Animal animalAttacker)
        {
            if (animalAttacker.getStrength() >= 5)
                animalAttacker.setAttack(true);

            else
            {
                animalAttacker.setAttack(false);
                animalAttacker.setMovement(false);
                this.myWorld.addToLogs(this.name + " has deflected attack\n");
            }
        }
    }


    @Override
    public void clicked()
    {
        System.out.println("*silence*");
    }


    @Override
    public void breeding(int x, int y)
    {
        this.myWorld.getOrganismManager().addOrganism(new Turtle(x, y, this.myWorld));
    }


    @Override
    public void move(int x, int y)
    {
        int number = initRandom(0, 100);

        if(number < 75)
        {
            this.myWorld.addToLogs(this.name + " stays at its place\n");
            return;
        }

        this.positionX = x;
        this.positionY = y;

        this.myWorld.addToLogs(this.name + " moves at " + this.positionX + ", " + this.positionY + "\n");
    }
}