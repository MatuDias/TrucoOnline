import java.net.*;
import java.util.ArrayList;

public class Connection_accepter extends Thread
{


    private ServerSocket server;
    private final ArrayList<User> users;
    private Round_manager round_manager;
    private Boolean hasStarted = false;

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

    }

    public void run()
    {

        while(true)
        {
            if(hasStarted && users.size() == 1)
                hasStarted = false;

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


                cManager = new Connection_manager(connection, users, round_manager);

                cManager.start(); // Adding 1 to users

                synchronized (users)
                {
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

}
