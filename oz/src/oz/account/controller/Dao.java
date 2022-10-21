package oz.account.controller;

import java.util.List;

import oz.account.model.Accounts;
import oz.account.model.Expense;
import oz.account.model.Import;
import oz.account.model.Income;
import oz.account.model.OzUser;
import oz.account.model.Spend;
import oz.account.model.Transfer;


public interface Dao {
    List<OzUser> readAllOzUser();
    List<Accounts> readAllAccounts();
    List<Spend> readAllSpend();    
    List<Import> readAllImport();
    List<Expense> readAllExpense();
    List<Income> readAllIncome();
    List<Transfer> readAllTransfer();
    
    int insertAccounts(Accounts account);
    int insertExpense(String expenseName);
    int insertIncome(String incomeName);
    int insertSpend(Spend spend);
    int insertImport(Import impor);
    int insertTransfer(Transfer transfer);
    
    int updateSpend(String accountName, int cost);
    int updateImport(String accountName, int cost);
    int updateWallet(int balance);
    int updateBudget(int budget);
    
    int deleteAccounts(String accountName);
    int deleteExpense(String expenseName);
    int deleteIncome(String incomeName);

}
