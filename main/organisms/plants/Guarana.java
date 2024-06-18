package main.organisms.plants;

import main.organisms.Animal;
import main.organisms.Organism;
import main.organisms.Plant;
import main.world.World;

public class Guarana extends Plant
{
    public Guarana(int x, int y, World world)
    {
        this.name = "Guarana";
        this.iconPath = "../../assets/guarana.png";

        this.positionX = x;
        this.positionY = y;

        this.initiative = 0;
        this.age = 0;

        this.strength = 0;

        this.isDead = false;

        this.myWorld = world;

        this.propagationPercentage = 2;
    }



    @Override
    public void attacked(Organism attacker)
    {
        if(attacker instanceof Animal)
        {
            ((Animal) attacker).setAttack(true);
            ((Animal) attacker).powerUp(5);
        }
    }


    @Override
    public void propagation(int x, int y)
    {
        this.myWorld.getOrganismManager().addOrganism(new Guarana(x, y, this.myWorld));
    }
}
