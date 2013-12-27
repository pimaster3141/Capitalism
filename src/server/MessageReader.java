package server;

/**
 * Deals with requests from client to server, as communicated via 
 * text protocol. You can specify the exact protocol later.
 */
public class MessageReader {
    private final String NEW_GAME= "create"; 
    private final String JOIN_GAME= "join";
    private final String PLAY_CARDS= "play";
    private final String SPAM= "spam";
    
    /**
     * 
     * @param message received by server
     * @param username of the client that sent the message (we may not use, not sure)
     */
    public void handleMessage(String message, String username){
        //tokens separated by spaces
        String[] tokens= message.split(" ");
        if (tokens[0].equals(NEW_GAME)){
            //Create a new game with the given name, if it hasn't already been taken
        }
        else if (tokens[0].equals(JOIN_GAME)){
            //Join an existing game if it is open (created and needs more players)
        }
        else if (tokens[0].equals(PLAY_CARDS)){
            //Plays cards if it's the user's turn and if it's a valid move
        }
        else if (tokens[0].equals(SPAM)){
            //Check if spam action is valid
        }
        else{
            //didn't match any of the above :( exception?
        }
    }
    
}
