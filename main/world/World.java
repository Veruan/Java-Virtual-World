package main.world;

import java.io.*;
import java.util.*;

public class World
{
    private final int sizeX;
    private final int sizeY;

    private final OrganismManager organismManager;
    private boolean[][] physicalLayer;

    private String logs;

    public final Map<Integer, int[]> moves = new HashMap<>()
    {{
        put(0, new int[]{0, 0});
        put(1, new int[]{0, 1});
        put(2, new int[]{1, 0});
        put(3, new int[]{0, -1});
        put(4, new int[]{-1, 0});
    }};

    private final boolean[] humanDirections;
    private boolean humanAbility;


    public World(int sizeX, int sizeY)
    {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        this.organismManager = new OrganismManager();
        this.physicalLayer = new boolean[sizeY][sizeX];

        this.humanDirections = new boolean[5];

        initPhysicalLayer();
        cleanLogs();
    }

    public void initPhysicalLayer()
    {
        for (boolean[] row : this.physicalLayer)
        {
            Arrays.fill(row, false);
        }
    }


    public void updatePhysicalLayer()
    {
        for(int i = 0; i < this.sizeY; i++)
        {
            for(int j = 0; j < this.sizeX; j++)
            {
                this.physicalLayer[i][j] = this.organismManager.getOrganismFromXY(j, i) != null;
            }
        }
    }


    public void cleanLogs()
    {
        logs = "";
    }


    public void turn()
    {
        this.organismManager.performActions();
        this.organismManager.clean();
        System.out.print(logs);
        cleanLogs();

        this.humanAbility = false;
    }


    public OrganismManager getOrganismManager()
    {
        return this.organismManager;
    }


    public int[] findEmptyField(int x, int y)
    {
        int tmpX = x, tmpY = y;

        for (int i = -1; i < 2; i++)
        {
            for (int j = -1; j < 2; j++)
            {
                int[] result = ensureValid(tmpX + j, tmpY + i);
                tmpX = result[0];
                tmpY = result[1];

                if (!this.physicalLayer[tmpY][tmpX])
                    return new int[]{tmpX, tmpY};

                tmpX = x; tmpY = y;
            }
        }

        return new int[]{-1, -1};
    }


    private int[] ensureValid(int x, int y)
    {
        int newX = (x <= 0) ? 0 : Math.min(x, this.sizeX - 1);
        int newY = (y <= 0) ? 0 : Math.min(y, this.sizeY - 1);

        return new int[]{newX, newY};
    }



    public int getSizeX()
    {
        return sizeX;
    }


    public int getSizeY()
    {
        return sizeY;
    }


    public boolean getPhysicalLayer(int x, int y)
    {
        return physicalLayer[y][x];
    }


    public void addToLogs(String logs)
    {
        this.logs += logs;
    }


    public int[] getHumanDirection()
    {
        for(int index = 0; index < this.humanDirections.length; index++)
        {
            if(this.humanDirections[index])
                return this.moves.get(index);
        }

        return this.moves.get(0);
    }


    public boolean getAbility()
    {
        return this.humanAbility;
    }


    public void setHumanDirection(char direction)
    {
        Arrays.fill(this.humanDirections, false);

        switch (direction)
        {
            case 'L':
                this.humanDirections[4] = true;
                break;

            case 'R':
                this.humanDirections[2] = true;
                break;

            case 'T':
                this.humanDirections[3] = true;
                break;

            case 'B':
                this.humanDirections[1] = true;
                break;

            case 'N':
                this.humanDirections[0] = true;
                break;
        }
    }


    public void setAbility()
    {
        this.humanAbility = true;
    }


    public void save()
    {
        try (PrintWriter saveWriter = new PrintWriter(new FileWriter("save.txt", false)))
        {
            saveWriter.println(sizeX + " " + sizeY);

            this.organismManager.printOrganisms(saveWriter);

        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
