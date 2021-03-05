import java.net.*;
import java.util.ArrayList;

public class Connection_accepter extends Thread
{


    private ServerSocket server;
    private ArrayList<User> users;
    private Round_manager round_manager;
    private Boolean isStarted = false;

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
            if(isStarted && users.size() == 1)
                isStarted = false;

            Socket connection;

            try
            {
                connection = server.accept();
            }
            catch(Exception err)
            {
                continue;
            }





        }
    }

}
