package oz.account.model;

public class Expense {
    public interface Entity {
        String TBL_EXPENSE = "EXPENSE";
        String TBL_EXPENSE_G = "EXPENSE e";

        String COL_EX_NAME = "NAME";
        String COL_EX_NAME_G = "e.NAME";
        String COL_EX_OZNAME = "OZNAME";
        String COL_EX_OZNAME_G = "e.OZNAME";
    }

    private String name;

    public String getName() {
        return name;
    }

    public Expense() {
    }

    public Expense(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Expense [name=" + name + "]";
    }

}
