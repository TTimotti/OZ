package oz.account.model;

public class Accounts {

    public interface Entity {
        String TBL_ACCOUNTS = "ACCOUNTS";
        String TBL_ACCOUNTS_G = "ACCOUNTS a";

        String COL_ACC_OZNAME = "OZNAME";
        String COL_ACC_OZNAME_G = "a.OZNAME";
        String COL_ACC_BANK = "BANK";
        String COL_ACC_BANK_G = "a.BANK";
        String COL_ACC_ACNTNAME = "ACNTNAME";
        String COL_ACC_ACNTNAME_G = "a.ACNTNAME";
        String COL_ACC_ACNTNO = "ACNTNO";
        String COL_ACC_ACNTNO_G = "a.ACNTNO";
        String COL_ACC_BALANCE = "BALANCE";
        String COL_ACC_BALANCE_G = "a.BALANCE";
        String COL_ACC_CARDNAME = "CARDNAME";
        String COL_ACC_CARDNAME_G = "a.CARDNAME";

    }

    private String bank;
    private String accountName;
    private String accountNo;
    private int balance;
    private String cardName;

    public Accounts() {
    }

    public Accounts(String bank, String accountName, String accountNo, int balance, String cardName) {
        this.bank = bank;
        this.accountName = accountName;
        this.accountNo = accountNo;
        this.balance = balance;
        this.cardName = cardName;
    }

    public String getBank() {
        return bank;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public int getBalance() {
        return balance;
    }

    public String getCardName() {
        return cardName;
    }

    @Override
    public String toString() {
        return "Accounts [bank=" + bank + ", accountName=" + accountName + ", accountNo=" + accountNo + ", balance="
                + balance + ", cardName=" + cardName + "]";
    }

}
