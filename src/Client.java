import java.util.ArrayList;
import java.util.Scanner;

interface Open{
    void openUsbStickToken();
    void openSmartCardToken();
}
interface OS{
    void cardInsertion();
    void writingProcess();
    void readingProcess();
}

class USBStick implements OS {
    buildAdapter adapter1 = new buildAdapter();
    private boolean connector = false ; //connectionun bağlı veya değil olduğunu tespit için
    private USBActions usbActions ;

    public USBStick(USBActions usbActions) {
        this.usbActions = usbActions;
    }

    // public void openFile(){

    //  System.out.println("File has been opened");
    //}

    public void closeFile() {
        System.out.println("File has been closed");
    }
    @Override
    public void cardInsertion() {
        connector = true  ;
        AbstractAdapterFactory UsbToken = new UsbTokenAdapter("UsbToken is insterted");
        adapter1.createAdapter(UsbToken);
    }

    @Override
    public void writingProcess() {
        if(connector){
            System.out.println("Writing process is starting...");
            //     System.out.println("Your data is wrote on your USB Card");
            usbActions.WriteActions();
        }else{
            System.out.println("Please connect your USB Stick");
        }
    }

    @Override
    public void readingProcess() {
        if(connector){
            System.out.println("Reading Process is starting...");
            System.out.println("Reading actions will be start");
            usbActions.ReadActions();
        }else{
            System.out.println("Please connect your USB Stick");
        }
    }

}
class SmartCard {
    private final SmartCardActions smartCardActions ;
    buildAdapter adapter2 = new buildAdapter();
    public SmartCard(SmartCardActions smartCardActions) {
        this.smartCardActions = smartCardActions;
    }

    private boolean connector = false ;

    public void cardInsertion() {

        connector = true ;
        // System.out.println("Smart Card is connected");
        // System.out.println("------------------------");
        // System.out.println();
        AbstractAdapterFactory SmartCard = new SmartCardAdapter("SmartCard is inserted");
        adapter2.createAdapter(SmartCard);
    }

    public void smartCardWriting() {
        if(connector){
            System.out.println("Writing proces is working for Smart Card");
            System.out.println("---------------------------------");
            //  System.out.println("Your data is wrote on your Smart Card");
            smartCardActions.WriteActions();
        }else {
            System.out.println("Please connect your smart card");
        }
    }

    public void smartCardReading() {
        if(connector){
            System.out.println("Reading process is working for Smart Card");
            System.out.println("----------------------------------");
            smartCardActions.ReadActions();
        }else {
            System.out.println("Please connect your smart card");
        }
    }
}

class OSToSmartCardAdapter implements OS{
    private final SmartCard smartCard ;

    public OSToSmartCardAdapter(SmartCard smartCard) {
        this.smartCard = smartCard ;
    }

    @Override
    public void cardInsertion() {
        smartCard.cardInsertion();
    }
    @Override
    public void writingProcess() {
        smartCard.smartCardWriting();
    }
    @Override
    public void readingProcess() {
        smartCard.smartCardReading();
    }
}

//Template pattern starts below

abstract class Template{

    public void ReadActions(){
//        verifyPIN();
//        openFile();
        selectFile();
        readData();
        decryptData();
        //closeFile();
    }
    public void WriteActions(){
//         openFile();
//         verifyPIN();
        selectFile();
        encryptData();
        writeData();
        //  decryptData();


    }

    //  protected  abstract void openFile();
    //  protected  abstract boolean verifyPIN();
    protected  abstract void selectFile();
    protected  final void encryptData(){
        System.out.println("Encrypting...");
    }
    protected  abstract void readData();
    protected abstract void writeData();
    protected  final void decryptData(){
        System.out.println("Decrypting...");
    }
//    protected  abstract void closeFile();

}

class SmartCardActions extends Template {

    //    @Override
//    public void openFile() {
//        hook();
//    }
    public boolean enterPIN(){
        if(1234==1234)
            return true;
        else
            return false;
    }


    public void verifyPIN() {
        if(enterPIN()){
            System.out.println("Your pin is verified ");

        }else{
            System.out.println("Your pin is rejected from system...");

        }
    }
    protected void selectFile() {
        if(enterPIN())
            System.out.println("File is selected...");
        else{
            System.out.println("File couldn't select");
        }
    }
    //    protected void encryptData() {
//
////        if(verifyPIN() )
////            System.out.println("Data is encrypted");
////        else {
////            System.out.println("Data couldn't select");
////        }
    //}
    protected void readData() {
        System.out.println("Data is reading***");
    }

    protected void writeData(){
        System.out.println("Data is writing to the card***");
        System.out.println("************");
        System.out.println("Data has written.");
        System.out.println("****************\n\n");
    }



//    protected void decryptData() {
//        System.out.println("Decrypting....");
////        if(verifyPIN())
////            System.out.println("Decrypting...");
////        else{
////            System.out.println("....");
////        }
//    }

    //   @Override
    //   protected void closeFile() {
//        hook();
    //   }

//    protected void hook(){
//        //Boş dönmesi için
//        System.out.println();
//    }
}

class USBActions extends Template {

    //  //  @Override
//    public void openFile() {
//        System.out.println("File is opened");
//    }
//    public boolean verifyPIN(){
//        hook();
//        return true ;
//    }
    protected void selectFile() {
        System.out.println("File is oppening...");
    }
    //    protected void encryptData() {
//        System.out.println("Data is encrypted");
//    }
    protected void readData() {
        System.out.println("Data is reading");
    }
    protected void writeData(){
        System.out.println("Data is writing to the card***");
    }
    //    protected void decryptData() {
//        System.out.println("Decrypting...");
//    }
    protected void closeFile() {
        System.out.println("File is closed");
    }

    protected void hook(){}

}
class Access implements Open{

    static ArrayList<buildAdapter> adapters = new ArrayList<>(4);

    Scanner scanner = new Scanner(System.in);

    // Hem usb hem de smart cardın çalıştırıldığı yer
    public Access() {


        usbActions = new USBActions();
        usbStick = new USBStick(usbActions);

        smartCardActions = new SmartCardActions();
        smartCARD = new SmartCard(smartCardActions);
        smartCard = new OSToSmartCardAdapter(smartCARD);
    }
    @Override
    public void openUsbStickToken() {
        adapters.add(usbStick.adapter1);
        System.out.println(adapters.size()+" port is using. "+(4-adapters.size())+" port is left.");
        //sockets.add(usbStick);
        System.out.println("If you want to use token press 1 or add new information press 2");
        int a = scanner.nextInt();
        if(a==1){
            usbStick.cardInsertion();
            usbStick.readingProcess();
            usbStick.closeFile();
        }
        else if(a==2){
            usbStick.cardInsertion();
            usbStick.writingProcess();
            usbStick.closeFile();
        }


    }
    @Override
    public void openSmartCardToken() {
        adapters.add(smartCARD.adapter2);
        System.out.println(adapters.size()+" port is using. "+(4-adapters.size())+" port is left.");
        //sockets.add(smartCard);
        System.out.println("If you want to use token press 1 or add new information press 2");
        int a = scanner.nextInt();
        if(a==1) {
            smartCard.cardInsertion();
            smartCardActions.verifyPIN();
            smartCard.readingProcess();
        }

        else if(a==2) {
            smartCard.cardInsertion();
            smartCardActions.verifyPIN();
            smartCard.writingProcess();
        }
    }


    private USBStick usbStick ;
    private USBActions usbActions;
    private SmartCard smartCARD ;
    private OSToSmartCardAdapter smartCard ;
    private SmartCardActions smartCardActions;

    ArrayList <OS> sockets = new ArrayList<OS>(4);
}

class Singleton extends Access{

    private static Singleton instance;

    private Singleton(){}

    public static Singleton getInstance(){

        if (instance == null){
            instance = new Singleton();
        }
        return instance;
    }
}
abstract  class AbstractAdapterFactory{
    abstract String cardInsertion();

}
class SmartCardAdapter extends AbstractAdapterFactory{
    String a;
    @Override
    String cardInsertion() {
        return a;
    }
    public SmartCardAdapter(String instertion){
        a = instertion;


    }
}
class UsbTokenAdapter extends AbstractAdapterFactory{
    String a;
    @Override
    String cardInsertion() {
        return a;
    }
    public UsbTokenAdapter(String instertion){
        a = instertion;


    }
}
class buildAdapter{
    public void createAdapter(AbstractAdapterFactory adapterFactory){
        ArrayList<AbstractAdapterFactory> x = new ArrayList<>(4);
        x.add(adapterFactory);
        System.out.println(adapterFactory.cardInsertion());

    }
}



public class Client {
    public static void main(String [] args) {

        // AbstractAdapterFactory SmartCard = new SmartCardAdapter("SmartCard is inserted");

        //adapter.createAdapter(SmartCard);

        Singleton singleton = Singleton.getInstance();


        Scanner scan = new Scanner(System.in); //Scanner for user input
        while (true) {
            System.out.println("Please choose your insertion");
            System.out.println("1 Smart Card");
            System.out.println("2 USB Stick");
            System.out.println("Press 3 to quit");
            System.out.println();

            int selection;
            System.out.println("What is your selection?");
            selection = scan.nextInt();

            if (selection == 1) {
                singleton.openSmartCardToken(); //Singletondan aldığımız  smart card objeyi çağırıyoruz

            } else if (selection == 2) {
                singleton.openUsbStickToken(); //Singletondan aldığımız usb stick objeyi çağırıyoruz

            }
            else if(selection == 3)
                break;
            else if (Access.adapters.size()==4){
                System.out.println("You are using all of ports on the computer");
                break;
            }

            else
                System.out.println("You entered an invalid value");


//        singleton.openUsbStickToken();
//        Open usbStick = new Access();
//        usbStick.openUsbStickToken();
//
//        System.out.println();
//        System.out.println("***********************************");
//        System.out.println();
//
//        Open smartCard = new Access();
//        smartCard.openSmartCardToken();


//asdasdasdasdasdas
            //COMMİT TEST
        }
        // commit test2
        //commit test3
    }


}