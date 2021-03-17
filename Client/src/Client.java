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

        Socket connection = null;
        ObjectInputStream receiver = null;
        ObjectOutputStream transmitter = null;
        User server = null;
        Warning_manager warningManager = null;

        Hand hand = null;
        Byte points = null;

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
                warning = server.peek();
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
            warning = server.send();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        System.out.println("O jogo começou!!!");

       /*
        *   User first will WAIT for the server to grant he/she permission to start the turn
        * then he/she will WAIT for the server to get their hand, WAIT to get the AxisCard and WAIT for the score
        * After he/she gets all this information, everything will be displayed to the user.
        *   Then the program will get what will the user do, there will be the option to play
        * whatever card he/she have in hands, to say "TRUCO" to the other participant, to hide
        * the card he/she will play or to exit the game.
        *
        * */


        while (true)
        {
            Card axisCard = null;
            try
            {
                do
                {
                    warning = server.peek();
                }
                while (!(warning instanceof WarningRequestRound));
                server.send();


                do
                {
                    warning = server.peek();
                }
                while(!(warning instanceof WarningHand));
                hand = (Hand) server.send();



            }
            catch(Exception error)
            {
                continue;
            }

            try
            {
                axisCard = (Card) server.send();
            }
            catch(Exception ignored){}

            try
            {
                /*
                Byte compare=null;
                do
                {
                    compare = server.peek();
                }
                while (compare instanceof Warning);
                points = (Byte) server.receiveObject();
                */
            }
            catch(Exception ignored){}



            String option = "";
            while(true)
            {
                System.out.println("Suas cartas: "+ hand);
                System.out.println("Manilha: "+ axisCard);
                System.out.println("Pontos: "+ points);
                System.out.println("T. Pedir \"Truco\".");
                System.out.println("E. Ocultar a próxima carta que jogar.");
                System.out.println("Caso queira jogar uma carta, Escreva o nome inteiro da carta que deseja jogar.");
                System.out.println("L. Sair da partida");
                System.out.print("> ");
                option = Teclado.getUmString().toUpperCase();

                switch (option)
                {
                    case "T" -> {
                                    //Case T
                    }
                    case "O" -> {
                                    //Case O
                    }
                    case "L" -> {
                        try
                        {
                            transmitter.close();
                            receiver.close();
                            connection.close();

                        }catch (Exception ignored){}
                        System.exit(0);
                    }

                    default -> {
                        for(int i=0; i < hand.getHand().size(); i++)
                        {
                            if(option.equals(hand.getHand().get(i)))
                            {
                                //TODO Find a way to send the card to the server
                            }
                        }
                                //Search the card on the hand, then send it to the server
                                // if there is no card, send to user it was
                                // an error and continue;
                    }
                }
            }


        }


    }
}
