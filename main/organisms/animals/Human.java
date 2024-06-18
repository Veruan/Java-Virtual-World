package main.organisms.animals;

import main.organisms.Animal;
import main.organisms.Organism;
import main.world.World;

public class Human extends Animal
{
    private int abilityDuration;
    private int abilityCooldown;

    public Human(int x, int y, World world)
    {
        this.name = "Human";
        this.iconPath = "../../assets/human.png";

        this.positionX = x;
        this.positionY = y;

        this.initiative = 4;
        this.age = 0;

        this.strength = 5;

        this.isDead = false;

        this.myWorld = world;

        this.movement = false;
        this.is_breeding = false;
        this.attack_success = false;

        this.abilityDuration = 0;
        this.abilityCooldown = 0;
    }


    @Override
    public String print()
    {
        if (this.isDead)
            return "";

        return "%s %d %d %d %d %d %d\n".formatted(this.name, this.positionX, this.positionY, this.age, this.strength, this.abilityDuration, this.abilityCooldown);
    }


    @Override
    public void action()
    {
        if(this.dead() || ++this.age <= 1)
            return;


        if (this.myWorld.getAbility() && this.abilityCooldown <= 0)
            this.special();

        int[] moves = this.humanMovement();
        int moveX = moves[0], moveY = moves[1];

        this.movement = moveX != this.positionX || moveY != this.positionY;

        if(this.movement && this.myWorld.getPhysicalLayer(moveX, moveY))
            this.collision(this.myWorld.getOrganismManager().getOrganismFromXY(moveX, moveY));

        if(this.dead())
            return;

        if(this.movement)
            this.move(moveX, moveY);

        else
            myWorld.addToLogs(this.name + " stays in place\n");

        this.abilityCooldown = (this.abilityCooldown == 0) ? 0 : --this.abilityCooldown;
        this.abilityDuration = (this.abilityDuration == 0) ? 0 : --this.abilityDuration;
    }


    @Override
    public void collision(Organism other)
    {
        if(other == null || this.dead() || other.dead())
            return;

        if(this.abilityDuration > 0 && other.getStrength() > this.strength)
            this.setMovement(false);

        else
            super.collision(other);
    }


    @Override
    public void attacked(Organism attacker)
    {
        if (attacker instanceof Animal animalAttacker)
        {
            if(this.abilityDuration > 0)
            {
                if(attacker.getStrength() >= this.strength)
                {
                    int[] result = this.myWorld.findEmptyField(this.positionX, this.positionY);

                    int x = result[0], y = result[1];

                    if((x != -1) && (y != -1))
                    {
                        animalAttacker.setMovement(true);
                        animalAttacker.setAttack(false);

                        this.positionX = x;
                        this.positionY = y;

                        this.myWorld.addToLogs(this.name + " is running to " + x + ", " + y + "\n");
                    }
                }
            }

            else
                animalAttacker.setAttack(true);
        }
    }

    @Override
    public void clicked()
    {
        System.out.println("Uga buga");
    }


    @Override
    public void breeding(int x, int y)
    {}


    public int[] humanMovement()
    {
        int[] moves = new int[2];

        int[] selectedMove = this.myWorld.getHumanDirection();

        int newX = this.positionX + selectedMove[0], newY = this.positionY + selectedMove[1];

        int boundedX = Math.max(0, Math.min(newX, this.myWorld.getSizeX() - 1));
        int boundedY = Math.max(0, Math.min(newY, this.myWorld.getSizeY() - 1));

        moves[0] = boundedX;
        moves[1] = boundedY;

        return moves;
    }


    public void special()
    {
        if(this.abilityCooldown != 0)
            return;

        this.abilityCooldown = 5;
        this.abilityDuration = 5;

        System.out.println("Human is powered up");
    }


    public void setAbilityCooldown(int abilityCooldown)
    {
        this.abilityCooldown = abilityCooldown;
    }


    public void setAbilityDuration(int abilityDuration)
    {
        this.abilityDuration = abilityDuration;
    }
}
