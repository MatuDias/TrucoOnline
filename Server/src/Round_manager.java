import java.util.ArrayList;

public class Round_manager
{
    private final ArrayList<User> users;




    public Round_manager(ArrayList<User> users) throws Exception
    {
        if(users == null)
            throw new Exception("users eh nulo");


        this.users = users;
    }

    public boolean canItGetATurn(User user){ return false;}
}
