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

    private boolean connector = false ; //connectionun bağlı veya değil olduğunu tespit için
    private USBActions usbActions ;

    public USBStick(USBActions usbActions) {
        this.usbActions = usbActions;
    }

    public void openFile(){
        System.out.println("File has been opened");
    }

    public void closeFile() {
        System.out.println("File has been closed");
    }
    @Override
    public void cardInsertion() {
        connector = true  ;
        System.out.println("USB Stick is connected");
    }

    @Override
    public void writingProcess() {
        if(connector){
            System.out.println("Data is writing...");
            System.out.println("Your data is wrote on your USB Card");
            usbActions.WriteActions();
        }else{
            System.out.println("Please connect your USB Stick");
        }
    }

    @Override
    public void readingProcess() {
        if(connector){
            System.out.println("Data is reading...");
            System.out.println("Your data is read on your USB Card");
            usbActions.ReadActions();
        }else{
            System.out.println("Please connect your USB Stick");
        }
    }

}
class SmartCard {
    private final SmartCardActions smartCardActions ;

    public SmartCard(SmartCardActions smartCardActions) {
        this.smartCardActions = smartCardActions;
    }

    private boolean connector = false ;

    public void cardInsertion() {
        connector = true ;
        System.out.println("Smart Card is connected");
        System.out.println("------------------------");
        System.out.println();
    }

    public void smartCardWriting() {
        if(connector){
            System.out.println("Writing proces is working for Smart Card");
            System.out.println("---------------------------------");
            System.out.println("Your data is wrote on your Smart Card");
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
    protected  abstract void closeFile();

}

class SmartCardActions extends Template {

//    @Override
//    public void openFile() {
//        hook();
//    }


    public boolean verifyPIN() {
        if(1234 == 1234){
            System.out.println("Your pin is verified ");
            return true ;
        }else{
            System.out.println("Your pin is rejected from system...");
            return  false ;
        }
    }
    protected void selectFile() {
        if(verifyPIN())
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
        System.out.println("Data is writing***");
    }



//    protected void decryptData() {
//        System.out.println("Decrypting....");
////        if(verifyPIN())
////            System.out.println("Decrypting...");
////        else{
////            System.out.println("....");
////        }
//    }

    @Override
    protected void closeFile() {
//        hook();
    }

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
        System.out.println("File is selected...");
    }
    //    protected void encryptData() {
//        System.out.println("Data is encrypted");
//    }
    protected void readData() {
        hook();
    }
    protected void writeData(){
        hook();
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
        usbStick.cardInsertion();
        usbStick.openFile();
        sockets.add(usbStick);
        usbStick.readingProcess();
        usbStick.writingProcess();
        usbStick.closeFile();

    }
    @Override
    public void openSmartCardToken() {
        smartCard.cardInsertion();
        smartCardActions.verifyPIN();
        sockets.add(smartCard);
        smartCard.readingProcess();
        smartCard.writingProcess();
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


public class Client {
    public static void main(String [] args){
        Singleton singleton = Singleton.getInstance();


        Scanner scan = new Scanner(System.in); //Scanner for user input

        System.out.println("Please choose your insertion");
        System.out.println("1 Smart Card");
        System.out.println("2 USB Stick");
        System.out.println();

        int selection ;
        System.out.println("What is your selection?");
        selection = scan.nextInt();

        if (selection == 1 ){
            singleton.openSmartCardToken(); //Singletondan aldığımız  smart card objeyi çağırıyoruz

        }
        else if (selection == 2){
            singleton.openUsbStickToken(); //Singletondan aldığımız usb stick objeyi çağırıyoruz

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



    }
}
