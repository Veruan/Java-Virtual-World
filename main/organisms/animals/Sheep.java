package main.organisms.animals;

import main.organisms.Animal;
import main.world.World;

import javax.swing.*;
import java.util.Objects;

public class Sheep extends Animal
{

    public Sheep(int x, int y, World world)
    {
        this.name = "Sheep";
        this.iconPath = "../../assets/sheep.png";

        this.positionX = x;
        this.positionY = y;

        this.initiative = 4;
        this.age = 0;

        this.strength = 4;

        this.isDead = false;

        this.myWorld = world;

        this.movement = false;
        this.is_breeding = false;
        this.attack_success = false;
    }


    @Override
    public void clicked()
    {
        System.out.println("Mee");
    }


    @Override
    public void breeding(int x, int y)
    {
        this.myWorld.getOrganismManager().addOrganism(new Sheep(x, y, this.myWorld));
    }
}
