package main.world;

import main.organisms.Organism;

import java.util.ArrayList;
import java.io.PrintWriter;

public class OrganismManager
{
    private final ArrayList<Organism> organisms;


    public OrganismManager()
    {
        this.organisms = new ArrayList<Organism>();
    }


    private int compare(Organism a, Organism b)
    {
        if (a.getInitiative() != b.getInitiative())
            return b.getInitiative() - a.getInitiative();

        else
            return b.getAge() - a.getAge();
    }


    public int getSize()
    {
        return this.organisms.size();
    }


    public void addOrganism(Organism organism)
    {
        this.organisms.add(organism);
    }


    public void removeOrganism(Organism organism)
    {
        this.organisms.remove(organism);
    }


    public Organism getOrganismFromXY(int x, int y)
    {
        for (Organism organism : this.organisms)
        {
            if (organism.getX() == x && organism.getY() == y)
            {
                return organism;
            }
        }

        return null;
    }


    public void performActions()
    {
        this.organisms.sort(this::compare);

        for(int i = 0; i < this.organisms.size(); i++)//so no concurrency issues
        {
            this.organisms.get(i).action();
            this.organisms.get(i).getMyWorld().updatePhysicalLayer();
        }
    }


    public void clean()
    {
        this.organisms.removeIf(Organism::dead);
    }


    public void printOrganisms(PrintWriter writer)
    {
        for (Organism organism : this.organisms)
        {
            writer.print(organism.print());
        }
    }

}
