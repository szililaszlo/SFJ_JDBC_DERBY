import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Db db = new Db();

       // db.addUser("Józsi","Kalocsa");
       // db.showAllUsers();
       // db.deleteTable("yes");
       //db.showUsersMeta();

         ArrayList<User> users =  db.getAllUsers();
        for (User u: users
             ) {
                System.out.println(u.getName());
        }
    }

}
