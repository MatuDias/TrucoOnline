import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class Connection_accepter extends Thread
{


    private ServerSocket server;
    private final ArrayList<User> users;
    private Round_manager round_manager;
    private Boolean hasStarted = false;
    private Deck deck;

    public Connection_accepter(String door, ArrayList<User> users) throws Exception
    {
        if(door == null)
            throw new Exception("Parametro door ausente");
        if(users == null)
            throw new Exception("Usuarios ausentes");

        try
        {
            server = new ServerSocket(Integer.parseInt(door));
        }
        catch(Exception err)
        {
            System.err.println("Porta invalida");
        }

        this.users = users;
        deck = new Deck();

    }

    public void run()
    {

        while(true)
        {
            if(hasStarted && users.size() == 1)
            {
                // One of the players exit the game mid-game

                hasStarted = false;
                synchronized (users)
                {
                    try
                    {
                        users.get(0).send(new WarningRestart());
                    }
                    catch(Exception ignored)
                    {}
                }
                //TODO Reset everything up
            }

            Socket connection;
            try
            {
                connection = server.accept();
            }
            catch(Exception err)
            {
                continue;
            }

            Connection_manager cManager;
            try
            {

                //cManager is one of the player.
                // It's how the server will communicate to the user, it's through here
                cManager = new Connection_manager(connection, users, round_manager);

                synchronized (users)
                {
                    cManager.start(); // Adding 1 to users

                    if(users.size() > 2)
                    {
                        users.get(users.size()-1).send(new WarningFull());
                        users.remove(users.size()-1);
                        connection.close();
                        continue;
                    }
                }

                /*  TODO Add to RoundManager this cManager
                 *  In order to manage the rounds, the roundManager needs
                 *  to have access to the connection manager so it could send
                 *  the round permission


                    synchronized (gerenciadoraDeRodada.getSupervisoras())
                    {
                        gerenciadoraDeRodada.addSupervisora(cManager);
                    }
                 */

                synchronized (users)
                {
                    if(users.size() == 2 && !hasStarted)
                    {
                        hasStarted = true;

                        for (User user: users)
                        {
                            user.send(new WarningStart());
                        }

                    }
                }

            }
            catch(Exception e)
            {
                System.err.println(e.getMessage());
            }


        }
    }

    private void resetEverything()
    {
        deck.resetDeck();
        for(User player: users)
        {
            try
            {
                player.send(new WarningRestart());
            }
            catch (IOException err)
            {
                player.shutDown();
            }
            catch(Exception ignored){}
        }
    }


    //TODO Add all those mandatory methods

}
