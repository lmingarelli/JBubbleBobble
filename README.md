# JBubbleBobble
Remake of the 1986 JBubbleBobble game in Java

# Design Patterns

**MVC (Model-View-Controller)**

The project was developed based on the MVC architectural pattern, with three packages created to categorize the classes into these three groups.

-   **Controller Package Functions:**
    
    -   Manage the game's startup (contains the main method) and control the game loop.
    -   Map the user's keyboard inputs with the necessary logic.
    -   Manage sound effects.
    -   Handle saving and loading player information via an XML file.
    -   Manage the start of the game and essential timers.
-   **View Package Functions:**
    
    -   Manage the JFrame and JPanels where all game visuals are drawn, including size, resizing, fonts, colors, etc.
    -   Navigate between pages using on-screen buttons, operable by mouse clicks.
-   **Model Package Functions:**
    
    -   Represent (through its classes) all game objects, from fundamental (such as blocks and entities) to more complex ones (specific enemies, elemental bubbles).
    -   Create all levels and load the next levels when one is completed.
    -   Control all types of collisions between objects.
<br/>
    
**Observer-Observable**  
To ensure correct communication between the MVC components without breaking its rules, the Observer-Observable design pattern was used.  
The management classes `GameController.java` and `GameView.java` act as Observers to the `GameModel.java` class, which functions as the Observable. The Observer classes contain code that executes when the Observable class sends them a notification, passing an object as a parameter that, depending on its type, triggers different behaviors in the Observers.  
Another Observable class is `Navigator.java`, in the view package. This class handles page switching on the game window, sending notifications to its Observer `GameView.java`.
<br/>

**Singleton**  
This design pattern was used to ensure that only one instance of certain specific classes is created during execution. The classes that implement this pattern are `AudioManager.java`, `GameController.java`, `GameModel.java`, `GameView.java`, and `Navigator.java`.  
Using the Singleton pattern helps prevent accidentally creating multiple objects of these fundamental classes, which could lead to errors and inconsistencies, such as creating multiple players or multiple JFrames.
<br/>

# Design Decisions and Notes
**Controller**  
The program starts from the controller package, specifically through the main method contained in the `JBubbleBobble.java` class.  
The main method is responsible for loading the instances (single, since Singleton is used) of `GameModel.java` and `GameView.java` and starting the game loop.

`GameController.java` is the managing class of the controller package. It plays a crucial role as the link between the view and the model, ensuring that the Observer-Observable mechanism is initialized between the model and controller/view. It also manages keyboard input mapping and the class dedicated to sound effects.

`PlayersRecord.java` handles reading and writing player data from an external XML file. During the first phase of the game (the nickname entry), if a nickname is not present in the file, it is created; otherwise, its data is retrieved from the XML.  
At the end of each game, the best score is updated in real-time and is immediately visible on the user page.  
Upon exiting the window, all player data is saved to the XML.
<br/>

**View**  
The view package contains all the classes that manage the visual display of game screens and objects.

`GameView.java` is the managing class of the view package. It is responsible for initializing the JFrame on which the various game panels will be displayed and ensuring it is set up correctly (fonts, sizes, etc.).  
To manage the various pages, a general JPanel (called deck) of type `CardLayout()` was created, and the various game panels were added to the deck with identifying names inserted into an enum.  
When a screen change button is pressed, the singleton instance of the `Navigator.java` class is called, which will notify `GameView.java` of the window change to be made. The enum value indicating the deck page to be displayed will be passed as an argument.

The various panels were created with separate classes, which extend the JPanel class. The most commonly used layouts are GridLayout and GridBagLayout.

All page content was displayed through Java Swing classes (JLabel, JButton, etc.), except for the game panel (`GamePanel.java`). The components of the game panel are redrawn (for each frame) on the panel using coordinates.
<br/>

**Model**  
The model package contains all the classes that represent game objects.

`GameModel.java` is the managing class of this package, acting as the link between all the classes in the model package. It handles starting the game, winning, game over, and creating and loading levels.

Each level is an object of the `Level.java` class (which extends `Grid.java`). This class manages the movement and collision of enemies, bonuses (items that give points), and flames (from the fire elemental bubble). It also controls (using the abstract `CollisionManager.java` class) collisions between players, enemies, and bubbles.  
The game grid is managed by `Grid.java` through a matrix of Block objects.

The abstract class `LevelsManager.java` and its static methods are called by `GameModel.java` to create the eight different objects that represent the eight playable levels. Each method handles the placement of blocks and specific enemies for the current level.

`Bonus.java` manages the creation and movement of bonus items, which increase the player's score when collected. Bonuses appear when the player kills an enemy, at the point of its death. The class decides which of the nine bonus objects to spawn based on the value of a random integer variable:

-   Sushi: 500 points (30%)
-   Onion: 500 points (30%)
-   Pretzel: 1000 points (15%)
-   Hamburger: 1500 points (10%)
-   Cake: 3000 points (5%)
-   Diamond: 5000 points (4%)
-   Gem: 7000 points (3%)
-   Necklace: 8500 points (2%)
-   Treasure Chest: 10000 points (1%)

(If the "luck" power-up is collected, the probabilities change. In order: 5%, 5%, 5%, 5%, 15%, 15%, 15%, 15%, 20%)

`PowerUp.java` manages the creation and movement of power-up items, which give the player temporary powers and bonuses when collected. Each power-up has a different duration, and some are instant. `GameController.java` creates a power-up every X seconds, where X is a random value between 30 and 60 seconds. Only one power-up can exist at a time during the game. The class decides which of the ten power-up objects to spawn based on the value of a random integer variable. All power-ups have a 10% chance of appearing. The implemented power-ups are:

-   Health (red heart): adds a life to the player.
-   Bubble speed (blue candy): increases bubble speed for 5 seconds.
-   Bubble fire rate (yellow candy): increases bubble firing rate for 5 seconds.
-   Bubble travel (red candy): increases bubble travel distance for 5 seconds.
-   Player speed (shoes): increases player speed for 8 seconds.
-   Rage delayed (book): increases the time it takes for bubble enemies to transform into angry enemies. Applies to all enemies in the current level.
-   Luck (yellow cross): increases the chances of better bonuses dropping from enemies for 10 seconds.
-   Invulnerability (yellow heart with a monster in the center): grants the player invulnerability for 5 seconds.
-   Stop enemies (clock): immobilizes all enemies for 2 seconds.
-   One shot (bomb): instantly eliminates all enemies.

`Bubble.java` represents the bubbles shot by the player. Once spawned, they travel horizontally for a set distance. Upon reaching the maximum horizontal distance, they will start rising until they almost reach the ceiling, where they will stop and begin floating.

`BubbleManager.java` manages all currently existing bubbles in the level, handling their movement, popping, and removal.

`ElementalBubble.java` (a generalization of the `Bubble.java` class) represents elemental bubbles, which come in two types: fire and lightning. Fire bubbles, once popped, will drop a flame that spreads out on the ground, creating a flame carpet. The flames instantly kill enemies. Lightning bubbles, on the other hand, create two lightning bolts that move horizontally (in both directions). Like flames, the lightning bolts instantly kill enemies.  
Each frame has a 0.10% chance of creating an elemental bubble. Two bubbles of the same type are not allowed to exist at the same time in the level. The creation of elemental bubbles is handled by the `BubblesManager.java` class.
