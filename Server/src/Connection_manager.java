import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class Connection_manager  extends Thread
{
    private Socket connection;
    private Round_manager round_manager;
    private User user;
    private ArrayList<User> users;
    private ObjectOutputStream transmitter;
    private ObjectInputStream receiver;

    public Connection_manager(Socket connection, ArrayList<User> users, Round_manager round_manager) throws Exception
    {
        if(connection == null || users == null || round_manager == null)
            throw new Exception("Componente faltando!! (Connection Manager)");

        this.connection = connection;
        this.users = users;
        this.round_manager = round_manager;

    }

    public void run()
    {
        try
        {
            transmitter = new ObjectOutputStream(this.connection.getOutputStream());
            receiver = new ObjectInputStream(this.connection.getInputStream());
        }
        catch(Exception e)
        {
            if(transmitter != null)
            {
                try
                {
                    transmitter.close();

                }
                catch (Exception ignored){}
                return;

            }
            return;
        }

        try
        {
            this.user = new User(this.connection,this.receiver,this.transmitter);
        }
        catch(Exception ignored){}


        try
        {
            synchronized (users)
            {
                this.users.add(user);
            }
        }
        catch(Exception err)
        {
            try
            {
                transmitter.close();
                receiver.close();
            }
            catch(Exception ignored){}
            return;
        }

        try
        {
            do{}while(!(user.look() instanceof WarningStart));
            user.receive();

        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        while (true)
        {
            try
            {

            }
            catch (Exception e){return;}
        }

    }

}
