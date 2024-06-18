package main.organisms.animals;

import main.organisms.Animal;
import main.world.World;

public class Wolf extends Animal
{

    public Wolf(int x, int y, World world)
    {
        this.name = "Wolf";
        this.iconPath = "../../assets/wolf.png";

        this.positionX = x;
        this.positionY = y;

        this.initiative = 5;
        this.age = 0;

        this.strength = 9;

        this.isDead = false;

        this.myWorld = world;

        this.movement = false;
        this.is_breeding = false;
        this.attack_success = false;
    }


    @Override
    public void clicked()
    {
        System.out.println("Woof");
    }


    @Override
    public void breeding(int x, int y)
    {
        this.myWorld.getOrganismManager().addOrganism(new Wolf(x, y, this.myWorld));
    }
}
