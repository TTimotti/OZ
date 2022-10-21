package oz.account.model;

import java.time.LocalDateTime;

public class Transfer {
    public interface Entity {
        String TBL_TRANSFER = "TRANSFER";
        String TBL_TRANSFER_G = "TRANSFER t";

        String COL_TR_OZNAME = "OZNAME";
        String COL_TR_OZNAME_G = "t.OZNAME";
        String COL_TR_PAYDATE = "PAY_DATE";
        String COL_TR_PAYDATE_G = "t.PAY_DATE";
        String COL_TR_ACNTNAME_SEND = "ACNTNAME_SEND";
        String COL_TR_ACNTNAME_SEND_G = "t.ACNTNAME_SEND";
        String COL_TR_ACNTNAME_RECEIVE = "ACNTNAME_RECEIVE";
        String COL_TR_ACNTNAME_RECEIVE_G = "t.ACNTNAME_RECEIVE";
        String COL_TR_CASH = "CASH";
        String COL_TR_CASH_G = "t.CASH";
        String COL_TR_MEMO = "MEMO";
        String COL_TR_MEMO_G = "t.MEMO";
    }

    private LocalDateTime payDate;
    private String sendAccountName;
    private String receiveAccountName;
    private int cash;
    private String memo;

    public LocalDateTime getPayDate() {
        return payDate;
    }

    public String getSendAccountName() {
        return sendAccountName;
    }

    public String getReceiveAccountName() {
        return receiveAccountName;
    }

    public int getCash() {
        return cash;
    }

    public String getMemo() {
        return memo;
    }

    public Transfer() {
    }

    public Transfer(LocalDateTime payDate, String sendAccountName, String receiveAccountName, int cash, String memo) {
        this.payDate = payDate;
        this.sendAccountName = sendAccountName;
        this.receiveAccountName = receiveAccountName;
        this.cash = cash;
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "Transfer [payDate=" + payDate + ", sendAccountName=" + sendAccountName + ", receiveAccountName="
                + receiveAccountName + ", cash=" + cash + ", memo=" + memo + "]";
    }

}
