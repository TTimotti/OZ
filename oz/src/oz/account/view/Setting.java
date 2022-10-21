package oz.account.view;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import oz.account.controller.DaoImpl;
import oz.account.model.Accounts;
import oz.account.model.Expense;
import oz.account.model.Income;
import oz.account.model.OzUser;

public class Setting extends JFrame {

    public interface SettingListener {
        void onSetting();
    }

    private static final String[] ACC_COL_NAME = { "계좌명", "잔액" };
    private static final String[] EXPENSE_COL_NAME = { "지출 항목" };
    private static final String[] INCOME_COL_NAME = { "수입 항목" };
    private SettingListener listener;
    private JPanel contentPane;
    private Component parent;
    private JLabel accountLabel;
    private JTextField bankField;
    private JTextField accNameField;
    private JTextField accNoField;
    private JTextField balField;
    private JTextField cardNameField;
    private JButton saveBtn;
    private JLabel walletLabel;
    private JLabel budgetLabel;
    private JTextField walletField;
    private JTextField budgetField;
    private JButton saveBtn_2;
    private JTable accountsTable;
    private JTextField incomeField;
    private JTextField expenseField;
    private DefaultTableModel model;
    private JButton deleteExBtn;
    private JScrollPane scrollPane;
    private JScrollPane scrollPane_1;
    private JTable incomeTable;
    private JScrollPane scrollPane_2;
    private JTable expenseTable;
    private DaoImpl dao;

    /**
     * Launch the application.
     */
    public static void openSetting(Component parent, SettingListener listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Setting frame = new Setting(parent, listener);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Setting(Component parent, SettingListener listener) {
        this.parent = parent;
        this.listener = listener;
        dao = dao.getInstance();
        initialize();
        initializeAccountTable();
        initializeExpenseTable();
        initializeIncomeTable();
    }

    private void initializeIncomeTable() {
        model = new DefaultTableModel(null, INCOME_COL_NAME);
        incomeTable.setModel(model);
        List<Income> list = dao.readAllIncome();
        for (Income l : list) {
            Object[] row = { l.getName() };
            model.addRow(row);
        }

    }

    private void initializeExpenseTable() {
        model = new DefaultTableModel(null, EXPENSE_COL_NAME);
        expenseTable.setModel(model);
        List<Expense> list = dao.readAllExpense();
        for (Expense l : list) {
            Object[] row = { l.getName() };
            model.addRow(row);
        }

    }

    private void initializeAccountTable() {
        model = new DefaultTableModel(null, ACC_COL_NAME);
        accountsTable.setModel(model);
        List<Accounts> list = dao.readAllAccounts();
        for (Accounts l : list) {
            if (!l.getAccountName().equals("내 지갑")) {
                Object[] row = { l.getAccountName(), l.getBalance() };
                model.addRow(row);
            }
        }

    }

    public void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("설 정");
        int x = parent.getX();
        int y = parent.getY();
        setBounds(x - 50, y + 100, 650, 599);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel bankLabel = new JLabel("은 행 명");
        bankLabel.setBounds(22, 10, 52, 20);
        bankLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        contentPane.add(bankLabel);

        accountLabel = new JLabel("계 좌 명");
        accountLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        accountLabel.setBounds(22, 40, 52, 20);
        contentPane.add(accountLabel);

        JLabel accountNoLabel = new JLabel("계좌번호");
        accountNoLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        accountNoLabel.setBounds(12, 70, 61, 20);
        contentPane.add(accountNoLabel);

        JLabel cardNameLabel = new JLabel("카 드 명");
        cardNameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        cardNameLabel.setBounds(21, 133, 52, 20);
        contentPane.add(cardNameLabel);

        JLabel cardNameLabel_1 = new JLabel("계좌잔액");
        cardNameLabel_1.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        cardNameLabel_1.setBounds(12, 103, 61, 20);
        contentPane.add(cardNameLabel_1);

        bankField = new JTextField();
        bankField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        bankField.setBounds(85, 9, 159, 21);
        contentPane.add(bankField);
        bankField.setColumns(10);

        accNameField = new JTextField();
        accNameField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        accNameField.setColumns(10);
        accNameField.setBounds(85, 42, 159, 21);
        contentPane.add(accNameField);

        accNoField = new JTextField();
        accNoField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        accNoField.setColumns(10);
        accNoField.setBounds(85, 72, 159, 21);
        contentPane.add(accNoField);

        balField = new JTextField();
        balField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        balField.setColumns(10);
        balField.setBounds(85, 102, 126, 21);
        contentPane.add(balField);

        cardNameField = new JTextField();
        cardNameField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        cardNameField.setColumns(10);
        cardNameField.setBounds(85, 132, 159, 21);
        contentPane.add(cardNameField);

        saveBtn = new JButton("계좌 추가");
        saveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                createAccount();
            }
        });
        saveBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        saveBtn.setBounds(147, 163, 97, 23);
        contentPane.add(saveBtn);

        walletLabel = new JLabel("내 지갑");
        walletLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        walletLabel.setBounds(22, 197, 47, 20);
        contentPane.add(walletLabel);

        budgetLabel = new JLabel("당월 예산");
        budgetLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        budgetLabel.setBounds(12, 267, 61, 20);
        contentPane.add(budgetLabel);

        walletField = new JTextField();
        int wallet = 0;
        for (Accounts l : dao.readAllAccounts()) {
            if (l.getAccountName().equals("내 지갑")) {
                wallet = l.getBalance();
            }
        }
        walletField.setText(Integer.toString(wallet));
        walletField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        walletField.setColumns(10);
        walletField.setBounds(85, 196, 126, 21);
        contentPane.add(walletField);

        budgetField = new JTextField();
        int budget = 0;
        for (OzUser l : dao.readAllOzUser()) {
            budget = l.getBudget();
        }
        budgetField.setText(Integer.toString(budget));
        budgetField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        budgetField.setColumns(10);
        budgetField.setBounds(85, 266, 126, 21);
        contentPane.add(budgetField);

        saveBtn_2 = new JButton("예산 등록");
        saveBtn_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                InsertBudget();
            }
        });
        saveBtn_2.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        saveBtn_2.setBounds(147, 297, 97, 23);
        contentPane.add(saveBtn_2);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(260, 14, 362, 239);
        contentPane.add(scrollPane);

        accountsTable = new JTable();
        accountsTable.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        model = new DefaultTableModel(null, ACC_COL_NAME);
        accountsTable.setModel(model);
        scrollPane.setViewportView(accountsTable);

        JButton deleteBtn = new JButton("계좌 삭제");
        deleteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteAccount();
            }
        });
        deleteBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        deleteBtn.setBounds(525, 263, 97, 23);
        contentPane.add(deleteBtn);

        JLabel expenseLabel = new JLabel("지출 항목");
        expenseLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        expenseLabel.setBounds(12, 356, 62, 20);
        contentPane.add(expenseLabel);

        JLabel incomeLabel = new JLabel("수입 항목");
        incomeLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        incomeLabel.setBounds(12, 419, 62, 20);
        contentPane.add(incomeLabel);

        expenseField = new JTextField();
        expenseField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        expenseField.setColumns(10);
        expenseField.setBounds(85, 358, 159, 21);
        contentPane.add(expenseField);

        incomeField = new JTextField();
        incomeField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        incomeField.setColumns(10);
        incomeField.setBounds(85, 419, 159, 21);
        contentPane.add(incomeField);

        JButton createExpenseBtn = new JButton("지출 항목 추가");
        createExpenseBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertExpense();
            }
        });
        createExpenseBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        createExpenseBtn.setBounds(110, 386, 134, 23);
        contentPane.add(createExpenseBtn);

        JButton createIncomeBtn = new JButton("수입 항목 추가");
        createIncomeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertIncome();
            }
        });
        createIncomeBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        createIncomeBtn.setBounds(110, 450, 134, 23);
        contentPane.add(createIncomeBtn);

        scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(441, 296, 181, 221);
        contentPane.add(scrollPane_1);

        incomeTable = new JTable();
        incomeTable.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        model = new DefaultTableModel(null, INCOME_COL_NAME);
        incomeTable.setModel(model);
        scrollPane_1.setViewportView(incomeTable);

        JButton deleteInBtn = new JButton("수입 항목 삭제");
        deleteInBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteIncome();
            }
        });
        deleteInBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        deleteInBtn.setBounds(466, 527, 134, 23);
        contentPane.add(deleteInBtn);

        JLabel WonLabel = new JLabel("원");
        WonLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        WonLabel.setBounds(223, 103, 14, 20);
        contentPane.add(WonLabel);

        JLabel WonLabel_1 = new JLabel("원");
        WonLabel_1.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        WonLabel_1.setBounds(223, 196, 14, 20);
        contentPane.add(WonLabel_1);

        JLabel WonLabel_2 = new JLabel("원");
        WonLabel_2.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        WonLabel_2.setBounds(223, 267, 14, 20);
        contentPane.add(WonLabel_2);

        scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(259, 296, 181, 221);
        contentPane.add(scrollPane_2);

        expenseTable = new JTable();
        expenseTable.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        model = new DefaultTableModel(null, EXPENSE_COL_NAME);
        expenseTable.setModel(model);
        scrollPane_2.setViewportView(expenseTable);

        deleteExBtn = new JButton("지출 항목 삭제");
        deleteExBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteExpense();
            }
        });
        deleteExBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        deleteExBtn.setBounds(283, 527, 134, 23);
        contentPane.add(deleteExBtn);

        JButton walletBtn = new JButton("현금 등록");
        walletBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertWallet();
            }
        });
        walletBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        walletBtn.setBounds(147, 227, 97, 23);
        contentPane.add(walletBtn);
    }

    private void insertWallet() {
        int balance = 0;
        try {
            balance = Integer.parseInt(walletField.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "올바른 숫자를 입력하세요", "정수만 입력", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (walletField.getText().equals("")) {
            balance = 0;
        }
        int result = JOptionPane.showConfirmDialog(this, "지갑의 잔액을 등록 할까요?", "내 지갑 등록", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "내 지갑의 잔액을 등록했습니다");
            dao.updateWallet(balance);
            listener.onSetting();

        }

    }

    private void InsertBudget() {
        int budget = 0;

        try {
            budget = Integer.parseInt(budgetField.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "올바른 숫자를 입력하세요", "정수만 입력", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (walletField.getText().equals("")) {
            budget = 0;
        }
        int result = JOptionPane.showConfirmDialog(this, "예산을 등록 할까요?", "예산 등록", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "예산을 등록했습니다");
            dao.updateBudget(budget);
            listener.onSetting();

        }

    }

    private void insertExpense() {
        String expense = expenseField.getText();
        if (expense.equals("")) {
            JOptionPane.showMessageDialog(this, "등록 할 지출 항목을 먼저 입력하세요", "입력되지 않음", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int result = JOptionPane.showConfirmDialog(this, expense + "를 지출 항목 목록에 추가할까요?", "지출 항목 등록",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            int insert = dao.insertExpense(expense);
            if (insert > 0) {
                JOptionPane.showMessageDialog(this, expense + "를 추가 했습니다.");
                expenseField.setText("");
                initializeExpenseTable();
            }

        }
    }

    private void insertIncome() {
        String income = incomeField.getText();
        if (income.equals("")) {
            JOptionPane.showMessageDialog(this, "등록 할 수입 항목을 먼저 입력하세요", "입력되지 않음", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int result = JOptionPane.showConfirmDialog(this, income + "를 수입 항목 목록에 추가할까요?", "수입 항목 등록",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            int insert = dao.insertIncome(income);
            if (insert > 0) {
                JOptionPane.showMessageDialog(this, income + "를 추가 했습니다.");
                incomeField.setText("");
                initializeIncomeTable();
            }

        }

    }

    private void deleteExpense() {
        int index = expenseTable.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "삭제할 항목을 먼저 선택하세요", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String expense = expenseTable.getValueAt(index, 0).toString();
        int result = JOptionPane.showConfirmDialog(this, expense + "를 지출 항목 목록에서 삭제할까요?", "지출 항목 삭제",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {

            int delete = dao.deleteExpense(expense);
            if (delete > 0) {
                JOptionPane.showMessageDialog(this, expense + "지출 항목을 삭제 했습니다.");
                initializeExpenseTable();
            }
        }

    }

    private void deleteIncome() {
        int index = incomeTable.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "삭제할 항목을 먼저 선택하세요", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String income = incomeTable.getValueAt(index, 0).toString();
        int result = JOptionPane.showConfirmDialog(this, income + "를 수입 항목 목록에서 삭제할까요?", "수입 항목 삭제",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {

            int delete = dao.deleteIncome(income);
            if (delete > 0) {
                JOptionPane.showMessageDialog(this, income + "수입 항목을 삭제 했습니다.");
                initializeIncomeTable();
            }
        }

    }

    private void deleteAccount() {
        int index = accountsTable.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "삭제할 계좌를 먼저 선택하세요", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String accName = accountsTable.getValueAt(index, 0).toString();
        int result = JOptionPane.showConfirmDialog(this, accName + "을 계좌 목록에서 삭제할까요?", "계좌 삭제",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {

            int delete = dao.deleteAccounts(accName);
            if (delete > 0) {
                JOptionPane.showMessageDialog(this, accName + "계좌를 삭제 했습니다.");
                initializeAccountTable();
            }
        }

    }

    private void createAccount() {
        String bank = bankField.getText();
        String accName = accNameField.getText();
        String accNo = accNoField.getText();
        int balance = 0;
        try {
            balance = Integer.parseInt(balField.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "올바른 숫자를 입력하세요", "정수만 입력", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String cardName = cardNameField.getText();
        if (bank.equals("") || accName.equals("") || accNo.equals("")) {
            JOptionPane.showMessageDialog(this, "은행명, 계좌명, 계좌번호는 필수 입력", "계좌 정보 부족", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Accounts account = new Accounts(bank, accName, accNo, balance, cardName);

        int result = JOptionPane.showConfirmDialog(this, accName + "을 계좌 목록에 추가할까요?", "계좌 등록",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            int insert = dao.insertAccounts(account);
            if (insert > 0) {
                JOptionPane.showMessageDialog(this, accName + "계좌를 추가 했습니다.");
                bankField.setText("");
                accNameField.setText("");
                accNoField.setText("");
                balField.setText("");
                cardNameField.setText("");
                initializeAccountTable();
                listener.onSetting();
            } else {
                JOptionPane.showMessageDialog(this, "같은 정보의 계좌가 이미 있습니다.", "계좌 정보 중복", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

}
