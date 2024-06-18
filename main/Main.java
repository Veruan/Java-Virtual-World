package main;

import main.world.WorldVisual;
import javax.swing.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws FileNotFoundException
    {
        boolean loadGame = false;
        int sizeX = 0;
        int sizeY = 0;

        Scanner scanner = new Scanner(new File("save.txt"));

        int choice = JOptionPane.showConfirmDialog(null, "Do you want to load the game?", "Load Game", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION)
        {
            loadGame = true;

            if (scanner.hasNextInt())
                sizeX = scanner.nextInt();

            if (scanner.hasNextInt())
                sizeY = scanner.nextInt();
        }

        else
        {
            String inputX = JOptionPane.showInputDialog(null, "Enter the size of the world (X dimension):");
            String inputY = JOptionPane.showInputDialog(null, "Enter the size of the world (Y dimension):");

            try
            {
                sizeX = Integer.parseInt(inputX);
                sizeY = Integer.parseInt(inputY);
            }

            catch (NumberFormatException e)
            {
                JOptionPane.showMessageDialog(null, "Invalid input. Defaulting to size 10x10.");
                sizeX = 10;
                sizeY = 10;
            }
        }

        WorldVisual worldVisual = new WorldVisual(sizeX, sizeY, loadGame, scanner);
    }
}