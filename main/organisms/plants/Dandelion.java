package main.organisms.plants;

import main.organisms.Plant;
import main.world.World;

public class Dandelion extends Plant
{

    private int propagationCount;


    public Dandelion(int x, int y, World world)
    {
        this.name = "Dandelion";
        this.iconPath = "../../assets/dandelion.png";

        this.positionX = x;
        this.positionY = y;

        this.initiative = 0;
        this.age = 0;

        this.strength = 0;

        this.isDead = false;

        this.myWorld = world;

        this.propagationPercentage = 5;

        this.propagationCount = 0;
    }


    @Override
    public void action()
    {
        if(this.dead() || ++this.age <= 1)
            return;

        for(int i = 0; i < 3; i++)
        {
            int number = initRandom(0, 100);

            if (number < this.propagationPercentage)
            {
                int[] result = this.myWorld.findEmptyField(this.positionX, this.positionY);

                int x = result[0], y = result[1];

                if ((x != -1) && (y != -1))
                {
                    this.propagation(x, y);
                }
            }
        }

        if(this.propagationCount != 0)
        {
            this.myWorld.addToLogs(this.name + " propagated " + this.propagationCount + " times\n");
            this.propagationCount = 0;
        }
    }


    @Override
    public void propagation(int x, int y)
    {
        this.myWorld.getOrganismManager().addOrganism(new Dandelion(x, y, this.myWorld));
    }
}
