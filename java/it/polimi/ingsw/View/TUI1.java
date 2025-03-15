package it.polimi.ingsw.View;

import it.polimi.ingsw.EventHandling.*;
import it.polimi.ingsw.Listener.ViewListener;
import it.polimi.ingsw.Model.Exceptions.NoCauseException;
import it.polimi.ingsw.Model.Exceptions.SilentRuntimeException;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.PlayingCard;
import it.polimi.ingsw.Model.Resource;
import it.polimi.ingsw.Model.State;
import it.polimi.ingsw.ModelView.GameView;
import org.fusesource.jansi.Ansi;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * TUI class, is used to represent the text user interface.
 */
public class TUI1  extends UI1 {
    /**
     * Attributes of the class
     * nickname is the nickname of the player
     * clientId is the id of the client
     * game is the GameView of the game
     * scanner is the BufferedReader used to read the input from the user
     * eventreceived is the type of event received
     * creators is the list of creators of the games
     * viewListener is the listener of the view
     * creatorChosen is the creator that created the game
     * lock is the object used to synchronize the threads
     */
    String nickname;
    String clientId;
    GameView game;
    BufferedReader scanner = new BufferedReader( new InputStreamReader(System.in));
    EventType eventreceived = EventType.WAIT;
    ArrayList<String> creators;
    ViewListener viewListener;
    String creatorChosen;
    private final Object lock = new Object();

    @Override
    public ViewListener getViewListener() {
        return this.viewListener;
    }

    /**
     * Method tu run the TUI
     * @param id is the id of the client
     * @throws InterruptedException the exception is thrown when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @Override
    public void run(String id) throws InterruptedException, IOException {
        this.clientId = id;
        showGameName();
        showMenuOptions();
        showWaitingForPlayers();
        synchronized (lock) {
            while(game == null || game.getState().equals(State.WAITING)){  // added the check for "null" because with multiple threads can enter when it's still not arrived the game
                try {
                    lock.wait(); // Wait until notified
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }
        }
        showGameStarted();
        showChoosePawnColor();
        showObjectiveCards();
        showStarterCard();
        System.out.println("Waiting for other players to be ready...");
        synchronized (lock) {
            while(game == null || game.getState().equals(State.WAITINGTOPLAY)) {
                try {
                    lock.wait(); // Wait until notified
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }
        }
        playGame();

    }

    /**
     * Method to choose the pawn color
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    public void showChoosePawnColor() throws IOException {
         boolean validInput = false;
        System.out.println("Choose your pawn color: ");
        System.out.println("1: Red");
        System.out.println("2: Blue");
        System.out.println("3: Green");
        System.out.println("4: Yellow");
        System.out.println("5: Black");
        while (!validInput) {
            try {
                int choice = Integer.parseInt(scanner.readLine());
                if (choice >= 1 && choice <= 5) {
                        ChoosePawnEvent choosePawnEvent = new ChoosePawnEvent(choice, creatorChosen, nickname);
                        eventreceived = EventType.WAIT;
                        notify(choosePawnEvent);
                    synchronized (lock) {
                        while (eventreceived.equals(EventType.WAIT)) {
                            try {
                                lock.wait(); // Wait until notified
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    if (eventreceived.equals(EventType.REFRESH)) {
                        validInput = true;
                    }


                } else
                    System.out.println("Insert a number between 1 and 5");
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input. Please provide an integer.");
            }}

    }

    /**
     * Method to add the listener to the UI
     * @param listener is the listener that will be added to the UI
     */
    public void addListener (ViewListener listener) { // nel main chiamo questo metodo e ci metto in input il controller
        this.viewListener = listener;
    }


    /**
     * Method to display the name of the game
     */
    public void showGameName() {
        StringBuilder ris = new StringBuilder();
        ris.append(ansi().fg(Ansi.Color.GREEN).bg(Ansi.Color.DEFAULT).a(" ██████╗ ██████╗ ██████╗ ███████╗██╗  ██╗        ███╗   ██╗ █████╗ ████████╗██╗   ██╗██████╗  █████╗ ██╗     ██╗███████╗").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n").
                fg(Ansi.Color.GREEN).bg(Ansi.Color.DEFAULT).a("██╔════╝██╔═══██╗██╔══██╗██╔════╝╚██╗██╔╝        ████╗  ██║██╔══██╗╚══██╔══╝██║   ██║██╔══██╗██╔══██╗██║     ██║██╔════╝").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n").
                fg(Ansi.Color.GREEN).bg(Ansi.Color.DEFAULT).a("██║     ██║   ██║██║  ██║█████╗   ╚███╔╝         ██╔██╗ ██║███████║   ██║   ██║   ██║██████╔╝███████║██║     ██║███████╗").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n").
                fg(Ansi.Color.GREEN).bg(Ansi.Color.DEFAULT).a("██║     ██║   ██║██║  ██║██╔══╝   ██╔██╗         ██║╚██╗██║██╔══██║   ██║   ██║   ██║██╔══██╗██╔══██║██║     ██║╚════██║").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n").
                fg(Ansi.Color.GREEN).bg(Ansi.Color.DEFAULT).a("╚██████╗╚██████╔╝██████╔╝███████╗██╔╝ ██╗        ██║ ╚████║██║  ██║   ██║   ╚██████╔╝██║  ██║██║  ██║███████╗██║███████║").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n").
                fg(Ansi.Color.GREEN).bg(Ansi.Color.DEFAULT).a(" ╚═════╝ ╚═════╝ ╚═════╝ ╚══════╝╚═╝  ╚═╝        ╚═╝  ╚═══╝╚═╝  ╚═╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n").
                fg(Ansi.Color.GREEN).bg(Ansi.Color.DEFAULT).a("                                                                                                                        ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT));
        System.out.println(ris);
    }

    /**
     * Method to display the options that can be chosen by the user
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    public void showMenuOptions() throws IOException {

        boolean validInput = false;

        // an user can join or create a game
        while(!validInput) {
            try {
                System.out.println("Do you want to join a game or create one?\ntype\n1: join\n2: create");
                int choice = Integer.parseInt(scanner.readLine());

                // join a game
                if (choice == 1) {
                    try {
                        eventreceived = EventType.WAIT;
                        notify(new WantToJoinEvent(clientId));
                        synchronized (lock) {
                            while (eventreceived.equals(EventType.WAIT)) {
                                try {
                                    lock.wait(); // Wait until notified
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                        if (creators.size() == 0) {
                            System.out.println("No games available, create one");
                            showCreateGame();
                        } else{
                            System.out.println("these are the creators of the available, type one of these names to join his game");
                            for (String creator : creators)
                                System.out.println(creator);
                            boolean flag = false;
                            while(!flag) {
                                creatorChosen = scanner.readLine();
                                if (creators.contains(creatorChosen)) {
                                    flag = true;
                                }else{
                                    System.out.println("Invalid creator, try again");
                                }
                            }
                            showSetNicknameAndJoin(0);

                        }


                        validInput = true;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                // create a game
                else if (choice == 2) {

                    showCreateGame();
                    validInput = true;
                }
                else
                    System.out.println("Insert a number between 1 and 2");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please provide an integer.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * Method to set nickname to the player and join the game
     * @param choice is the choice of the user
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    public void showSetNicknameAndJoin(int choice) throws IOException {
        eventreceived = EventType.WAIT;
        // player insert his name and, if valid, join a game
        while (!eventreceived.equals(EventType.REFRESH)) {
            boolean flag = false;
            while(!flag) {
                System.out.println("Insert your Nickname: ");
                nickname = scanner.readLine();
                if(!nickname.trim().isEmpty()){
                    flag = true;
                }else{
                    System.out.println("Invalid nickname, try again");
                }
            }
            if (choice > 0)  // case : create game
                creatorChosen = nickname;
            JoinEvent1 joinEvent = new JoinEvent1(nickname, creatorChosen, clientId, choice);
            notify(joinEvent);

            synchronized (lock) {
                while (eventreceived.equals(EventType.WAIT)) {
                    try {
                        lock.wait(); // Wait until notified
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    }
                }
            }
            if (eventreceived.equals(EventType.NOJOIN)) {
                System.out.println("You can't join the game");
                eventreceived = EventType.WAIT;
                showCreateGame();
                break;
            } else if (eventreceived.equals(EventType.INVALIDUSER)) {
                System.out.println("Nickname already used by another player.");
                eventreceived = EventType.WAIT;
            }
        }
        eventreceived = EventType.WAIT;}  // after join the game the player wait


    /**
     * Method to create a game and join it
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    public void showCreateGame() throws IOException {

        int choice = 0;
        boolean validInput = false;

        // creazione del gioco
        System.out.println("Creating new game...");
        while(!validInput) {
            System.out.println("How many players do you want?");
            try {
                choice = Integer.parseInt(scanner.readLine());
                if(choice >= 2 && choice <= 4) {

                    validInput = true;
                }

                else
                    System.out.println("Insert a number between 2 and 4");
            }

            catch (NumberFormatException e) {
                System.out.println("You have to insert a number");
            }
        }
        // after the player created the game,join it
            showSetNicknameAndJoin(choice);
    }


    /**
     * Method to show the 2 objective cards to the player
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    public void showObjectiveCards() throws IOException {
        Player player = game.getPlayerByUsername(nickname);
        int card = 0;
        boolean validInput = false;
        StringBuilder combinedCards = new StringBuilder();
        List<String> cards = new ArrayList<>();
        System.out.println("Your Possible Objective Cards: ");
        cards.add(player.possibleObjectives().get(0).toString());
        cards.add(player.possibleObjectives().get(1).toString());
        int numberOfLines = player.possibleObjectives().get(1).toString().split("<br>").length;
        for (int i = 0; i < numberOfLines; i++) {
            for (String card2 : cards) {
                String[] cardLines = card2.split("<br>");
                combinedCards.append(cardLines[i]).append("  ");
            }
            combinedCards.append("\n");
        }

        System.out.print(combinedCards);
        while (!validInput) {
            try {
                System.out.println("Which Objective Card do you want? \ntype\n 1:first one \n 2:second one");
                card = Integer.parseInt(scanner.readLine());

                if(card==1 || card==2) {
                    ChooseObjEvent1 chooseObjEvent = new ChooseObjEvent1(nickname, card, creatorChosen);
                    notify(chooseObjEvent);
                    validInput = true;
                }
                else {
                    System.out.println("Error, try again ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please provide an integer.");
            }
        }
        System.out.flush();
    }


    /**
     * Method to show the starter card to the player
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    public void showStarterCard() throws IOException {
        Player player = game.getPlayerByUsername(nickname);
        int card = 0;
        boolean validInput = false;
        StringBuilder combinedCards = new StringBuilder();
        List<String> cards = new ArrayList<>();
        System.out.println("This is your starter card");
        cards.add(player.getPlayerStarterCard().toStringFront());
        cards.add(player.getPlayerStarterCard().toStringBack());

        int numberOfLines = player.getPlayerStarterCard().toStringFront().split("<br>").length;
        for (int i = 0; i < numberOfLines; i++) {
            for (String card2 : cards) {
                String[] cardLines = card2.split("<br>");
                combinedCards.append(cardLines[i]).append("  ");
            }
            combinedCards.append("\n");
        }

        System.out.print(combinedCards);

        while (!validInput) {
            try {

                System.out.println("Which side of the starter card do you want? \ntype\n 1: front \n 2: back");
                card = Integer.parseInt(scanner.readLine());

                if (card == 1 || card == 2) {
                    boolean front = false;
                    if (card == 1) front = true;
                    if (card == 2) front = false;

                    SetStarterCardEvent1 setStarterCardEvent = new SetStarterCardEvent1(nickname, front, creatorChosen);
                    notify(setStarterCardEvent);
                    validInput = true;
                } else {
                    System.out.println("Error, try again");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please provide an integer.");
            }
        }
    }

    /**
     * Method to manage the game
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    public void playGame() throws IOException {
        Thread thread = null;
        while (game.getState().equals(State.RUNNING) || game.getState().equals(State.LAST_TURN)) {
            // not my turn
            if (!game.getCurrentPlayer().getName().equals(nickname)) {
                // print the board, the market, the hand
                this.showStation();
                this.showMarket();
                this.showHand();
                Thread.UncaughtExceptionHandler h = new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread thread, Throwable ex) {
                        System.out.println("");
                    }
                };
                thread = new Thread(() -> {
                    String input1;
                    // quit or chat
                    while (!game.getCurrentPlayer().getName().equals(nickname)) {
                        System.out.println("\n"+ nickname + " POINTS: " + game.getPlayerByUsername(nickname).getPoints());
                        showPlayerResources();
                        System.out.println("\n");
                        System.out.println("It's not your turn. Write: ");
                        showBaseOptions();
                        System.out.println("'otherBoard' to see other player's board");
                        if (game.getState().equals(State.LAST_TURN)) {
                            int myposition = game.getPlayers().indexOf(game.getPlayerByUsername(nickname));
                            int currentpos = game.getPlayers().indexOf(game.getCurrentPlayer());

                            if (myposition < currentpos) {
                                System.out.println("ATTENTION! SOMEONE HAS REACHED THE MAXIMUM POINTS");
                                System.out.println("This is the last turn. You cannot longer play\");");
                            }
                            else {
                                System.out.println("ATTENTION! SOMEONE HAS REACHED THE MAXIMUM POINTS");
                                System.out.println("This is the last turn. Be ready for your last play");
                            }
                        }

                      try{
                            while (!scanner.ready())
                                sleep(1000);
                        } catch (InterruptedException e) {
                            throw new SilentRuntimeException(e);
                        } catch (IOException e) {
                          throw new RuntimeException(e);
                      }
                      try {
                            input1 = (scanner.readLine());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                      // decides to quit
                        if (input1.equalsIgnoreCase("quit")) {
                            showGameExit();
                            notify(new QuitEvent(EventType.QUITEVENT, creatorChosen, nickname));
                            System.exit(0); // Exit from the program
                        }
                        else if (input1.equalsIgnoreCase("cards")) {
                            showCard();
                        }

                        else if(input1.equalsIgnoreCase("otherBoard")) {
                            showBoard();

                        }
                        // decides to chat
                        else if (input1.equalsIgnoreCase("chat")) {
                            String chatChoice;
                            int chatChoice1;
                            int flag = -1;



                            while (true){
                                System.out.println("who do you want to chat with ;) ?");
                                System.out.println("1 : global");
                                int i = 2;
                                for (int j = 0; j < game.getPlayers().size(); j++) {
                                    if (game.getPlayers().get(j).getName().equals(nickname)) {
                                        flag = j;
                                    } else {
                                        System.out.println(i + " : " + game.getPlayers().get(j).getName());
                                        i++;
                                    }
                                }
                                try{
                                    while (!scanner.ready())
                                        sleep(1000);
                                } catch (InterruptedException e) {
                                    throw new SilentRuntimeException(e);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                                try {
                                    chatChoice =scanner.readLine();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                try{
                                 chatChoice1 = Integer.parseInt(chatChoice);
                                if (chatChoice1 >= 1 && chatChoice1 <= game.getPlayers().size() )
                                    break;
                                else
                                System.out.println("Invalid choice, try again");
                                System.out.flush();
                            }
                            catch (NumberFormatException e){
                                System.out.println("Invalid choice, try again");
                                System.out.flush();
                            }
                            }
                            System.out.println("Enter your message: ");
                            System.out.flush();
                            String message = null;
                            try{
                                while (!scanner.ready())
                                    sleep(1000);
                            } catch (InterruptedException e) {
                                throw new SilentRuntimeException(new NoCauseException());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                message = scanner.readLine();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            if(chatChoice1 ==1)
                                notify(new ChatEvent1(nickname, message, "all", creatorChosen));
                             else if (chatChoice1 > flag+1)
                            {
                                notify(new ChatEvent1(nickname, message, game.getPlayers().get(chatChoice1-1).getName(),creatorChosen));
                                System.out.flush();}
                            else
                            {
                                notify(new ChatEvent1(nickname, message, game.getPlayers().get(chatChoice1-2).getName(),creatorChosen));
                                System.out.flush();
                            }
                        }

                        ;}
                 });
                thread.setUncaughtExceptionHandler(h);
                 thread.start();
                synchronized (lock) {
                    while (!game.getCurrentPlayer().getName().equals(nickname) && !game.getState().equals(State.ENDED)) {
                        try {
                            lock.wait(); // Wait until notified
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            throw new RuntimeException(e);
                        }}}
                thread.interrupt();
            }
            else{
                int column = 0;
                int row = 0;
                int chosenCard = 0;
                String input;
                System.out.println("\n"+nickname + " POINTS: " + game.getPlayerByUsername(nickname).getPoints());
                showPlayerResources();
                System.out.println("\n");
                System.out.println("It's your turn. Write: ");
                showBaseOptions();
                System.out.println("'place' to place your card");
                System.out.println("'draw' to draw ");
                System.out.println("'flip' to flip your cards");


                if (game.getState().equals(State.LAST_TURN))
                    System.out.println("This is the last turn. Play wisely!!");
                // if i have 3 cards, U can place them
                if(game.getPlayerByUsername(nickname).getHand().size() == 3){
                    this.showStation();
                    this.showHand();}

                // if I have 2 cards, I can draw
                if(game.getPlayerByUsername(nickname).getHand().size() == 2) {
                    this.showMarket();
                    this.showHand();
                    }

                input = scanner.readLine();
                if (input.equalsIgnoreCase("quit")) {
                    showGameExit();
                    notify(new QuitEvent(EventType.QUITEVENT, creatorChosen, nickname));
                    System.exit(0); // Exit from the program
                }
                    // place -> notify
                else if (input.equalsIgnoreCase("place") && game.getPlayerByUsername(nickname).getHand().size() == 3) {
                    while (true) {
                        try {
                            BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));

                            do {
                                System.out.println("Insert the row where you want to place");
                                row = Integer.parseInt(scanner.readLine());
                                System.out.println("Insert the column where you want to place");
                                column = Integer.parseInt(scanner.readLine());
                                System.out.println("Insert the card you want to place");
                                chosenCard = Integer.parseInt(scanner.readLine());

                                if (column < 0 || column > 81 || row < 0 || row > 81 || chosenCard < 1 || chosenCard > 3) {
                                    System.out.println("Invalid choices. Please try again.");
                                }
                            } while (column < 0 || column > 81 || row < 0 || row > 81 || chosenCard < 1 || chosenCard > 3);

                            PlaceEvent1 placeEvent = new PlaceEvent1(column, row, chosenCard, nickname, creatorChosen);
                            eventreceived = EventType.WAIT;
                            notify(placeEvent);
                            synchronized (lock) {
                                while (eventreceived.equals(EventType.WAIT)) {
                                    try {
                                        lock.wait(); // Wait until notified
                                    } catch (InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                        throw new RuntimeException(e);
                                    }
                                }
                                if (eventreceived.equals(EventType.REFRESH)) {
                                    break;
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please provide an integer.");
                        } catch (IOException e) {
                            System.out.println("An error occurred while reading the input.");
                        }
                    }
                } else if (input.equalsIgnoreCase("chat")) {

                        String chatChoice;
                        int chatChoice1;
                        int flag = -1;


                        while (true) {
                            System.out.println("who do you want to chat with ;) ?");
                            System.out.println("1 : global");
                            int i = 2;
                            for (int j = 0; j < game.getPlayers().size(); j++) {
                                if (game.getPlayers().get(j).getName().equals(nickname)) {
                                    flag = j;
                                } else {
                                    System.out.println(i + " : " + game.getPlayers().get(j).getName());
                                    i++;
                                }
                            }
                            try {
                                while (!scanner.ready())
                                    sleep(1000);
                            } catch (InterruptedException e) {
                                throw new SilentRuntimeException(e);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            try {
                                chatChoice = scanner.readLine();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                chatChoice1 = Integer.parseInt(chatChoice);
                                if (chatChoice1 >= 1 && chatChoice1 <= game.getPlayers().size() + 1)
                                    break;
                                else
                                    System.out.println("Invalid choice, try again");
                                System.out.flush();
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid choice, try again");
                                System.out.flush();
                            }
                        }
                        System.out.println("Enter your message: ");
                        System.out.flush();
                        String message = null;
                        try {
                            while (!scanner.ready())
                                sleep(1000);
                        } catch (InterruptedException e) {
                            throw new SilentRuntimeException(new NoCauseException());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            message = scanner.readLine();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        if (chatChoice1 == 1)
                            notify(new ChatEvent1(nickname, message, "all", creatorChosen));
                        else if (chatChoice1 > flag+1) {
                            notify(new ChatEvent1(nickname, message, game.getPlayers().get(chatChoice1 - 1).getName(), creatorChosen));
                            System.out.flush();
                        } else  {
                            notify(new ChatEvent1(nickname, message, game.getPlayers().get(chatChoice1 - 2).getName(), creatorChosen));
                            System.out.flush();
                        }
                    }


                else if (input.equalsIgnoreCase("flip")) {
                    FlipEvent1 flipEvent = new FlipEvent1(nickname, creatorChosen);
                    notify(flipEvent);
                    System.out.println("Cards flipped");
                    this.showHand();
                     // Exit from the program
                }
                else if (input.equalsIgnoreCase("cards")) {
                    showCard();
                }
                else if (input.equalsIgnoreCase("draw") && game.getPlayerByUsername(nickname).getHand().size() == 2) { // draw cards only when the playr has 2 cards in his hand
                        this.showStation();
                        this.showOtherStations();
                        this.showMarket();
                        this.showHand();
                        int select = 0;
                        while (true) {
                            try {
                                BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));

                                do {
                                    System.out.println("Insert a number between 1 and 6");
                                    System.out.println("1, 2, 3, 4 for market");
                                    System.out.println("5 for resource deck, 6 for golden deck");
                                    select = Integer.parseInt(scanner.readLine());
                                } while (select < 1 || select > 6 );

                                DrawEvent1 drawEvent = new DrawEvent1(select - 1, nickname, creatorChosen);
                                eventreceived = EventType.WAIT;
                                notify(drawEvent);
                                synchronized (lock) {
                                    while (eventreceived.equals(EventType.WAIT)) {
                                        try {
                                            lock.wait(); // Wait until notified
                                        } catch (InterruptedException e) {
                                            Thread.currentThread().interrupt();
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }
                                break;
                            }
                            catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please provide an integer.");
                            } catch (IOException e) {
                                System.out.println("An error occurred while reading the input.");
                            }
                        }
                }
                else
                    System.out.println("Invalid! Enter a valid input please");
                }

            }
        endGame();
    }

    /**
     * Method to manage the endGame, so it finds the winners and notify the players
     */
    public void endGame(){
        if(game.findWinners().size() == 1)
            System.out.println("And the winner is....." + game.findWinners().get(0).getName());
        else{
            System.out.println("And the winners are...");
            for( Player winner : game.findWinners())
                System.out.println(winner.getName());
        }
        System.out.println("The game is over. Thanks for playing!");
        GameEndedEvent gameEndedEvent = new GameEndedEvent(nickname, creatorChosen);
        notify(gameEndedEvent);
        System.exit(0);
    }

    /**
     * Method to notify the client that there is an event
     * @param event is the event to be notified
     */
    // methods that notify the client of a user decision ( = event)
    public void notify(Event event){
        viewListener.handleMessage(event);
    }


    /**
     * Method to print the commands that the player can use
     */
    public void showBaseOptions(){
        System.out.println("'chat' to chat");
        System.out.println("'quit' to quit");
        System.out.println("'cards' to see your cards");
    }

    /**
     * Method to refresh the game
     * @param gameview is the GameView of the game.
     * @param username is the nickname of the player that generated the event
     */
    @Override
    public void handleRefresh(GameView gameview, String username) {
        synchronized (lock) {
            this.game = gameview;
            if (nickname == null || this.nickname.equals(username))   // username = null to manage createGame in which there ins't yet an username
                eventreceived = EventType.REFRESH;
            lock.notifyAll(); // Notify all waiting threads
        }

    }

    /**
     * Method to get the creators of the games
     * @param creators is the list of creators of the games
     */
    public void handleCreators(ArrayList<String> creators){
        synchronized (lock){

            eventreceived = EventType.GETCREATORS;
            this.creators = creators;
            lock.notifyAll();

        }
    }

    /**
     * Method to handle the error
     * @param event    type of error event received
     * @param username is the nickname of the player that generated the event
     */
    public void handleError (Event event, String username){
        synchronized (lock){
         if (event.getType() == EventType.NOPAWN) {
             System.out.println("Pawn already chosen by " + ((NoPawnEvent) event).getPawnOwner() + ", choose another one");
             eventreceived = EventType.NOPAWN;
         }
         else if (event.getType() == EventType.INVALIDUSER)
            eventreceived = EventType.INVALIDUSER;
        else if (event.getType() == EventType.INVALIDPOS){
            eventreceived = EventType.INVALIDPOS;
            event = (InvalidPositionEvent1) event;
            System.out.println(((InvalidPositionEvent1) event).getReason());
        }
        lock.notifyAll();}}

    /**
     * Method to print the message in the console
     * @param message  is the message that a player sent
     * @param username is the nickname of the player that sent the message
     */
    public void handleChat(String message, String username){
        if (!username.equals(nickname)) // don't print the message if it was sent by him
            System.out.println(username + ": " + message);
    }

    /**
     * Method to exit from the game
     */
    @Override
    public void exitGame() {
        System.out.print("\033[H\033[2J"); // clear the terminal
        System.out.flush();
        System.out.println("You are being disconnected from the game...");
        System.exit(0);
    }
    // methods that use the ModelView to show the Model state

    /**
     * Method to get the nickname of the player
     * @return the nickname of the player
     */
    public String getNickname(){
        return this.nickname;
    }


    /**
     * Method to show the game exit
     */
    public  void showGameExit(){
        System.out.println("Exiting the game...");
    }

    /**
     * Method to show the waiting for players
     */
    public void showWaitingForPlayers(){
        System.out.println("Waiting players to start the game");
    }

    /**
     * Method to show the game started
     */
    public void showGameStarted(){
        System.out.println("Game started! Enjoy !");
    }

    /**
     * Method to show the player resources
     */
    public void showPlayerResources() {
        Player player = game.getPlayerByUsername(nickname);

        System.out.println(player.getStation().getResNum(Resource.ANIMAL) + " 🐺 ANIMALS");
        System.out.println(player.getStation().getResNum(Resource.PLANT) + " 🌿 PLANTS");
        System.out.println(player.getStation().getResNum(Resource.INSECT) + " 🦋 INSECTS");
        System.out.println(player.getStation().getResNum(Resource.FUNGI) + " 🍄 FUNGIS");
    }

    /**
     * Method to show the hand of the player
     */
    public void showHand(){
        StringBuilder combinedCards = new StringBuilder();
        List<String> cards = new ArrayList<>();
        for(PlayingCard card: game.getPlayerByUsername(nickname).getHand())
            cards.add(card.toString());
                System.out.println("Here is your hand");
                int numberOfLines = cards.get(0).split("<br>").length;
                for (int i = 0; i < numberOfLines; i++) {
                    for (String card : cards) {
                        String[] cardLines = card.split("<br>");
                        combinedCards.append(cardLines[i]).append("  ");
                    }
                    combinedCards.append("\n");
                }
        System.out.print(combinedCards);

    }

    /**
     * Method to show the station of the player
     */
    public void showStation(){
        for(Player player: game.getPlayers()){
            if(player.getName().equals(this.nickname)) {
                System.out.println("\nYour Board\n");
                System.out.println(player.getStation().toString());
            }
        }
    }

    /**
     * Method to show the card of another player
     * @param nickname is the nickname of another player
     */
    public void showCard(String nickname){

        int choice = 0;
        boolean validInput = false;
        StringBuilder combinedCards = new StringBuilder();
        Player player = game.getPlayerByUsername(nickname);
        int maxsize = player.getStation().getCards().size() - 1;

        while (!validInput) {
            try {
                System.out.println("Which card of the station do you want to see?\n");
                System.out.println("Choose between 0 and " + maxsize);
                try {
                    while (!scanner.ready())
                        sleep(1000);
                } catch (InterruptedException e) {
                    throw new SilentRuntimeException(new NoCauseException());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                choice = Integer.parseInt(scanner.readLine());

                if (0 <= choice && choice <= maxsize) {
                    List<String> cards = new ArrayList<>();
                    cards.add(player.getStation().getCards().get(choice).toString());
                    int numberOfLines = cards.get(0).split("<br>").length;

                    for (int i = 0; i < numberOfLines; i++) {
                        for (String card : cards) {
                            String[] cardLines = card.split("<br>");
                            combinedCards.append(cardLines[i]).append("  ");
                        }
                        combinedCards.append("\n");
                    }

                    System.out.print(combinedCards);
                    System.out.flush();
                    validInput = true;
                }
                else{
                    System.out.println("Error there isn't this card, try again ");
                }
            } catch (IOException | NumberFormatException |InputMismatchException e) {
                System.out.println("Error, insert a number: ");
            }
        }
    }

    /**
     * Method to show the cards of the player
     */
    public void showCard(){
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean validInput = false;
        StringBuilder combinedCards = new StringBuilder();
        Player player = game.getPlayerByUsername(nickname);
        int maxsize = player.getStation().getCards().size() - 1;
        System.out.flush();

        while (!validInput) {
            try {
                System.out.println("Which card of the station do you want to see?\n");
                System.out.flush();
                System.out.println("Choose between 0 and " + maxsize);
                System.out.flush();
                choice = scanner.nextInt();

                if (0 <= choice && choice <= maxsize) {
                    List<String> cards = new ArrayList<>();
                    cards.add(player.getStation().getCards().get(choice).toString());
                    int numberOfLines = cards.get(0).split("<br>").length;

                    for (int i = 0; i < numberOfLines; i++) {
                        for (String card : cards) {
                            String[] cardLines = card.split("<br>");
                            combinedCards.append(cardLines[i]).append("  ");
                        }
                        combinedCards.append("\n");
                    }

                    System.out.print(combinedCards);
                    System.out.flush();
                    validInput = true;
                }
                else{
                    System.out.println("Error there isn't this card, try again ");
                    System.out.flush();
                }
            } catch (InputMismatchException e) {
                System.out.println("Error, insert a number: ");
                System.out.flush();
                scanner.next();
            }
        }
    }

    /**
     * Method to show the other stations of the players
     */
    public void showOtherStations(){
        for(Player player: game.getPlayers()){
            if(!player.getName().equals(this.nickname)) {
                System.out.println("Board of " + player.getName() + "\n");
                System.out.println(player.getStation().toString());
                System.out.println("\n");
            }
        }
    }

    /**
     * Method to show the market
     */
    public void showMarket(){
        System.out.println(game.getMarket().toString());
    }

    /**
     * Method to show the board of another player
     */
    public void showBoard(){
        int boardChoice = -1;
        int flag = -1;
        while (true) {
            System.out.println("Which board do you want to see?");
            int i = 1;
            for (int j = 0; j < game.getPlayers().size(); j++) {
                if (game.getPlayers().get(j).getName().equals(nickname)) {
                    flag = j;
                } else {
                    System.out.println(i + " : " + game.getPlayers().get(j).getName());
                    i++;
                }
            }
            try {
                while (!scanner.ready())
                    sleep(1000);
            } catch (InterruptedException e) {
                throw new SilentRuntimeException(new NoCauseException());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                boardChoice = Integer.parseInt(scanner.readLine());
                if (boardChoice >= 1 && boardChoice < game.getPlayers().size()){
                    break;}
                else{
                    System.out.println("Invalid choice, try again");}
                System.out.flush();
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice, try again");
                System.out.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (boardChoice > flag)
            {
            System.out.println(game.getPlayers().get(boardChoice).getName() + "'s Board");
            System.out.println(game.getPlayers().get(boardChoice).getStation().toString());
            System.out.flush();
            showCard(game.getPlayers().get(boardChoice).getName());
            System.out.flush();}
        else
        {
            System.out.println(game.getPlayers().get(boardChoice-1).getName() + "'s Board");
            System.out.println(game.getPlayers().get(boardChoice-1).getStation().toString());
            System.out.flush();
            showCard(game.getPlayers().get(boardChoice-1).getName());
            System.out.flush();
        }

            }
}
