package oz.account.controller;

import static oz.account.model.Accounts.Entity.*;
import static oz.account.model.Expense.Entity.*;
import static oz.account.model.Import.Entity.*;
import static oz.account.model.Income.Entity.*;
import static oz.account.model.OzUser.Entity.*;
import static oz.account.model.Spend.Entity.*;
import static oz.account.model.Transfer.Entity.*;


public interface JdbcSql {
   
    String SQL_READ_ALL_OZUSER = String.format("select * from %s order by %s desc", TBL_OZUSER, COL_OZNAME);
    String SQL_READ_ALL_ACCOUNTS = String.format("select * from %s", TBL_ACCOUNTS);
    String SQL_READ_ALL_SPEND = String.format("select * from %s order by %s desc", TBL_SPEND, COL_SP_PAYDATE);
    String SQL_READ_ALL_IMPORT = String.format("select * from %s order by %s desc", TBL_IMPORT, COL_IMP_ACNTNAME);
    String SQL_READ_ALL_EXPENSE = String.format("select * from %s order by %s desc", TBL_EXPENSE, COL_EX_NAME);
    String SQL_READ_ALL_INCOME = String.format("select * from %s order by %s desc", TBL_INCOME, COL_IN_NAME);
    String SQL_READ_ALL_TRANSFER = String.format("select * from %s order by %s desc", TBL_TRANSFER, COL_TR_PAYDATE);
    
//    String SQL_READ_OZUSER_BY_OZNAME = String.format("select * from %s where %s like ?", TBL_OZUSER, COL_OZNAME);
//    String SQL_READ_ACCOUNTS_BY_ACNTNAME = String.format("select * from %s where %s like ?", TBL_ACCOUNTS, COL_ACC_ACNTNAME);
//    String SQL_READ_ACCOUNTS_BY_CARDNAME = String.format("select * from %s where %s like ?", TBL_ACCOUNTS, COL_ACC_CARDNAME);
//    String SQL_READ_SPEND_BY_ACNTNAME = String.format("select * from %s where %s like ?", TBL_SPEND, COL_SP_ACNTNAME);
//    String SQL_READ_SPEND_BY_CARDNAME = String.format("select * from %s where %s like ?", TBL_SPEND, COL_SP_CARDNAME);
//    String SQL_READ_IMPORT_BY_ACNTNAME = String.format("select * from %s where %s like ?", TBL_IMPORT, COL_IMP_ACNTNAME);
//    String SQL_READ_IMPORT_BY_CARDNAME = String.format("select * from %s where %s like ?", TBL_IMPORT, COL_IMP_CARDNAME);
//    String SQL_READ_EXPENSE_BY_NAME = String.format("select * from %s where %s like ?", TBL_EXPENSE, COL_EX_NAME);
//    String SQL_READ_INCOME_BY_NAME = String.format("select * from %s where %s like ?", TBL_INCOME, COL_IN_NAME);
//    String SQL_READ_TRANSFER_BY_SEND = String.format("select * from %s where %s like ?", TBL_TRANSFER, COL_TR_ACNTNAME_SEND);
//    String SQL_READ_TRANSFER_BY_RECEIVE = String.format("select * from %s where %s like ?", TBL_TRANSFER, COL_TR_ACNTNAME_RECEIVE);
    
    String SQL_INSERT_ACCOUNTS = String.format("insert into %s ( %s, %s, %s, %s, %s) values ( ?, ?, ?, ?, ?)",
            TBL_ACCOUNTS, COL_ACC_BANK, COL_ACC_ACNTNAME, COL_ACC_ACNTNO, COL_ACC_BALANCE, COL_ACC_CARDNAME);
    String SQL_INSERT_SPEND = String.format("insert into %s ( %s, %s, %s, %s, %s) values ( ?, ?, ?, ?, ?)",
            TBL_SPEND, COL_SP_PAYDATE, COL_SP_ACNTNAME, COL_SP_CARDNAME, COL_SP_EXPENSE, COL_SP_COST);
    String SQL_INSERT_IMPORT = String.format("insert into %s ( %s, %s, %s, %s) values ( ?, ?, ?, ?)",
            TBL_IMPORT, COL_IMP_PAYDATE, COL_IMP_ACNTNAME, COL_IMP_INCOME, COL_IMP_COST);
    String SQL_INSERT_EXPENSE = String.format("insert into %s (%s) values ( ? )",TBL_EXPENSE, COL_EX_NAME);
    String SQL_INSERT_INCOME = String.format("insert into %s (%s) values ( ? )",TBL_INCOME, COL_IN_NAME);
    String SQL_INSERT_TRANSFER = String.format("insert into %s ( %s, %s, %s, %s, %s) values ( ?, ?, ?, ?, ?)", 
            TBL_TRANSFER, COL_TR_PAYDATE, COL_TR_ACNTNAME_SEND, COL_TR_ACNTNAME_RECEIVE, COL_TR_CASH, COL_TR_MEMO);
    
    String SQL_DELETE_ACCOUNTS_BY_ACNTNAME = String.format("delete from %s where %s like ?", TBL_ACCOUNTS, COL_ACC_ACNTNAME);
    String SQL_DELETE_EXPENSE_BY_NAME = String.format("delete from %s where %s like ?", TBL_EXPENSE, COL_EX_NAME);
    String SQL_DELETE_INCOME_BY_NAME = String.format("delete from %s where %s like ?", TBL_INCOME, COL_IN_NAME);
    
    String SQL_UPDATE_OZUSER = String.format("update %s set %s = ?", TBL_OZUSER, COL_BUDGET);
    String SQL_UPDATE_WALLET = String.format("update %s set %s = ? where %s like ? ", TBL_ACCOUNTS, COL_ACC_BALANCE, COL_ACC_ACNTNO);
    String SQL_UPDATE_ACCOUNTS_SPEND = String.format("update %s set %s = (select %s from %s where %s like ?) - ? where %s like ?",
            TBL_ACCOUNTS, COL_ACC_BALANCE, COL_ACC_BALANCE, TBL_ACCOUNTS, COL_ACC_ACNTNAME, COL_ACC_ACNTNAME );
    String SQL_UPDATE_ACCOUNTS_IMPORT = String.format("update %s set %s = (select %s from %s where %s like ?) + ? where %s like ?",
            TBL_ACCOUNTS, COL_ACC_BALANCE, COL_ACC_BALANCE, TBL_ACCOUNTS, COL_ACC_ACNTNAME, COL_ACC_ACNTNAME );


    
    
    

}
