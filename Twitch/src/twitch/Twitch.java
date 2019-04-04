/*
 * 
 * Program Name: Twitch
 * Version: 1.0
 * Environment: Windows 10
 * Developed under Netbeans IDE 8.0.2
 * This program is a simple console based game.
 * The user is to begin with the option of adding or removing and selecting a bird that they will play with.
 * Users have the options to sort the birds by anger level or by name alphabetically.
 * Once selected the user goes on an adventure with the aim to land within an unknown randomly generated range on a line.
 * A score is based on number of user inputs to find the range. A lower number of inputs to complete the task is the goal.
 * This program extends the Adventure3.java program supplied to students by TAFE NSW
 * If you wish to distribute this program for any commercial purpose please contact its author.
 * Contact: trevsstuff@hotmail.com
 * 
 */
package twitch;

import java.util.ArrayList;
import java.util.Scanner;
import static twitch.Twitch.Mode.SELECTING;
import java.util.Comparator;
import java.util.Collections;

/**
 *
 * @author trevs
 */
public class Twitch {
   
    private Bird selection; // Holds the bird instance after selection is made
    private int gameCounter; // counts the number of user inputs
    
    // Sets the mode that the game state is in determines what the user input should do and what menu to display.
    Mode Mode;

    public enum Mode {

        SELECTING, // Shows a selection menu
        GAME,      // shows the playing menu
        ADDING,    // Prompts user to input new bird details
        SORTING
    }

    public Mode getMode() {
        return Mode;
    }

    public void setMode(Mode s) {
        Mode = s;
        System.out.println("     MODE : " + Mode);
    }
    // Sets up the instances of the bird objects, creates an array of the bird objects
    public Bird setUp(Mode m) {

        System.out.println("Birds sorted at random:");
        Bird furiousFred = new Bird("Furiuos Fred", 0, 0, 70, 0, false);
        Bird chilledCharlie = new Bird("Chilled Charlie", 0, 0, 20, 0, true);
        Bird erinEagle = new Bird("Erin Eagle", 0, 0, 30, 0, true);
        Bird berthaBinChicken = new Bird("Bertha Bin-chicken", 1, 2, 90, 3, false);
        ArrayList<Bird> birdArray = new ArrayList<>();
        birdArray.add(erinEagle);
        birdArray.add(berthaBinChicken);
        birdArray.add(furiousFred);
        birdArray.add(chilledCharlie);

        // User menu for sorting, selecting, adding and deleting instances of the Bird objects
        while (Mode == SELECTING) {

            showBirds(birdArray);

            System.out.println("Enter the bird ID to select.");
            System.out.println("Enter l to sort the birds by anger level.");
            System.out.println("Enter s to sort the birds by name alphabetically.");
            System.out.println("Enter r to select a random bird.");
            System.out.println("Enter a to add new birds.");
            System.out.println("Enter d to delete birds.");
            System.out.println("Enter q to quit.");

            Scanner myInput = new Scanner(System.in);

            // Determine if the input is, or is not an integer. Selects game options, selects Bird by users numeric input
            if (!myInput.hasNextInt()) {
                // Convert input to char
                String inputString = myInput.nextLine();
                char c = inputString.charAt(0);

                switch (c) {
                    case 'r': // Select a random bird
                        System.out.println("Select a random index");
                        // determine the range for the random input
                        int max;
                        int min;
                        min = 0;
                        max = birdArray.size();
                        int randomSelect = (int) (Math.random() * max + min);

                        
                        // Select the bird randomly
                        selection = birdArray.get(randomSelect);
                        // Set the direction
                        selection.setDirection(1);
                        // Switch to playing the game mode
                        setMode(Mode.GAME);
                        // output the selected bird
                        System.out.println(selection.getName());
                        System.out.println("Was Selected for you.");

                        break;
                    case 's':

                        setMode(Mode.SORTING);  // enter sorting mode, alphabetically by name.
                        System.out.println("Sort the names");
                        
                        sortBirdsAlphabetically(birdArray, Mode); // Sort alphabetically
                        setMode(Mode.SELECTING); //Change game mode back to selecting
                        showBirds(birdArray); // Show results of sort

                        break;
                        
                   
                        case 'q': // Quit the program
                            System.out.println("Good bye!");
                            System.exit(0);
                    
                    break;
                       

                    case 'a': // Adding new birds
                        System.out.println("Add your own bird");
                        setMode(Mode.ADDING); // Change mode
                        addBird(birdArray); // Method to add birds
                        setMode(Mode.SELECTING); // Change mode back
                        break;

                    case 'd': 
                        showBirds(birdArray); // Show the list for deleting
                        System.out.println("Enter the id number of the bird you wish to destroy or r to return.");
                        Scanner input = new Scanner(System.in);
                            // Check if input is numeric
                        if (input.hasNextInt()) {
                            // convert to string
                            String inputCon = input.nextLine();
                            int deleteKey;
                            // convert to integer
                            deleteKey = Integer.parseInt(inputCon);
                            // Check if user input falls within the array size
                            if (deleteKey <= birdArray.size() - 1) {
                                System.out.println("You deleted: " + 
                                        birdArray.get(deleteKey).getName());
                                // Remove index
                                birdArray.remove(deleteKey);

                            } else {
                                // Catch out of bounds inputs
                                System.out.println("Please check your input.");
                            }

                            // catch non numeric inputs
                        } else {
                            System.out.println("Please enter a number.");
                        }
                        // Change mode 
                        setMode(Mode.SELECTING);
                        break;
                        
                    case 'l': // Sort birds by anger levels and show new list
                        sortBirdsAnger(birdArray, Mode);
                        showBirds(birdArray);
                        break;

                    default: // Catch any erronious inputs
                        System.out.println("We dont recognise that input : ");
                }

                System.out.println("You entered : " + inputString);

            } else // Convert mumeric string input to int
            {
                String inputString = myInput.nextLine();
                int intKey;
                intKey = Integer.parseInt(inputString);

                // Check input falls in the correct index range
                if (birdArray.size() - 1 >= intKey) {
                    setMode(Mode.GAME);

                    System.out.println("You Selected : ");
                    System.out.println(birdArray.get(intKey).getName());
                    // Return the selected bird
                    selection = birdArray.get(intKey);
                    selection.setDirection(1);

                } else {
                    // catch any out of index selections
                    System.out.println("Check the id again : " + inputString);
                }
            }

        }
        return selection;
    }
    // Sort birds by anger level
    ArrayList<Bird> sortBirdsAnger(ArrayList<Bird> birdArray, Mode m) {
        
        Collections.sort(birdArray, new Comparator<Bird>() {
            
            @Override
            public int compare(Bird lhs, Bird rhs) {
                if (lhs.getAnger() == rhs.getAnger()) {
                    return 0;
                } else if (lhs.getAnger() < rhs.getAnger()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        return birdArray;
    }
    // Sort birds by alphabet
    ArrayList<Bird> sortBirdsAlphabetically(ArrayList<Bird> birdArray, Mode m) {
        if (m == Mode.SORTING) {
            Collections.sort(birdArray, new Comparator<Bird>() {
                @Override
                public int compare(Bird lhs, Bird rhs) {
                    return lhs.getName().compareTo(rhs.getName());
                }
            });

        }
        return birdArray;
    }
    // Instantiates a new bird and adds it to the array list 
    Bird addBird(ArrayList<Bird> b) {
        Bird userBird;
        
        System.out.println("Type your Bird name and hit Enter : ");
        Scanner usrAddName = new Scanner(System.in);
        String name = usrAddName.nextLine();

        System.out.println("Type your Birds anger level 1-100 and hit Enter : ");
        Scanner usrAddAnger = new Scanner(System.in);
        
        // validate input for integer type
        if(usrAddAnger.hasNextInt()){
            
        String tempAnger = usrAddAnger.nextLine();
        int anger = Integer.parseInt(tempAnger);
        
        // validate integer is within range 
        if(anger <= 100){
            // Construct new instance 
              userBird = new Bird(name, 0, 0, anger, 0, false);
              // add to Array
                b.add(userBird);
                System.out.println(userBird.getName());
            return userBird;
           
        }else{
            System.out.println("Your anger is too damn high! Enter an integer lower than 100 !");
        }
        
        }else{
            System.out.println("Check you have entered an integer. ");
            
        }
        

        
      return null;
        
    }
    // Shows details of all of the birds in the array list
    static void showBirds(ArrayList<Bird> b) {
        int i;
        String birdName;
        int birdAnger;
        boolean birdFlight;
        for (i = 0; i < b.size(); i++) {
            birdName = b.get(i).getName();
            birdAnger = b.get(i).getAnger();
            birdFlight = b.get(i).flight;
            System.out.println("------------ Bird ID " + b.indexOf(b.get(i)) + 
                    " ------------");
            System.out.println("Name : " + birdName);
            System.out.println("Anger : " + birdAnger);
            System.out.println("Can fly : " + birdFlight);

            //Print a break line:
            if ((b.size() - 1) == b.indexOf(b.get(i))) {
                System.out.println("-----------------------------------");
            }
        }

    }

    public static void main(String[] args) {
        // Creates a new instance of twitch class
        Twitch myAdventure = new Twitch();
        myAdventure.startAdventure();

        
    }
 // Counts the number of user inputs
    public int getGameCounter() {
        return gameCounter;
    }

    public void setGameCounter(int g) {
        gameCounter = g;
    }


    // Sets up the game and begins to play
    public void startAdventure() {
        setMode(Mode.SELECTING);
        setUp(Mode);
        playGame(Mode);

    }
    // Sets up the range user must find prints out game options
    public void playGame(Mode m) {
        int low = (int) (Math.random() * - 400 + 400);
        // Modify the high range according to the anger levels
        int high = low + selection.getAnger() / 2 ;
        
        // The following is for testing the program. 
        // It shows the low and high points of the range the user must find to complete the game.
        // This is so testers can move to the target range to determine if the game ends correctly.
        // Un-comment the next 2 lines for testing
            // System.out.println("To complete the game end the turn with the bird between these numbers: ");
            //  System.out.println("Low : " + low + " High : " + high);
        // End test
        
        // Outputs the GAME menu 
        while (Mode == Mode.GAME) {

            System.out.println("m move");
            System.out.println("a accelerate");
            System.out.println("b brake");
            System.out.println("h home");
            System.out.println("t turn around");
            System.out.println("a get angry");
            System.out.println("f flight");
            System.out.println("q quit");
           
            // Apply methods according to usr input
            Scanner playInput = new Scanner(System.in);
            String playKey;
            playKey = playInput.next();
            switch (playKey) {
                case "a":
                    selection.accelerate();
                    break;

                case "b":
                    selection.brake();
                    break;
                case "h":
                    selection.home();
                    break;
                case "t":
                    selection.turn();
                    break;
                case "m":
                case "M":
                    selection.move(low, high);
                    break;
                case "s":
                    selection.stop();
                    break;
                case "q":
                    System.exit(0);
                    break;
                default:
                    System.out.println("I didn't recognise that input");

            }

            selection.report();

        }
    }
    // Creates the animal class
    public class animal {

        private String name;
        private int position;
        private int speed;
        private int direction;

        public void animal(String n, int p, int s, int d) {
            name = n;
            position = p;
            speed = s;
            direction = d;
        }

        public String getName() {
            return name;

        }

        public int getPosition() {
            return position;
        }

        public int getSpeed() {
            return speed;
        }

        public int getDirection() {
            return direction;
        }

        public void setName(String n) {
            name = n;
        }

        public void setPosition(int p) {
            position = p;
        }

        public void setSpeed(int s) {
            speed = s;
        }

        public void setDirection(int d) {
            direction = d;
        }

        public void home() {
            setPosition(0);
        }
            // Calculates and sets the speed, position and direction the user is travelling
            // Determines if the user has found the correct range
        public void move(int low, int high) {
            
            setPosition(getPosition() + getSpeed() * getDirection());
            int tempPosition = getPosition();
            setGameCounter(gameCounter++);
            
            if (tempPosition > low && tempPosition < high) {

                System.out.println(" ....^^.... ");
                System.out.println("|          |");
                System.out.println("|    BIN   |");
                System.out.println("|          |");
                System.out.println("|          |");
                System.out.println("|-        -|");
                System.out.println("|-        -|");
                System.out.println("------------");

                System.out.println("You Found the bin!");
                System.out.println("You took : " + getGameCounter() + " inputs.");
                System.out.println("Try to beat your record.");
                System.exit(0);
            } else {

                System.out.println("Bin is not here!");

            }
        }
        // Reports the game details to the console
        public void report() {

            System.out.println("Selected Bird " + getName());
            System.out.println(getName());
            System.out.println("Position: " + selection.getPosition());
            System.out.println("Speed: " + getSpeed());
            System.out.println("Direction: " + selection.getDirection());
            System.out.println("-----------------------");
            System.out.println("Number of Turns : " + getGameCounter());

        }

        public void accelerate() {
            setSpeed(10 + getSpeed());
        }

        public void brake() {

            setSpeed(getSpeed() - 10);

        }

        public void turn() {
            setDirection(getDirection() * -1);
        }

        public void stop() {
            setSpeed(0);
        }
    }

    // Bird class
    public class Bird extends animal {

        private int anger;
        private boolean flight;

        Bird(String n, int p, int s, int a, int h, boolean f) {

            setName(n);
            setPosition(p);
            setSpeed(s);
            setAnger(a);
            setFlight(f);
        }

        public void setAnger(int a) {
            anger = a;
        }

        public int getAnger() {
            return anger;
        }

        public void setFlight(boolean f) {
            flight = f;
        }

        public boolean getflight() {
            return flight;
        }
        // Overrides the animal class report method
        @Override
        public void report() {
            gameCounter = gameCounter + 1;
            System.out.println("Name : " + getName());
            System.out.println("Position: " + selection.getPosition());
            System.out.println("Speed: " + getSpeed());
            System.out.println("Direction: " + selection.getDirection());
            System.out.println("Anger levels " + anger);
            System.out.println("Number of Turns : " + getGameCounter());
            System.out.println("-----------------------");

        }

    }

}
