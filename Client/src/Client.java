import java.io.*;
import java.net.Socket;

public class Client
{
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_DOOR = 8888;

    public static void main(String[] args)
    {
        if(args.length > 2)
        {
            System.err.println("Uso esperado: java Client [HOST[PORTA]]");
            return;
        }

        Socket connection;
        ObjectInputStream receiver;
        ObjectOutputStream transmitter;
        User server = null;
        Warning_manager warningManager = null;


        try
        {
            connection = Instanciacao.instanciarConexao(args);
            transmitter = Instanciacao.instanciarTransmissor(connection);
            receiver = Instanciacao.instanciarReceptor(connection);
            server = Instanciacao.instanciarServidor(connection,receiver, transmitter);
            warningManager = Instanciacao.instanciarTratadora(server);
        }
        catch(Exception error)
        {
            System.err.println(error.getMessage());
            System.err.println("Verefique se o servidor está ativo" +
                    "E verifique se o IP e porta estão corretos");
            System.exit(0);
        }

        warningManager.start();

        System.out.println("Aguarde o jogador entrarem na partida.");

        Warning warning = null;
        while(!(warning instanceof WarningStart))
        {
            try
            {
                warning = server.look();
            }
            catch(Exception e)
            {
                System.err.println(e.getMessage());
            }
        }

        try
        {
            //it might need to comunicate back to server
            //server.send(warning);
            warning = server.receive();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        System.out.println("O jogo começou!!!");


        while (true)
        {
            //TODO
        }


    }
}
