package oz.account.model;

import java.time.LocalDateTime;

public class Import {
    public interface Entity {
        String TBL_IMPORT = "IMPORT";
        String TBL_IMPORT_G = "IMPORT m";

        String COL_IMP_OZNAME = "OZNAME";
        String COL_IMP_OZNAME_G = "m.OZNAME";
        String COL_IMP_PAYDATE = "PAY_DATE";
        String COL_IMP_PAYDATE_G = "m.PAY_DATE";
        String COL_IMP_ACNTNAME = "ACNTNAME";
        String COL_IMP_ACNTNAME_G = "m.ACNTNAME";
        String COL_IMP_INCOME = "INCOME";
        String COL_IMP_INCOME_G = "m.INCOME";
        String COL_IMP_COST = "COST";
        String COL_IMP_COST_G = "m.COST";
    }

    private LocalDateTime payDate;
    private String accountName;
    private String income;
    private int cost;

    public LocalDateTime getPayDate() {
        return payDate;
    }

    public String getAccountName() {
        return accountName;
    }


    public String getIncome() {
        return income;
    }

    public int getCost() {
        return cost;
    }

    public Import() {
    }

    public Import(LocalDateTime payDate, String accountName, String income, int cost) {
        this.payDate = payDate;
        this.accountName = accountName;
        this.income = income;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Import [payDate=" + payDate + ", accountName=" + accountName + ", income="
                + income + ", cost=" + cost + "]";
    }
    

}