package main.world;

import java.util.*;

import main.organisms.Organism;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WorldVisual extends JFrame
{
    private final int rows;
    private final int cols;

    private final World world;
    private final OrganismFactory factory;

    private final JButton[][] buttons;
    private JButton saveButton;
    private JButton nextTurnButton;

    private JButton upButton;
    private JButton downButton;
    private JButton leftButton;
    private JButton rightButton;

    private JButton abilityButton;
    private final Map<String, ImageIcon> iconCache;


    public WorldVisual(int sizeX, int sizeY, boolean loadGame, Scanner scanner)
    {
        super("World Simulation");
        this.rows = sizeY;
        this.cols = sizeX;
        this.buttons = new JButton[rows][cols];
        this.iconCache = new HashMap<>();

        this.world = new World(sizeX, sizeY);
        this.factory = new OrganismFactory(this.world);

        if(loadGame)
            this.loadGame(scanner);

        else
            this.randomizeGame();


        JPanel mainPanel = initMainPanel(initFieldPanel(), initControlPanel());

        getContentPane().add(mainPanel);
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        updateButtons();
    }


    private JPanel initFieldPanel()
    {
        JPanel fieldPanel = new JPanel(new GridLayout(this.rows, this.cols));

        for (int y = 0; y < this.rows; y++)
        {
            for (int x = 0; x < this.cols; x++)
            {
                JButton button = new JButton();
                button.addActionListener(new ButtonListener(x, y, null, this.factory));
                fieldPanel.add(button);
                this.buttons[y][x] = button;
            }
        }
        return fieldPanel;
    }


    private JPanel initControlPanel()
    {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.saveButton = new JButton("Save");
        this.nextTurnButton = new JButton("Next Turn");

        this.saveButton.addActionListener(new SaveListener());
        this.nextTurnButton.addActionListener(new NextTurnListener());

        this.initDirectionButtons();

        this.initAbilityButton();

        controlPanel.add(this.upButton);
        controlPanel.add(this.downButton);
        controlPanel.add(this.leftButton);
        controlPanel.add(this.rightButton);

        controlPanel.add(this.abilityButton);

        controlPanel.add(this.saveButton);
        controlPanel.add(this.nextTurnButton);

        return controlPanel;
    }


    private JPanel initMainPanel(JPanel fieldPanel, JPanel controlPanel)
    {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20)); // Add border
        mainPanel.add(fieldPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return mainPanel;
    }


    private void initDirectionButtons()
    {
        this.upButton = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../../assets/upArrowKey.png"))));
        this.downButton = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../../assets/downArrowKey.png"))));
        this.leftButton = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../../assets/leftArrowKey.png"))));
        this.rightButton = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../../assets/rightArrowKey.png"))));

        this.upButton.addActionListener(new ArrowButtonListener('T'));
        this.downButton.addActionListener(new ArrowButtonListener('B'));
        this.leftButton.addActionListener(new ArrowButtonListener('L'));
        this.rightButton.addActionListener(new ArrowButtonListener('R'));
    }


    private void initAbilityButton()
    {
        this.abilityButton = new JButton();
        this.abilityButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("../../assets/powerup.png"))));
        this.abilityButton.addActionListener(new AbilityListener());
    }


    private class ButtonListener implements ActionListener
    {
        private final int x;
        private final int y;

        private final Organism organism;
        private final OrganismFactory factory;

        public ButtonListener(int x, int y, Organism organism, OrganismFactory factory)
        {
            this.x = x;
            this.y = y;

            this.organism = organism;
            this.factory = factory;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(this.organism != null)
                this.organism.clicked();

            else
            {
                String[] options = {"Wolf", "Sheep", "Fox", "Turtle", "Antelope",
                        "Grass", "Dandelion", "Guarana", "Nightshade", "SosnowskisHogweed"};
                String selectedValue = (String) JOptionPane.showInputDialog(
                        null,
                        "Choose an organism to create:",
                        "Organism Selection",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                );

                if (selectedValue != null)
                {
                    factory.createOrganism(selectedValue, this.x, this.y, 0, -1, 0, 0);
                    world.updatePhysicalLayer();
                    updateButtons();
                }
            }
        }
    }


    private class SaveListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            world.save();
        }
    }


    private class NextTurnListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            world.setHumanDirection('N');

            System.out.println("NEXT TURN");
            world.turn();
            updateButtons();
        }
    }


    private class ArrowButtonListener implements ActionListener
    {
        private final char direction;

        public ArrowButtonListener(char direction)
        {
            this.direction = direction;
        }


        @Override
        public void actionPerformed(ActionEvent e)
        {
            world.setHumanDirection(this.direction);

            System.out.println("NEXT TURN");
            world.turn();
            updateButtons();
        }
    }


    private class AbilityListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            world.setAbility();
        }
    }


    private void updateButtons()
    {
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                JButton button = buttons[i][j];

                ActionListener[] listeners = button.getActionListeners();
                for (ActionListener listener : listeners)
                {
                    button.removeActionListener(listener);
                }

                Organism fieldOrganism = world.getOrganismManager().getOrganismFromXY(j, i);

                if (fieldOrganism == null)
                    button.setIcon(getCachedIcon("../../assets/placeholder.png", button.getWidth(), button.getHeight()));

                else
                    button.setIcon(getCachedIcon(fieldOrganism.getIconPath(), button.getWidth(), button.getHeight()));

                button.addActionListener(new ButtonListener(j, i, fieldOrganism, this.factory));

                this.buttons[i][j] = button;
            }
        }
    }


    private ImageIcon getCachedIcon(String iconPath, int width, int height)
    {
        String cacheKey = String.format("%s-%d-%d", iconPath, width, height);

        if (!this.iconCache.containsKey(cacheKey))
        {
            ImageIcon originalIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(iconPath)));
            this.iconCache.put(cacheKey, resizeIcon(originalIcon, width, height));
        }

        return this.iconCache.get(cacheKey);
    }


    private ImageIcon resizeIcon(ImageIcon icon, int width, int height)
    {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }



    private void loadGame(Scanner scanner)
    {
        while (scanner.hasNextLine())
        {
            String line = scanner.nextLine().trim();

            if (line.isEmpty())
                continue;

            Scanner lineScanner = new Scanner(line);
            String name = lineScanner.next();
            int positionX = lineScanner.nextInt();
            int positionY = lineScanner.nextInt();
            int age = lineScanner.nextInt();
            int strength = lineScanner.nextInt();//??
            int abilityCd = 0;
            int abilityDur = 0;

            if (lineScanner.hasNextInt())
                abilityCd = lineScanner.nextInt();

            if (lineScanner.hasNextInt())
                abilityDur = lineScanner.nextInt();

            this.factory.createOrganism(name, positionX, positionY, age, strength, abilityCd, abilityDur);
        }
    }


    private void randomizeGame()
    {
        Random random = new Random();
        int numberOfOrganisms = (int) (0.25 * (this.rows * this.cols));

        this.factory.createOrganism("Human", 0, 0, 0, -1, 0, 0);
        this.world.updatePhysicalLayer();

        for (int i = 0; i < numberOfOrganisms; i++)
        {
            int x, y;
            do
            {
                x = random.nextInt(this.cols);
                y = random.nextInt(this.rows);
            } while (this.world.getPhysicalLayer(x, y));

            this.factory.createRandom(x, y);
            this.world.updatePhysicalLayer();
        }
    }
}