package oz.account.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import oz.account.controller.DaoImpl;
import oz.account.model.Accounts;
import oz.account.model.OzUser;
import oz.account.model.Spend;
import oz.account.view.ExpenseFrame.ExpenseListener;
import oz.account.view.IncomeFrame.IncomeListener;
import oz.account.view.Setting.SettingListener;
import oz.account.view.TransferFrame.TransferListener;

public class App implements ExpenseListener, IncomeListener, SettingListener, TransferListener {

    private static final String[] TOTAL_TABLE_COL = { "계좌명", "지출 항목", "비용" };
    private JFrame frmOzAccountProgram;
    private JTextField textBudget;
    private JTextField textExpense;
    private JTextField textBalance;
    private JTextField textWallet;
    private JTextField textTotalBalance;
    private JTable totalTable;
    private DefaultTableModel model;
    private DaoImpl dao;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    App window = new App();
                    window.frmOzAccountProgram.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });
    }

    /**
     * Create the application.
     */
    public App() {
        dao = DaoImpl.getInstance();
        initialize();
        try {
            initializeTable();
        } catch (Exception e) {
            initialize();
        }
        initializePanel();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initializeTable() {
        model = new DefaultTableModel(null, TOTAL_TABLE_COL);
        totalTable.setModel(model);
        List<Spend> list = dao.readAllSpend();
            for (Spend l : list) {
                Object[] row = { l.getAccountName(), l.getExpense(), l.getCost() };
                model.addRow(row);
            }

    }
    
    private void initializePanel() {
        int budget = 0;
        for (OzUser l : dao.readAllOzUser()) {
            budget = l.getBudget();
        }
        
        int spend = 0;
        for (Spend l : dao.readAllSpend()) {
            spend += l.getCost();
        }
        
        int wallet = 0;
        int totalBalance = 0;
        for (Accounts l : dao.readAllAccounts()) {
            if (l.getAccountName().equals("내 지갑")) {
                wallet = l.getBalance();
            }
            totalBalance += l.getBalance();
        }
        
        
        
      textBudget.setText(String.valueOf(budget));
      textBalance.setText(String.valueOf(budget - spend));
      textExpense.setText(String.valueOf(spend));
      textWallet.setText(String.valueOf(wallet));
      textTotalBalance.setText(String.valueOf(totalBalance));
    }

    private void initialize() {
        frmOzAccountProgram = new JFrame();
        frmOzAccountProgram.setTitle("OZ Account Program");
        frmOzAccountProgram.setBounds(600, 200, 550, 600);
        frmOzAccountProgram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmOzAccountProgram.getContentPane().setLayout(null);

        JButton btnIncome = new JButton("수 입");
        btnIncome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                IncomeFrame.newIncomeFrame(frmOzAccountProgram, App.this);
            }
        });
        btnIncome.setFont(new Font("맑은 고딕", Font.BOLD, 25));
        btnIncome.setBounds(12, 10, 170, 100);
        frmOzAccountProgram.getContentPane().add(btnIncome);

        JButton btnExpense = new JButton("지 출");
        btnExpense.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ExpenseFrame.newExpenseFrame(frmOzAccountProgram, App.this);
            }
        });
        btnExpense.setFont(new Font("맑은 고딕", Font.BOLD, 25));
        btnExpense.setBounds(12, 120, 170, 100);
        frmOzAccountProgram.getContentPane().add(btnExpense);

        JButton btnTransfer = new JButton("자금이동");
        btnTransfer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TransferFrame.newTransferFrame(frmOzAccountProgram, App.this);
            }
        });
        btnTransfer.setFont(new Font("맑은 고딕", Font.BOLD, 25));
        btnTransfer.setBounds(12, 230, 170, 100);
        frmOzAccountProgram.getContentPane().add(btnTransfer);

        JButton btnSearch = new JButton("내역검색");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SearchFrame.newSearchFrame(frmOzAccountProgram);
            }
        });
        btnSearch.setFont(new Font("맑은 고딕", Font.BOLD, 25));
        btnSearch.setBounds(12, 340, 170, 100);
        frmOzAccountProgram.getContentPane().add(btnSearch);

        JButton btnSetting = new JButton("설정");
        btnSetting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Setting.openSetting(frmOzAccountProgram, App.this);
            }
        });
        btnSetting.setFont(new Font("맑은 고딕", Font.BOLD, 25));
        btnSetting.setBounds(12, 451, 170, 100);
        frmOzAccountProgram.getContentPane().add(btnSetting);

        JLabel lblBudget = new JLabel("당월 예산");
        lblBudget.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        lblBudget.setBounds(231, 10, 61, 21);
        frmOzAccountProgram.getContentPane().add(lblBudget);

        JLabel lblExpense = new JLabel("당월 지출");
        lblExpense.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        lblExpense.setBounds(231, 41, 61, 21);
        frmOzAccountProgram.getContentPane().add(lblExpense);

        JLabel lblBalance = new JLabel("남은 예산");
        lblBalance.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        lblBalance.setBounds(231, 72, 61, 21);
        frmOzAccountProgram.getContentPane().add(lblBalance);

        JLabel lblWallet = new JLabel("내 지갑");
        lblWallet.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        lblWallet.setBounds(245, 499, 47, 21);
        frmOzAccountProgram.getContentPane().add(lblWallet);

        JLabel lblTotalBalance = new JLabel("보유 금액");
        lblTotalBalance.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        lblTotalBalance.setBounds(231, 530, 61, 21);
        frmOzAccountProgram.getContentPane().add(lblTotalBalance);

        textBudget = new JTextField();

        textBudget.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        textBudget.setEnabled(false);
        textBudget.setBounds(304, 10, 150, 21);
        frmOzAccountProgram.getContentPane().add(textBudget);
        textBudget.setColumns(10);

        textExpense = new JTextField();

        textExpense.setFont(new Font("맑은 고딕", Font.ITALIC, 15));
        textExpense.setEnabled(false);
        textExpense.setColumns(10);
        textExpense.setBounds(304, 40, 150, 21);
        frmOzAccountProgram.getContentPane().add(textExpense);

        textBalance = new JTextField();

        textBalance.setFont(new Font("맑은 고딕", Font.ITALIC, 15));
        textBalance.setEnabled(false);
        textBalance.setColumns(10);
        textBalance.setBounds(304, 71, 150, 21);
        frmOzAccountProgram.getContentPane().add(textBalance);

        textWallet = new JTextField();
        textWallet.setFont(new Font("맑은 고딕", Font.ITALIC, 15));
        textWallet.setEnabled(false);
        textWallet.setColumns(10);
        textWallet.setBounds(304, 499, 150, 21);
        frmOzAccountProgram.getContentPane().add(textWallet);

        textTotalBalance = new JTextField();
        textTotalBalance.setFont(new Font("맑은 고딕", Font.ITALIC, 15));
        textTotalBalance.setEnabled(false);
        textTotalBalance.setColumns(10);
        textTotalBalance.setBounds(304, 530, 150, 21);
        frmOzAccountProgram.getContentPane().add(textTotalBalance);

        JLabel lblWon = new JLabel("원");
        lblWon.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        lblWon.setBounds(457, 10, 29, 21);
        frmOzAccountProgram.getContentPane().add(lblWon);

        JLabel lblWon_1 = new JLabel("원");
        lblWon_1.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        lblWon_1.setBounds(457, 41, 29, 21);
        frmOzAccountProgram.getContentPane().add(lblWon_1);

        JLabel lblWon_2 = new JLabel("원");
        lblWon_2.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        lblWon_2.setBounds(457, 72, 29, 21);
        frmOzAccountProgram.getContentPane().add(lblWon_2);

        JLabel lblWon_3 = new JLabel("원");
        lblWon_3.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        lblWon_3.setBounds(457, 496, 29, 21);
        frmOzAccountProgram.getContentPane().add(lblWon_3);

        JLabel lblWon_4 = new JLabel("원");
        lblWon_4.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        lblWon_4.setBounds(457, 530, 29, 21);
        frmOzAccountProgram.getContentPane().add(lblWon_4);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(194, 137, 328, 341);
        frmOzAccountProgram.getContentPane().add(scrollPane);

        totalTable = new JTable();
        totalTable.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        model = new DefaultTableModel(null, TOTAL_TABLE_COL);
        totalTable.setModel(model);
        scrollPane.setViewportView(totalTable);

        JLabel totalTableLabel = new JLabel("최근 지출");
        totalTableLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        totalTableLabel.setBounds(314, 106, 75, 21);
        frmOzAccountProgram.getContentPane().add(totalTableLabel);
    }

    @Override
    public void onExpense() {
        initializeTable();
        initializePanel();
    }

    @Override
    public void onIncome() {
        initializeTable();
        initializePanel();
    }

    @Override
    public void onSetting() {
        initializeTable();
        initializePanel();
    }

    @Override
    public void onTransfer() {
        initializeTable();
        initializePanel();
    }
}
