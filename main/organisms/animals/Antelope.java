package main.organisms.animals;

import main.organisms.*;
import main.world.World;

public class Antelope extends Animal
{
    public Antelope(int x, int y, World world)
    {
        this.name = "Antelope";
        this.iconPath = "../../assets/antelope.png";

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
    public void attacked(Organism attacker)
    {
        int number = initRandom(0, 1);

        if(number << 2 != 0 && attacker instanceof Animal)
        {
            ((Animal) attacker).setAttack(true);
            return;
        }

        
        int[] result = this.myWorld.findEmptyField(this.positionX, this.positionY);

        int x = result[0], y = result[1];

        if (attacker instanceof Animal)
        {
            if (x != -1 && y != -1)
            {
                ((Animal) attacker).setMovement(true);//IDK if needed
                ((Animal) attacker).setAttack(false);

                this.positionX = x;
                this.positionY = y;

                this.myWorld.addToLogs(this.name + " is running to " + x + ", " + y + "\n");
            }
        }
    }


    @Override
    public void clicked()
    {
        System.out.println("Tu juz mi sie skonczyly pomysly");
    }


    @Override
    public void breeding(int x, int y)
    {
        this.myWorld.getOrganismManager().addOrganism(new Antelope(x, y, this.myWorld));
    }


    @Override
    public int[] randomizeMovement()
    {
        int[] moves = new int[2];

        int number = initRandom(0, 8);
        int modifier = 1;

        if (number >= 5)
        {
            modifier = 2;
            number = number % 2;
        }

        int[] selectedMove = this.myWorld.moves.get(number);

        int newX = this.positionX + (selectedMove[0] * modifier);
        int newY = this.positionY + (selectedMove[1] * modifier);

        int boundedX = Math.max(0, Math.min(newX, this.myWorld.getSizeX() - 1));
        int boundedY = Math.max(0, Math.min(newY, this.myWorld.getSizeY() - 1));

        moves[0] = boundedX;
        moves[1] = boundedY;

        return moves;
    }
}