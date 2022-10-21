package oz.account.controller;

import static oz.account.OracleJdbc.*;
import static oz.account.model.Accounts.Entity.*;
import static oz.account.model.Expense.Entity.*;
import static oz.account.model.Import.Entity.*;
import static oz.account.model.Income.Entity.*;
import static oz.account.model.OzUser.Entity.*;
import static oz.account.model.Spend.Entity.*;
import static oz.account.model.Transfer.Entity.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static oz.account.controller.JdbcSql.*;
import oracle.jdbc.driver.OracleDriver;

import oz.account.model.Accounts;
import oz.account.model.Expense;
import oz.account.model.Import;
import oz.account.model.Income;
import oz.account.model.OzUser;
import oz.account.model.Spend;
import oz.account.model.Transfer;

public class DaoImpl implements Dao {

    // single-ton
    private static DaoImpl instance = null;

    private DaoImpl() {
    }

    public static DaoImpl getInstance() {
        if (instance == null) {
            instance = new DaoImpl();
        }

        return instance;
    }

    // ojdbc methods
    private Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new OracleDriver());

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private void closeResources(Connection conn, Statement stmt) throws SQLException {
        stmt.close();
        conn.close();
    }

    private void closeResources(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
        rs.close();
        closeResources(conn, stmt);
    }

    // override methods

    @Override
    public List<OzUser> readAllOzUser() {
        List<OzUser> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_READ_ALL_OZUSER);

            rs = stmt.executeQuery();
            while (rs.next()) {
                int cost = rs.getInt(COL_BUDGET);

                OzUser o = new OzUser(cost);
                list.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<Accounts> readAllAccounts() {
        List<Accounts> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_READ_ALL_ACCOUNTS);

            rs = stmt.executeQuery();
            while (rs.next()) {

                String bank = rs.getString(COL_ACC_BANK);
                String accountName = rs.getString(COL_ACC_ACNTNAME);
                String accountNo = rs.getString(COL_ACC_ACNTNO);
                int balance = rs.getInt(COL_ACC_BALANCE);
                String cardName = rs.getString(COL_ACC_CARDNAME);

                Accounts o = new Accounts(bank, accountName, accountNo, balance, cardName);
                list.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<Spend> readAllSpend() {
        List<Spend> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_READ_ALL_SPEND);

            rs = stmt.executeQuery();
            while (rs.next()) {

                LocalDateTime payDate = rs.getTimestamp(COL_SP_PAYDATE).toLocalDateTime();
                String accountName = rs.getString(COL_SP_ACNTNAME);
                String cardName = rs.getString(COL_SP_CARDNAME);
                String expense = rs.getString(COL_SP_EXPENSE);
                int cost = rs.getInt(COL_SP_COST);

                Spend o = new Spend(payDate, accountName, cardName, expense, cost);
                list.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<Import> readAllImport() {
        List<Import> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_READ_ALL_IMPORT);

            rs = stmt.executeQuery();
            while (rs.next()) {

                LocalDateTime payDate = rs.getTimestamp(COL_IMP_PAYDATE).toLocalDateTime();
                String accountName = rs.getString(COL_IMP_ACNTNAME);
                String income = rs.getString(COL_IMP_INCOME);
                int cost = rs.getInt(COL_IMP_COST);

                Import o = new Import(payDate, accountName, income, cost);
                list.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<Expense> readAllExpense() {
        List<Expense> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_READ_ALL_EXPENSE);

            rs = stmt.executeQuery();
            while (rs.next()) {                
                String expense = rs.getString(COL_EX_NAME);                

                Expense o = new Expense(expense);
                list.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<Income> readAllIncome() {
        List<Income> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_READ_ALL_INCOME);

            rs = stmt.executeQuery();
            while (rs.next()) {                
                String income = rs.getString(COL_IN_NAME);                

                Income o = new Income(income);
                list.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<Transfer> readAllTransfer() {
        List<Transfer> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_READ_ALL_TRANSFER);

            rs = stmt.executeQuery();
            while (rs.next()) {

                LocalDateTime payDate = rs.getTimestamp(COL_TR_PAYDATE).toLocalDateTime();
                String sendAccountName = rs.getString(COL_TR_ACNTNAME_SEND);
                String receiveAccountName = rs.getString(COL_TR_ACNTNAME_RECEIVE);
                String memo = rs.getString(COL_TR_MEMO);
                int cash = rs.getInt(COL_TR_CASH);

                Transfer o = new Transfer(payDate, sendAccountName, receiveAccountName, cash, memo);
                list.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public int updateWallet(int balance) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int result = 0;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE_WALLET);
            stmt.setInt(1, balance);
            stmt.setString(2, "내 지갑");
            result = stmt.executeUpdate();            

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public int updateBudget(int budget) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int result = 0;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE_OZUSER);
            stmt.setInt(1, budget);
            result = stmt.executeUpdate();            

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public int insertExpense(String expenseName) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int result = 0;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT_EXPENSE);
            stmt.setString(1, expenseName);
            result = stmt.executeUpdate();            

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public int insertIncome(String incomeName) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int result = 0;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT_INCOME);
            stmt.setString(1, incomeName);
            result = stmt.executeUpdate();            

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public int deleteExpense(String expenseName) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int result = 0;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_DELETE_EXPENSE_BY_NAME);
            stmt.setString(1, expenseName);
            result = stmt.executeUpdate();            

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public int deleteIncome(String incomeName) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int result = 0;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_DELETE_INCOME_BY_NAME);
            stmt.setString(1, incomeName);
            result = stmt.executeUpdate();            

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public int insertAccounts(Accounts account) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int result = 0;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT_ACCOUNTS);
            stmt.setString(1, account.getBank());
            stmt.setString(2, account.getAccountName());
            stmt.setString(3, account.getAccountNo());
            stmt.setInt(4, account.getBalance());
            stmt.setString(5, account.getCardName());
            
            result = stmt.executeUpdate();            

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public int deleteAccounts(String accountName) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int result = 0;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_DELETE_ACCOUNTS_BY_ACNTNAME);
            stmt.setString(1, accountName);
            result = stmt.executeUpdate();            

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public int insertSpend(Spend spend) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int result = 0;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT_SPEND);
            stmt.setTimestamp(1, Timestamp.valueOf(spend.getPayDate()));
            stmt.setString(2, spend.getAccountName());
            stmt.setString(3, spend.getCardName());
            stmt.setString(4, spend.getExpense());
            stmt.setInt(5, spend.getCost());
            
            result = stmt.executeUpdate();            

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public int updateSpend(String accountName, int cost) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int result = 0;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE_ACCOUNTS_SPEND);

            stmt.setString(1, accountName);
            stmt.setInt(2, cost);
            stmt.setString(3, accountName);
            
            
            result = stmt.executeUpdate();            

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public int insertImport(Import impor) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int result = 0;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT_IMPORT);
            stmt.setTimestamp(1, Timestamp.valueOf(impor.getPayDate()));
            stmt.setString(2, impor.getAccountName());
            stmt.setString(3, impor.getIncome());
            stmt.setInt(4, impor.getCost());
            
            result = stmt.executeUpdate();            

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public int updateImport(String accountName, int cost) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int result = 0;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE_ACCOUNTS_IMPORT);

            stmt.setString(1, accountName);
            stmt.setInt(2, cost);
            stmt.setString(3, accountName);
            
            
            result = stmt.executeUpdate();            

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public int insertTransfer(Transfer transfer) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int result = 0;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT_TRANSFER);
            stmt.setTimestamp(1, Timestamp.valueOf(transfer.getPayDate()));
            stmt.setString(2, transfer.getSendAccountName());
            stmt.setString(3, transfer.getReceiveAccountName());
            stmt.setInt(4, transfer.getCash());
            stmt.setString(5, transfer.getMemo());
            
            result = stmt.executeUpdate();            

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
