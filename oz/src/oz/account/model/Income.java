package oz.account.model;

public class Income {
    public interface Entity {
        String TBL_INCOME = "INCOME";
        String TBL_INCOME_G = "INCOME i";

        String COL_IN_NAME = "NAME";
        String COL_IN_NAME_G = "i.NAME";
        String COL_IN_OZNAME = "OZNAME";
        String COL_IN_OZNAME_G = "i.OZNAME";
    }

    private String name;

    public String getName() {
        return name;
    }

    public Income() {
    }

    public Income(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Income [name=" + name + "]";
    }

}
