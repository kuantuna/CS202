import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TunaTuncerHW1{
    private static final String URL = "jdbc:mysql://localhost:3306/FoodHorse?useSSL=false";
    private static final String username = System.getenv("UNAME");
    private static final String password = System.getenv("PWORD");
    private static int userInput = 0;
    private static Connection connection = null;

    public static void establishConnection(){
        try{
            connection = DriverManager.getConnection(URL, username, password);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void closeConnection(){
        try{
            if(connection != null)
                connection.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void displayMenu(){
        System.out.println("(1) Customer registration");
        System.out.println("(2) Buying a product");
        System.out.println("(3) List customers");
        System.out.println("(4) List a customer's purchases");
        System.out.println("(5) List a customer's most recent purchases");
        System.out.println("(6) List branches");
        System.out.println("(7) List a branch’s stock");
        System.out.println("(8) Add new branch store");
        System.out.println("(9) Add new product");
        System.out.println("(10)Add product to branch’s stock");
        System.out.println("(11)Search customer by their phone number");
        System.out.println("(12)Remove a user from system");
        System.out.println("(13)Exit");
    }

    public static void getUserInput(){
        Scanner userInputScanner = new Scanner(System.in);
        System.out.print("Please enter a number between 1-13: ");
        String tempInput = userInputScanner.next();
        while(true){
            try{
                userInput = Integer.parseInt(tempInput);
                if(userInput>=1 && userInput<=13)
                    break;
            }
            catch(NumberFormatException nfe){}
            System.out.print("Your input was invalid, please enter a number between 1-13: ");
            tempInput = userInputScanner.next();
        }
    }
    public static String getCustomerInfo(String info){
        Scanner customerInfoScanner = new Scanner(System.in);
        System.out.print("Please enter customer " + info +": ");
        String customerInfo = customerInfoScanner.next();
        if(info.equals("phone number")){
            while(true){
                if(customerInfo.length()==11)
                    break;
                System.out.println("Phone number must be 11 digits. Like (05551112233)");
                System.out.print("Please enter customer phone number: ");
                customerInfo = customerInfoScanner.next();
            }
        }
        else{
            while(true){
                if(customerInfo.length()<=30)
                    break;
                System.out.println(info.substring(0,1).toUpperCase() + info.substring(1) + " can be up to 30 letters.");
                System.out.print("Please enter customer " + info +": ");
                customerInfo = customerInfoScanner.next();
            }
        }
        return customerInfo;
    }
    public static void createCustomer(String customerName, String customerSurname,
                                      String customerAddress, String customerPhoneNumber){
        try{
            Statement customerStatement = connection.createStatement();
            String addCustomerQuery = "INSERT INTO Customer(fname, lname, caddress, pnum) "
                    + "VALUES('" + customerName + "', '" + customerSurname + "', '" + customerAddress
                    + "', '" + customerPhoneNumber + "')";
            customerStatement.executeUpdate(addCustomerQuery);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void handle1(){
        String customerName = getCustomerInfo("name");
        String customerSurname = getCustomerInfo("surname");
        String customerAddress = getCustomerInfo("address");
        String customerPhoneNumber = getCustomerInfo("phone number");
        createCustomer(customerName, customerSurname, customerAddress, customerPhoneNumber);
    }
    public static void handle2(){
        System.out.println(2);
    }
    public static void handle3(){
        System.out.println(3);
    }
    public static void handle4(){
        System.out.println(4);
    }
    public static void handle5(){

    }
    public static void handle6(){

    }
    public static void handle7(){

    }
    public static void handle8(){

    }
    public static void handle9(){

    }
    public static void handle10(){

    }
    public static void handle11(){

    }
    public static void handle12(){

    }
    public static void handle13(){

    }
    public static void functionHandler(){
        if(userInput==1){
            handle1();
        }
        else if(userInput==2){
            handle2();
        }
        else if(userInput==3){
            handle3();
        }
        else if(userInput==4){
            handle4();
        }
        else if(userInput==5){
            handle5();
        }
        else if(userInput==6){
            handle6();
        }
        else if(userInput==7){
            handle7();
        }
        else if(userInput==8){
            handle8();
        }
        else if(userInput==9){
            handle9();
        }
        else if(userInput==10){
            handle10();
        }
        else if(userInput==11){
            handle11();
        }
        else if(userInput==12){
            handle12();
        }
        else if(userInput==13){
            handle13();
        }
    }

    public static void main(String[] args){
        try {
            establishConnection();
            while(userInput != 13){
                displayMenu();
                getUserInput();
                functionHandler();
            }
            closeConnection();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
