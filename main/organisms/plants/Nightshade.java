package main.organisms.plants;

import main.organisms.Plant;
import main.world.World;

import javax.swing.*;
import java.util.Objects;

public class Nightshade extends Plant
{
    public Nightshade(int x, int y, World world)
    {
        this.name = "Nightshade";
        this.iconPath = "../../assets/nightshade.png";

        this.positionX = x;
        this.positionY = y;

        this.initiative = 0;
        this.age = 0;

        this.strength = 99;

        this.isDead = false;

        this.myWorld = world;

        this.propagationPercentage = 2;
    }


    @Override
    public void propagation(int x, int y)
    {
        this.myWorld.getOrganismManager().addOrganism(new Nightshade(x, y, this.myWorld));
    }
}
