import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Semaphore;


public class User
{
    private Socket connection;
    private ObjectInputStream receiver;
    private ObjectOutputStream transmiter;

    private Warning nextWarning;
    private Semaphore mutualExclusion = new Semaphore(1,true);

    public User(Socket connection, ObjectInputStream receiver, ObjectOutputStream transmiter) throws Exception
    {
        if(connection == null)
            throw new Exception("connection nula!");
        if(receiver == null)
            throw new Exception("receiver nulo");
        if(transmiter == null)
            throw new Exception("transmitter nulo");

        this.connection = connection;
        this.receiver = receiver;
        this.transmiter = transmiter;
    }

    public void send(Warning warning) throws Exception
    {
        try
        {
            transmiter.writeObject(warning);
            transmiter.flush();
        }
        catch(Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public Warning look() throws Exception
    {
        try {
            mutualExclusion.acquireUninterruptibly();

            if (nextWarning == null)
                nextWarning = (Warning) receiver.readObject();

            mutualExclusion.release();

            return nextWarning;
        }catch(Exception e)
        {
            throw new Exception("Erro look(): "+e.getMessage());
        }
    }

    public Warning receive() throws Exception
    {
        try
        {
            if(nextWarning == null)
                nextWarning = (Warning) receiver.readObject();

            Warning warning = nextWarning;
            nextWarning = null;

            return warning;
        }
        catch (Exception e)
        {
            throw new Exception("Erro recepcao (send): " + e.getMessage());
        }
    }

    public void shutDown()
    {
        try
        {
            receiver.close();
            transmiter.close();
            connection.close();
        }
        catch(Exception ignored){}
    }

    //TODO create mandatory methods (Setters, Getters, toString, etc...)


}
