package main.organisms;

public abstract class Plant extends Organism
{
    protected int propagationPercentage;

    @Override
    public void action()
    {
        if(this.dead() || ++this.age <= 1)
            return;

        int number = initRandom(0, 100);

        if (number < this.propagationPercentage)
        {
            int[] result = this.myWorld.findEmptyField(this.positionX, this.positionY);

            int x = result[0], y = result[1];

            if((x != -1) && (y != -1))
            {
                this.propagation(x, y);
                this.myWorld.addToLogs(this.name + " is propagating\n");
            }
        }
    }


    @Override
    public void collision(Organism other)
    {

    }


    @Override
    public void attacked(Organism attacker)
    {
        if(attacker instanceof Animal)
            ((Animal) attacker).setAttack(true);
    }


    @Override
    public void clicked()
    {
        System.out.println("Khskshkshshs");
    }


    public abstract void propagation(int x, int y);


    @Override
    public String deathInfo()
    {
        return this.name + " is eaten\n";
    }
}
