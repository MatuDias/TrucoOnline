import java.util.ArrayList;

public class Server
{
    public static final String DEFAULT_DOOR = "8888";

    public static void main(String[] args)
    {
        if(args.length > 1)
        {
            System.err.println("Uso esperado: java Server [porta]\n");
            return;
        }

        String door = DEFAULT_DOOR;
        if(args.length == 1)
        {
            door = args[0];
        }

        String command = " ";
        Connection_accepter connection_accepter;
        ArrayList<User> users = new ArrayList<>();

        try
        {
            connection_accepter = new Connection_accepter(door,users);
            connection_accepter.start();

        }catch(Exception err)
        {
            System.err.println(err.getMessage());
        }

        while(true)
        {
            System.out.println("O servidor esta no ar!");
            System.out.println("Digite \"off\" para desligar o servidor");
            System.out.print("> ");

            try
            {
                command = Teclado.getUmString().toLowerCase();
            }
            catch(Exception err)
            {
                System.err.println(err.getMessage());
            }


            switch (command) {
                case "exit", "sair", "desligar", "out", "off", "disconnect" ->
                {
                    synchronized (users) {
                        WarningShutDown warning = new WarningShutDown();

                        for (User user : users)
                        {
                            try
                            {
                                user.send(warning);
                                user.shutDown();
                            }
                            catch (Exception ignored) {}

                        }
                    }
                    System.out.println("O servidor foi desligado!\n");
                    System.exit(0);
                }
                default -> System.out.println("Comando invalido!");
            }


        }

    }
}
