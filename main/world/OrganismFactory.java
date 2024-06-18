package main.world;

import main.organisms.plants.*;
import main.organisms.animals.*;
import main.organisms.Organism;

import java.util.List;
import java.util.Random;

public class OrganismFactory
{
    private final World destinationWorld;

    public OrganismFactory(World destinationWorld)
    {
        this.destinationWorld = destinationWorld;
    }

    public void createRandom(int x, int y)
    {
        List<String> symbols = List.of("Wolf", "Sheep", "Fox", "Turtle", "Antelope",
                "Grass", "Dandelion", "Guarana", "Nightshade", "SosnowskisHogweed");

        Random random = new Random();
        String symbol = symbols.get(random.nextInt(symbols.size()));
        createOrganism(symbol, x, y, 0, -1, 0, 0);
    }


    public void createOrganism(String name, int x, int y, int age, int strength, int abilityCd, int abilityDur)
    {
        Organism newOrganism;

        switch (name)
        {
            case "Wolf":
                newOrganism = new Wolf(x, y, destinationWorld);
                break;

            case "Sheep":
                newOrganism = new Sheep(x, y, destinationWorld);
                break;

            case "Fox":
                newOrganism = new Fox(x, y, destinationWorld);
                break;

            case "Turtle":
                newOrganism = new Turtle(x, y, destinationWorld);
                break;

            case "Antelope":
                newOrganism = new Antelope(x, y, destinationWorld);
                break;

            case "Human":
                newOrganism = new Human(x, y, destinationWorld);
                Human human = (Human) newOrganism;
                human.setAbilityCooldown(abilityCd);
                human.setAbilityDuration(abilityDur);
                break;

            case "Grass":
                newOrganism = new Grass(x, y, destinationWorld);
                break;

            case "Dandelion":
                newOrganism = new Dandelion(x, y, destinationWorld);
                break;

            case "Guarana":
                newOrganism = new Guarana(x, y, destinationWorld);
                break;

            case "Nightshade":
                newOrganism = new Nightshade(x, y, destinationWorld);
                break;

            case "SosnowskisHogweed":
                newOrganism = new SosnowskisHogweed(x, y, destinationWorld);
                break;

            default:
                System.err.println("No organism with this symbol");
                return;
        }

        newOrganism.setAge(age);

        if(strength != -1 && strength != newOrganism.getStrength())
            newOrganism.setStrength(strength);

        destinationWorld.getOrganismManager().addOrganism(newOrganism);
    }
}
