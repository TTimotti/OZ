package oz.account.model;

import java.time.LocalDateTime;

public class Spend {
    public interface Entity {
        String TBL_SPEND = "SPEND";
        String TBL_SPEND_G = "SPEND s";

        String COL_SP_OZNAME = "OZNAME";
        String COL_SP_OZNAME_G = "s.OZNAME";
        String COL_SP_PAYDATE = "PAY_DATE";
        String COL_SP_PAYDATE_G = "s.PAY_DATE";
        String COL_SP_ACNTNAME = "ACNTNAME";
        String COL_SP_ACNTNAME_G = "s.ACNTNAME";
        String COL_SP_CARDNAME = "CARDNAME";
        String COL_SP_CARDNAME_G = "s.CARDNAME";
        String COL_SP_EXPENSE = "EXPENSE";
        String COL_SP_EXPENSE_G = "s.EXPENSE";
        String COL_SP_COST = "COST";
        String COL_SP_COST_G = "s.COST";
    }

    private LocalDateTime payDate;
    private String accountName;
    private String cardName;
    private String expense;
    private int cost;

    public LocalDateTime getPayDate() {
        return payDate;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getCardName() {
        return cardName;
    }

    public String getExpense() {
        return expense;
    }

    public int getCost() {
        return cost;
    }

    public Spend() {
    }

    public Spend(LocalDateTime payDate, String accountName, String cardName, String expense, int cost) {
        this.payDate = payDate;
        this.accountName = accountName;
        this.cardName = cardName;
        this.expense = expense;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Spend [payDate=" + payDate + ", accountName=" + accountName + ", cardName=" + cardName + ", expense="
                + expense + ", cost=" + cost + "]";
    }

}
