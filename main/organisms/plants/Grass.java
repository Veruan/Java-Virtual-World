package main.organisms.plants;

import main.organisms.Plant;
import main.world.World;

public class Grass extends Plant
{
    public Grass(int x, int y, World world)
    {
        this.name = "Grass";
        this.iconPath = "../../assets/grass.png";

        this.positionX = x;
        this.positionY = y;

        this.initiative = 0;
        this.age = 0;

        this.strength = 0;

        this.isDead = false;

        this.myWorld = world;

        this.propagationPercentage = 5;
    }


    @Override
    public void propagation(int x, int y)
    {
        this.myWorld.getOrganismManager().addOrganism(new Grass(x, y, this.myWorld));
    }
}
