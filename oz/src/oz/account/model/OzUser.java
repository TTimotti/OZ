package oz.account.model;

public class OzUser {
    public interface Entity {
        String TBL_OZUSER = "OZUSER";
        String TBL_OZUSER_G = "OZUSER o";

        String COL_OZNAME = "OZNAME";
        String COL_OZNAME_G = "o.OZNAME";
        String COL_BUDGET = "BUDGET";
        String COL_BUDGET_G = "o.BUDGET";
    }

    private int budget;

    public int getBudget() {
        return budget;
    }

    public OzUser() {
    }

    public OzUser(int budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "OzUser [budget=" + budget + "]";
    }

}
