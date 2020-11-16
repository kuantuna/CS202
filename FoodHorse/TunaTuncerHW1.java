import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TunaTuncerHW1{
    private static final String URL = "jdbc:mysql://localhost:3306/FoodHorse?useSSL=false";
    private static final String username = System.getenv("UNAME");
    private static final String password = System.getenv("PWORD");
    private static final Scanner scanner = new Scanner(System.in);
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
        System.out.println("---------------------------------------------");
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
        System.out.println("---------------------------------------------");
    }

    public static void getUserInput(){
        System.out.print("Please enter a number between 1-13: ");
        String tempInput = scanner.nextLine();
        while(true){
            try{
                userInput = Integer.parseInt(tempInput);
                if(userInput>=1 && userInput<=13)
                    break;
            }
            catch(NumberFormatException nfe){}
            System.out.print("Your input was invalid, please enter a number between 1-13: ");
            tempInput = scanner.nextLine();
        }
    }
    public static String getCustomerInfo(String info){
        System.out.print("Please enter customer " + info +": ");
        String customerInfo = scanner.nextLine();
        if(info.equals("phone number")){
            while(true){
                if(customerInfo.length()==11)
                    break;
                System.out.println("Phone number must be 11 digits. Like (05551112233)");
                System.out.print("Please enter customer phone number: ");
                customerInfo = scanner.nextLine();
            }
        }
        else{
            while(true){
                if(customerInfo.length()<=30)
                    break;
                System.out.println(info.substring(0,1).toUpperCase() + info.substring(1) + " can be up to 30 letters.");
                System.out.print("Please enter customer " + info +": ");
                customerInfo = scanner.nextLine();
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

    public static String getBranchInfo(String info){
        System.out.print("Please enter branch " + info +": ");
        String branchInfo = scanner.nextLine();
        if(info.equals("name")){
            while(true){
                if(branchInfo.length()<=50)
                    break;
                System.out.println("Name can be up to 50 letters.");
                System.out.print("Please enter branch name: ");
                branchInfo = scanner.nextLine();
            }
        }
        else if(info.equals("address")){
            while(true){
                if(branchInfo.length()<=30)
                    break;
                System.out.println("Address can be up to 30 letters.");
                System.out.print("Please enter branch address: ");
                branchInfo = scanner.nextLine();
            }
        }
        return branchInfo;
    }
    public static void createBranch(String branchName, String branchAddress){
        try{
            Statement branchStatement = connection.createStatement();
            String addBranchQuery = "INSERT INTO Branch(bname, baddress) "
                    + "VALUES('" + branchName + "', '" + branchAddress + "')";
            branchStatement.executeUpdate(addBranchQuery);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static void handle8(){
        String branchName = getBranchInfo("name");
        String branchAddress = getBranchInfo("address");
        createBranch(branchName, branchAddress);
    }

    public static String getProductInfo(String info){
        System.out.print("Please enter product " + info +": ");
        String productInfo = scanner.nextLine();
        if(info.equals("name")){
            while(true){
                if(productInfo.length()<=50)
                    break;
                System.out.println("Name can be up to 50 letters.");
                System.out.print("Please enter product name: ");
                productInfo = scanner.nextLine();
            }
        }
        else if(info.equals("description")){
            while(true){
                if(productInfo.length()<=100)
                    break;
                System.out.println("Description can be up to 100 letters.");
                System.out.print("Please enter product description: ");
                productInfo = scanner.nextLine();
            }
        }
        else if(info.equals("price")){
            while(true){
                try{
                    float trial = Integer.parseInt(productInfo);
                    break;
                }
                catch(Exception e){}
                System.out.print("Your input was invalid, please enter a float number: ");
                productInfo = scanner.nextLine();
            }
        }
        return productInfo;
    }
    public static void createProduct(String productName, String productDescription, float productPrice){
        // Buradan devam!
    }
    public static void handle9(){
        String productName = getProductInfo("name");
        String productDescription = getProductInfo("description");
        float productPrice = Float.parseFloat(getProductInfo("price"));

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
            scanner.close();
            closeConnection();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
