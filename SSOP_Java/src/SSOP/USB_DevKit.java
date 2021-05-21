package SSOP;

public interface USB_DevKit {

    void waitForUSBTokenInsertion();
    void openFile();
    void closeFile();
    void readData();
    void writeData();
    void decryptData();
    void encryptData();
    void deleteFile();




}
