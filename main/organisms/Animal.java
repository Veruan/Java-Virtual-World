package main.organisms;

public abstract class Animal extends Organism
{
    protected boolean movement;
    protected boolean is_breeding;
    protected boolean attack_success;

    @Override
    public void action()
    {
        if(this.dead() || ++this.age <= 1 || this.is_breeding)
            return;

        int[] moves = this.randomizeMovement();
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
    }


    @Override
    public void collision(Organism other)
    {
        if(other == null)
            return;

        if(this.dead() || other.dead())
            return;

        if(this.getClass() == other.getClass())
        {
            this.movement = false;

            if((this.age > 2) || (other.getAge() > 2))
            {
                int[] result = this.myWorld.findEmptyField(this.positionX, this.positionY);

                int x = result[0], y = result[1];

                if((x != -1) && (y != -1))
                {
                    this.breeding(x, y);
                    ((Animal) other).setBreeding(true);
                    this.myWorld.addToLogs(this.name + " is mating\n");
                }

                else
                    this.myWorld.addToLogs(this.name + " has no place for mating\n");
            }
        }

        else
        {
            this.myWorld.addToLogs(this.name + " attacks " + other.getName() + "\n");

            other.attacked(this);

            if(!this.attack_success)
                return;

            int outcome = this.strength - other.getStrength();

            if(outcome >= 0)
                other.kill();

            else
                this.kill();
        }
    }


    @Override
    public void attacked(Organism attacker)
    {
        if (attacker instanceof Animal animalAttacker)
        {
            animalAttacker.setAttack(true);
        }
    }


    public abstract void clicked();


    public abstract void breeding(int x, int y);


    public void move(int x, int y)
    {
        this.positionX = x;
        this.positionY = y;

        this.myWorld.addToLogs(this.name + " moves at " + this.positionX + ", " + this.positionY + "\n");
    }


    public void setMovement(boolean movement)
    {
        this.movement = movement;
    }


    public void setBreeding(boolean breeding)
    {
        this.is_breeding = breeding;
    }


    public void setAttack(boolean attack_success)
    {
        this.attack_success = attack_success;
    }


    public void powerUp(int value)
    {
        this.strength += value;
    }
}
