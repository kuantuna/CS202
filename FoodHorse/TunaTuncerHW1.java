import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

// Handle1()  DONE
// Handle2()  DONE
// Handle3()  DONE
// Handle4()  DONE     CAN BE IMPROVED
// Handle5()  DONE
// Handle6()  DONE
// Handle7()  DONE
// Handle8()  DONE
// Handle9()  DONE
// Handle10() DONE
// Handle11() DONE
// Handle12() DONE
// Handle13() NOTHING TO BE DONE

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
                if(userInput>=1 && userInput<=13) {
                    break;
                }
                else{
                    System.out.print("Your input is not between 1-13. Please enter a number between 1-13: ");
                    tempInput = scanner.nextLine();
                }
            }
            catch(Exception e) {
                System.out.print("Your input was invalid, please enter a number between 1-13: ");
                tempInput = scanner.nextLine();
            }
        }
    }
    public static String getCustomerInfo(String info){
        System.out.print("Please enter customer " + info +": ");
        String customerInfo = scanner.nextLine();
        if(info.equals("phone number")){
            while(true){
                if(customerInfo.length()==11) {
                    break;
                }
                System.out.println("Phone number must be 11 digits. Like (05551112233)");
                System.out.print("Please enter customer phone number: ");
                customerInfo = scanner.nextLine();
            }
        }
        else if(info.equals("name")||info.equals("surname")||info.equals("address")) {
            while(true){
                if(customerInfo.length()<=30) {
                    break;
                }
                System.out.println(info.substring(0,1).toUpperCase() + info.substring(1) + " can be up to 30 letters.");
                System.out.print("Please enter customer " + info +": ");
                customerInfo = scanner.nextLine();
            }
        }
        else if(info.equals("id")){
            boolean brk = false;
            while(true){
                try{
                    Integer.parseInt(customerInfo);
                    // I need to check if it exists in the db.
                    Statement statement = connection.createStatement();
                    String selectQuery = "SELECT * FROM Customer";
                    ResultSet resultSet = statement.executeQuery(selectQuery);
                    while(resultSet.next()){
                        int id = resultSet.getInt("cid");
                        if(id == Integer.parseInt(customerInfo)){
                            brk = true;
                            break;
                        }
                    }
                    if(brk){
                        break;
                    }
                    System.out.print("Your customer id is not in the database. Please enter a valid id: ");
                    customerInfo = scanner.nextLine();
                }
                catch(NumberFormatException nfe) {
                    System.out.print("Your input was invalid, please enter a number for customer id: ");
                    customerInfo = scanner.nextLine();
                }
                catch(SQLException sqlExc){
                    sqlExc.printStackTrace();
                }
            }
        }
        return customerInfo;
    }
    public static void addCustomer(String customerName, String customerSurname,
                                      String customerAddress, String customerPhoneNumber){
        try{
            Statement customerStatement = connection.createStatement();
            String addCustomerQuery = "INSERT INTO Customer(fname, lname, caddress, pnum) "
                    + "VALUES('" + customerName + "', '" + customerSurname + "', '" + customerAddress
                    + "', '" + customerPhoneNumber + "')";
            customerStatement.executeUpdate(addCustomerQuery);
            System.out.println("Customer has been added to the db successfully.");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static String getOrderDate(){
        System.out.print("Please enter order date. For ex(2020-01-27): ");
        String orderDate = scanner.nextLine();
        while(true){
            try{
                if(orderDate.length()==10) {
                    Integer.parseInt(orderDate.substring(8,10));
                    if(Integer.parseInt(orderDate.substring(8,10))<=31 && Integer.parseInt(orderDate.substring(8,10))>=0){
                        Integer.parseInt(orderDate.substring(5,7));
                        if(Integer.parseInt(orderDate.substring(5,7))<=12 && Integer.parseInt(orderDate.substring(5,7))>=0){
                            Integer.parseInt(orderDate.substring(0,4));
                            if(Integer.parseInt(orderDate.substring(0,4)) > 0){
                                break;
                            }
                            else{
                                System.out.println("Your year is not greater than 0");
                                System.out.print("Please enter order date");
                                orderDate = scanner.nextLine();
                            }
                        }
                        else{
                            System.out.println("Your month is not between 01-12");
                            System.out.print("Please enter order date");
                            orderDate = scanner.nextLine();
                        }
                    }
                    else{
                        System.out.println("Your day is not between 01-31");
                        System.out.print("Please enter order date");
                        orderDate = scanner.nextLine();
                    }
                }
                System.out.println("Order date must be 10 characters. Like (1964-03-18)");
                System.out.print("Please enter order date: ");
                orderDate = scanner.nextLine();
            }
            catch(Exception e){
                System.out.println("There is a problem with the date input, please provide a valid one with the" +
                        " (yyyy-mm-dd) format");
                System.out.print("Please enter order date: ");
                orderDate = scanner.nextLine();
            }
        }
        return orderDate;
    }
    public static void addOrder(int customerId, int productId, int branchId, String orderDate){
        try{
            Statement statement = connection.createStatement();
            String addCustomerQuery = "INSERT INTO Orders(odate, cid, pid, bid) "
                    + "VALUES('" + orderDate + "', '" + customerId + "', '" + productId
                    + "', '" + branchId + "')";
            statement.executeUpdate(addCustomerQuery);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static int getQuantity(int productId, int branchId){
        int quantity = 0;
        try{
            Statement statement = connection.createStatement();
            String selectQuery = "SELECT * FROM Stock";
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while(resultSet.next()){
                if(productId == resultSet.getInt("pid" )&& branchId == resultSet.getInt("bid")){
                    quantity = resultSet.getInt("quantity");
                    break;
                }
            }
        }
        catch(SQLException sqlExc){
            sqlExc.printStackTrace();
        }
        return quantity;
    }
    public static void uptadeStock(int productId, int branchId, int quantity){
        quantity--;
        try{
            Statement statement = connection.createStatement();
            String updateQuery = "UPDATE Stock " + "SET quantity = " + quantity
                    + " WHERE (pid = " + productId + " AND bid = " + branchId + ")";
            statement.executeUpdate(updateQuery);
        }
        catch(SQLException sqlExc){
            sqlExc.printStackTrace();
        }
    }
    public static void listCustomerInfo(String info){
        boolean firstEntrance = true;
        try{
            Statement statement = connection.createStatement();
            String selectQuery = "SELECT * FROM Customer";
            ResultSet resultSet = statement.executeQuery(selectQuery);
            if(info.equals("")) {
                while (resultSet.next()) {
                    if (firstEntrance) {
                        System.out.println("##############################################");
                        System.out.println("CUSTOMER INFORMATION");
                    }
                    firstEntrance = false;
                    int customerId = resultSet.getInt("cid");
                    String customerName = resultSet.getString("fname");
                    String customerSurname = resultSet.getString("lname");
                    String customerAddress = resultSet.getString("caddress");
                    String customerPhoneNumber = resultSet.getString("pnum");
                    System.out.println("ID:" + customerId + "\tName:" + customerName + "\tSurname:" + customerSurname
                            + "\tAddress:" + customerAddress + "\tPhoneNumber:" + customerPhoneNumber);
                }
                System.out.println("##############################################");
            }
            // List via phone number
            else{
                while(resultSet.next()){
                    if(info.equals(resultSet.getString("pnum"))){
                        if(firstEntrance){
                            System.out.println("##############################################");
                            System.out.println("CUSTOMER INFORMATION");
                        }
                        firstEntrance = false;
                        int customerId = resultSet.getInt("cid");
                        String customerName = resultSet.getString("fname");
                        String customerSurname = resultSet.getString("lname");
                        String customerAddress = resultSet.getString("caddress");
                        System.out.println("ID:" + customerId + "\tName:" + customerName + "\tSurname:" + customerSurname
                                + "\tAddress:" + customerAddress + "\tPhoneNumber:" + info);
                    }
                }
            }
        }
        catch(SQLException sqlExc){
            sqlExc.printStackTrace();
        }
    }
    public static void listCustomersOrders(int customerId){
        boolean firstEntrance = true;
        try{
            Statement statement = connection.createStatement();
            String selectQuery = "SELECT * FROM Orders";
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while(resultSet.next()){
                if(customerId == resultSet.getInt("cid")){
                    if(firstEntrance){
                        System.out.println("##############################################");
                        System.out.println("ORDER INFORMATION");
                    }
                    firstEntrance = false;
                    String orderDate = resultSet.getString("odate");
                    int productId = resultSet.getInt("pid");
                    int branchId = resultSet.getInt("bid");
                    System.out.println("OrderDate:" + orderDate+ "\tID:" + customerId + "\tProductID:" + productId
                            + "\tBranchID:" + branchId);
                }
            }
            System.out.println("##############################################");
        }
        catch(SQLException sqlExc){
            sqlExc.printStackTrace();
        }
    }
    public static ArrayList<String> getOrderDatesFromDb(int customerId){
        ArrayList<String> orderDates = new ArrayList<String>();
        try{
            Statement statement = connection.createStatement();
            String orderDateQuery = "SELECT * FROM Orders";
            ResultSet resultSet = statement.executeQuery(orderDateQuery);
            while(resultSet.next()){
                if(customerId == resultSet.getInt("cid")){
                    orderDates.add(resultSet.getString("odate"));
                }
            }
        }
        catch(SQLException sqlExc){
            sqlExc.printStackTrace();
        }
        return orderDates;
    }
    public static void listRecentOrders(ArrayList<String> customerDates){
        while(customerDates.size() > 5){
            customerDates.remove(customerDates.size()-1);
        }
        try{
            Statement statement = connection.createStatement();
            String orderQuery = "SELECT * FROM Orders";
            for(String date: customerDates){
                ResultSet resultSet = statement.executeQuery(orderQuery);
                while(resultSet.next()){
                    if(resultSet.getString("odate").equals(date)){
                        System.out.println("OrderDate:" + date + "\tCustomerId:" + resultSet.getInt("cid")
                                + "\tProductId:" + resultSet.getInt("pid") + "\tBranchId:"
                                + resultSet.getInt("bid"));
                    }
                }
            }
        }
        catch(SQLException sqlExc){
            sqlExc.printStackTrace();
        }
    }
    public static void listBranchInfo(){
        boolean firstEntrance = true;
        try{
            Statement statement = connection.createStatement();
            String selectQuery = "SELECT * FROM Branch";
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while(resultSet.next()){
                if(firstEntrance){
                    System.out.println("##############################################");
                    System.out.println("BRANCH INFORMATION");
                }
                firstEntrance = false;
                int branchId = resultSet.getInt("bid");
                String branchName = resultSet.getString("bname");
                String branchAddress = resultSet.getString("baddress");
                System.out.println("BranchID:" + branchId + "\tBranchName:" + branchName + "\tBranchAddress:" + branchAddress);
            }
            System.out.println("##############################################");
        }
        catch(SQLException sqlExc){
            sqlExc.printStackTrace();
        }
    }
    public static void listBranchsOrders(int branchId){
        boolean firstEntrance = true;
        try{
            Statement statement = connection.createStatement();
            String selectQuery = "SELECT * FROM Orders";
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while(resultSet.next()){
                if(branchId == resultSet.getInt("bid")){
                    if(firstEntrance){
                        System.out.println("##############################################");
                        System.out.println("ORDER INFORMATION");
                    }
                    firstEntrance = false;
                    String orderDate = resultSet.getString("odate");
                    int productId = resultSet.getInt("pid");
                    int customerId = resultSet.getInt("cid");
                    System.out.println("OrderDate:" + orderDate+ "\tID:" + customerId + "\tProductID:" + productId
                            + "\tBranchID:" + branchId);
                }
            }
            System.out.println("##############################################");
        }
        catch(SQLException sqlExc){
            sqlExc.printStackTrace();
        }
    }
    public static String getBranchInfo(String info){
        System.out.print("Please enter branch " + info +": ");
        String branchInfo = scanner.nextLine();
        if(info.equals("name")){
            while(true){
                if(branchInfo.length()<=50) {
                    break;
                }
                System.out.println("Name can be up to 50 letters.");
                System.out.print("Please enter branch name: ");
                branchInfo = scanner.nextLine();
            }
        }
        else if(info.equals("address")){
            while(true){
                if(branchInfo.length()<=30) {
                    break;
                }
                System.out.println("Address can be up to 30 letters.");
                System.out.print("Please enter branch address: ");
                branchInfo = scanner.nextLine();
            }
        }
        else if(info.equals("id")){
            boolean brk = false;
            while(true){
                try{
                    Integer.parseInt(branchInfo);
                    // I need to check if it exists in the db.
                    Statement statement = connection.createStatement();
                    String selectQuery = "SELECT * FROM Branch";
                    ResultSet resultSet = statement.executeQuery(selectQuery);
                    while(resultSet.next()){
                        int id = resultSet.getInt("bid");
                        if(id == Integer.parseInt(branchInfo)){
                            brk = true;
                            break;
                        }
                    }
                    if(brk){
                        break;
                    }
                    System.out.print("Your branch id is not in the database. Please enter a valid id: ");
                    branchInfo = scanner.nextLine();
                }
                catch(NumberFormatException nfe) {
                    System.out.print("Your input was invalid, please enter a number for branch id: ");
                    branchInfo = scanner.nextLine();
                }
                catch(SQLException sqlExc){
                    sqlExc.printStackTrace();
                }
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
    public static String getProductInfo(String info){
        System.out.print("Please enter product " + info +": ");
        String productInfo = scanner.nextLine();
        if(info.equals("name")){
            while(true){
                if(productInfo.length()<=50) {
                    break;
                }
                System.out.println("Name can be up to 50 letters.");
                System.out.print("Please enter product name: ");
                productInfo = scanner.nextLine();
            }
        }
        else if(info.equals("description")){
            while(true){
                if(productInfo.length()<=100) {
                    break;
                }
                System.out.println("Description can be up to 100 letters.");
                System.out.print("Please enter product description: ");
                productInfo = scanner.nextLine();
            }
        }
        else if(info.equals("price")){
            while(true){
                try{
                    Float.parseFloat(productInfo);
                    if(Float.parseFloat(productInfo)>0){
                        break;
                    }
                    else{
                        System.out.print("Price must be greater than 0. Please enter price: ");
                    }
                }
                catch(Exception e) {
                    System.out.print("Your input was invalid, please enter a float number: ");
                    productInfo = scanner.nextLine();
                }
            }
        }
        else if(info.equals("id")){
            boolean brk = false;
            while(true){
                try{
                    Integer.parseInt(productInfo);
                    // I need to check if it exists in the db.
                    Statement statement = connection.createStatement();
                    String selectQuery = "SELECT * FROM Product";
                    ResultSet resultSet = statement.executeQuery(selectQuery);
                    while(resultSet.next()){
                        int id = resultSet.getInt("pid");
                        if(id == Integer.parseInt(productInfo)){
                            brk = true;
                            break;
                        }
                    }
                    if(brk){
                        break;
                    }
                    System.out.print("Your product id is not in the database. Please enter a valid id: ");
                    productInfo = scanner.nextLine();
                }
                catch(NumberFormatException nfe) {
                    System.out.print("Your input was invalid, please enter a number for product id: ");
                    productInfo = scanner.nextLine();
                }
                catch(SQLException sqlExc){
                    sqlExc.printStackTrace();
                }
            }
        }
        return productInfo;
    }
    public static void createProduct(String productName, String productDescription, float productPrice){
        try{
            Statement productStatement = connection.createStatement();
            String addProductQuery = "INSERT INTO Product(pname, pdescription, price) "
                    + "VALUES('" + productName + "', '" + productDescription + "', '" + productPrice + "')";
            productStatement.executeUpdate(addProductQuery);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static String getStockInfo(String info){
        String stockInfo = "";
        if(info.equals("product") || info.equals("branch")){
            boolean brk = false;
            System.out.print("Please enter " + info + " id: ");
            stockInfo = scanner.nextLine();
            while(true){
                try{
                    Integer.parseInt(stockInfo);
                    // I need to check if it exists in the db.
                    Statement statement = connection.createStatement();
                    String selectQuery = "SELECT * FROM " + info;
                    ResultSet resultSet = statement.executeQuery(selectQuery);
                    while(resultSet.next()){
                        int id = resultSet.getInt(info.substring(0,1) + "id");
                        if(id == Integer.parseInt(stockInfo)){
                            brk = true;
                            break;
                        }
                    }
                    if(brk){
                        break;
                    }
                    System.out.print("Your " + info + " id is not in the database. Please enter a valid id: ");
                    stockInfo = scanner.nextLine();
                }
                catch(NumberFormatException nfe) {
                    System.out.print("Your input was invalid, please enter a number for " + info + " id: ");
                    stockInfo = scanner.nextLine();
                }
                catch(SQLException sqlExc){
                    sqlExc.printStackTrace();
                }
            }
        }
        else if(info.equals("quantity")){
            System.out.print("Please enter quantity: ");
            stockInfo = scanner.nextLine();
            while(true){
                try{
                    Integer.parseInt(stockInfo);
                    if(Integer.parseInt(stockInfo)>0){
                        break;
                    }
                    else{
                        System.out.print("Your quantity must be greater than 0. Please enter quantity:  ");
                        stockInfo = scanner.nextLine();
                    }
                }
                catch(NumberFormatException nfe) {
                    System.out.print("Your input was invalid, please enter a number for quantity: ");
                    stockInfo = scanner.nextLine();
                }
            }
        }
        return stockInfo;
    }
    public static void addStock(int quantity, int productId, int branchId){
        try{
            Statement stockStatement = connection.createStatement();
            String addStockQuery = "INSERT INTO Stock(quantity, pid, bid) "
                    + "VALUES('" + quantity + "', '" + productId + "', '" + branchId + "')";
            stockStatement.executeUpdate(addStockQuery);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static void deleteCustomersInfo(int customerId){
        try{
            Statement statement = connection.createStatement();
            String deleteQueryInOrders = "DELETE FROM Orders WHERE cid='" + customerId + "'";
            statement.executeUpdate(deleteQueryInOrders);
            String deleteQueryInCustomer = "DELETE FROM Customer WHERE cid='" + customerId + "'";
            statement.executeUpdate(deleteQueryInCustomer);
            System.out.println("Customer and her/his orders has been deleted successfully.");
        }
        catch(SQLException sqlExc){
            sqlExc.printStackTrace();
        }
    }


    public static void handle1(){
        String customerName = getCustomerInfo("name");
        String customerSurname = getCustomerInfo("surname");
        String customerAddress = getCustomerInfo("address");
        String customerPhoneNumber = getCustomerInfo("phone number");
        addCustomer(customerName, customerSurname, customerAddress, customerPhoneNumber);
    }


    public static void handle2(){
        int customerId = Integer.parseInt(getCustomerInfo("id"));
        int productId = Integer.parseInt(getProductInfo("id"));
        int branchId = Integer.parseInt(getBranchInfo("id"));
        String orderDate = getOrderDate();
        addOrder(customerId, productId, branchId, orderDate);
        int quantity = getQuantity(productId, branchId);
        uptadeStock(productId, branchId ,quantity);
    }


    public static void handle3(){
        listCustomerInfo("");
    }


    public static void handle4(){
        int customerId = Integer.parseInt(getCustomerInfo("id"));
        listCustomersOrders(customerId);
    }


    public static void handle5(){
        int customerId = Integer.parseInt(getCustomerInfo("id"));
        ArrayList<String> customerDates = getOrderDatesFromDb(customerId);
        Collections.sort(customerDates);
        Collections.reverse(customerDates);
        listRecentOrders(customerDates);
    }


    public static void handle6(){
        listBranchInfo();
    }


    public static void handle7(){
        int branchId = Integer.parseInt(getBranchInfo("id"));
        listBranchsOrders(branchId);
    }


    public static void handle8(){
        String branchName = getBranchInfo("name");
        String branchAddress = getBranchInfo("address");
        createBranch(branchName, branchAddress);
    }


    public static void handle9(){
        String productName = getProductInfo("name");
        String productDescription = getProductInfo("description");
        float productPrice = Float.parseFloat(getProductInfo("price"));
        createProduct(productName, productDescription, productPrice);
    }


    public static void handle10(){
        int productId = Integer.parseInt(getStockInfo("product"));
        int branchId = Integer.parseInt(getStockInfo("branch"));
        int quantity = Integer.parseInt(getStockInfo("quantity"));
        addStock(quantity, productId, branchId);
    }

    public static void handle11(){
        String customerPhoneNumber = getCustomerInfo("phone number");
        listCustomerInfo(customerPhoneNumber);
    }


    public static void handle12(){
        int customerId = Integer.parseInt(getCustomerInfo("id"));
        deleteCustomersInfo(customerId);
    }
    public static void handle13(){ }
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
