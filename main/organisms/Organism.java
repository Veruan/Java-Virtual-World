package main.organisms;

import main.world.World;
import java.util.Random;

public abstract class Organism
{
    protected String name;
    protected String iconPath;

    protected int positionX;
    protected int positionY;

    protected int initiative;
    protected int age;
    protected int strength;

    protected boolean isDead;

    protected World myWorld;


    public abstract void action();

    public abstract void collision(Organism other);

    public abstract void attacked(Organism attacker);

    public abstract void clicked();


    public String print()
    {
        if (this.isDead)
            return "";

        return this.name + " " + this.positionX + " " + this.positionY + " " + this.age + " " + this.strength + "\n";
    }


    public String deathInfo()
    {
        return this.name + " is brutally murdered\n";
    }


    public String getName()
    {
        return this.name;
    }


    public String getIconPath()
    {
        return this.iconPath;
    }


    public int getX()
    {
        return this.positionX;
    }


    public int getY()
    {
        return this.positionY;
    }


    public int getInitiative()
    {
        return this.initiative;
    }


    public int getAge()
    {
        return this.age;
    }


    public int getStrength()
    {
        return this.strength;
    }


    public World getMyWorld()
    {
        return this.myWorld;
    }


    public void setWorld(World world)
    {
        myWorld = world;
    }


    public void setAge(int age)
    {
        this.age = age;
    }


    public void setStrength(int strength)
    {
        this.strength = strength;
    }


    public boolean dead()
    {
        return isDead;
    }


    public void kill()
    {
        this.isDead = true;

        this.positionX = -1;
        this.positionY = -1;
        this.iconPath = "";

        this.myWorld.addToLogs(this.deathInfo());
    }


    public static int initRandom(int bottom, int top)
    {
        Random rand = new Random();
        return rand.nextInt(top - bottom + 1) + bottom;
    }


    public int[] randomizeMovement()
    {
        int[] moves = new int[2];

        int number = initRandom(0, 4);

        int[] selectedMove = this.myWorld.moves.get(number);

        int newX = this.positionX + selectedMove[0], newY = this.positionY + selectedMove[1];

        int boundedX = Math.max(0, Math.min(newX, this.myWorld.getSizeX() - 1));
        int boundedY = Math.max(0, Math.min(newY, this.myWorld.getSizeY() - 1));

        moves[0] = boundedX;
        moves[1] = boundedY;

        return moves;
    }
}
