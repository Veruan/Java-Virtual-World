package main.organisms.plants;

import main.organisms.Animal;
import main.organisms.Organism;
import main.organisms.Plant;
import main.world.World;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SosnowskisHogweed extends Plant
{

    public SosnowskisHogweed(int x, int y, World world)
    {
        this.name = "SosnowskisHogweed";
        this.iconPath = "../../assets/hogweed.png";

        this.positionX = x;
        this.positionY = y;

        this.initiative = 0;
        this.age = 0;

        this.strength = 10;

        this.isDead = false;

        this.myWorld = world;

        this.propagationPercentage = 30;
    }


    @Override
    public void action()
    {
        if(this.dead() || ++this.age <= 1)
            return;

        this.exterminateNeighbors();

        int number = initRandom(0, 100);

        if (number < this.propagationPercentage)
        {
            int[] result = this.myWorld.findEmptyField(this.positionX, this.positionY);

            int x = result[0], y = result[1];

            if((x != -1) && (y != -1))
            {
                this.propagation(x, y);
                this.myWorld.addToLogs("Sosnowski's Hogweed is propagating\n");
            }
        }
    }


    @Override
    public void propagation(int x, int y)
    {
        this.myWorld.getOrganismManager().addOrganism(new SosnowskisHogweed(x, y, this.myWorld));
    }


    public void exterminateNeighbors()
    {
        int x = this.positionX, y = this.positionY;

        // Define the offsets for neighboring cells
        List<int[]> offsets = new ArrayList<>();
        offsets.add(new int[]{0, -1});
        offsets.add(new int[]{0, 1});
        offsets.add(new int[]{-1, 0});
        offsets.add(new int[]{1, 0});

        for (int[] offset : offsets)
        {
            int newX = x + offset[0];
            int newY = y + offset[1];

            if (newX >= 0 && newX < this.myWorld.getSizeX() && newY >= 0 && newY < this.myWorld.getSizeY())
            {
                Organism neighbor = this.myWorld.getOrganismManager().getOrganismFromXY(newX, newY);

                if (neighbor instanceof Animal)
                {
                    neighbor.kill();
                    this.myWorld.addToLogs("Sosnowski's Hogweed killed " + neighbor.getName() + "!\n");
                }
            }
        }
    }
}