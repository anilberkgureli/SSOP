package SSOP;

public interface SmartCard {

    void selectFile();
    void waitForCardInsertion();
    void readCard();
    void writeCard();
    void decryptData();
    void encryptData();
    void verifyPIN();
    void deleteDFile();

}

