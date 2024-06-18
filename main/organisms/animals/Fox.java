package main.organisms.animals;

import main.organisms.Animal;
import main.world.World;


public class Fox extends Animal
{

    public Fox(int x, int y, World world)
    {
        this.name = "Fox";
        this.iconPath = "../../assets/fox.png";

        this.positionX = x;
        this.positionY = y;

        this.initiative = 7;
        this.age = 0;

        this.strength = 3;

        this.isDead = false;

        this.myWorld = world;

        this.movement = false;
        this.is_breeding = false;
        this.attack_success = false;
    }


    @Override
    public void action()
    {
        if(this.dead() || ++this.age <= 1 || this.is_breeding)
            return;

        int[] moves = this.randomizeMovement();
        int moveX = moves[0], moveY = moves[1];

        this.movement = moveX != this.positionX || moveY != this.positionY;

        if(this.movement && this.myWorld.getPhysicalLayer(moveX, moveY))
        {
            if(this.strength >= this.myWorld.getOrganismManager().getOrganismFromXY(moveX, moveY).getStrength())
                this.collision(this.myWorld.getOrganismManager().getOrganismFromXY(moveX, moveY));

            else
                this.movement = false;
        }

        if(this.dead())
            return;

        if(this.movement)
            this.move(moveX, moveY);

        else
            myWorld.addToLogs(this.name + " stays in place\n");
    }


    @Override
    public void clicked()
    {
        System.out.println("What does the fox say - idk");
    }


    @Override
    public void breeding(int x, int y)
    {
        this.myWorld.getOrganismManager().addOrganism(new Fox(x, y, this.myWorld));
    }
}
