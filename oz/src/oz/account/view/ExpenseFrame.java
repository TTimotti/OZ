package oz.account.view;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import oz.account.controller.DaoImpl;
import oz.account.model.Accounts;
import oz.account.model.Expense;
import oz.account.model.Spend;
import javax.swing.ButtonGroup;

public class ExpenseFrame extends JFrame {

    public interface ExpenseListener {
        void onExpense();
    }

    private JPanel contentPane;
    private SpringLayout springLayout;
    private JTextField costField;
    private Component parentFrame;
    private UtilDateModel model;
    private JComboBox<String> comboBoxAccountList;
    private JComboBox<String> comboBoxItemList;
    private JComboBox<String> comboBoxCardList;
    private DaoImpl dao;
    private JCheckBox walletCheckBox;
    private JCheckBox transferCheckBox;
    private ExpenseListener listener;

    /**
     * Launch the application.
     */
    public static void newExpenseFrame(Component parentFrame, ExpenseListener listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ExpenseFrame frame = new ExpenseFrame(parentFrame, listener);
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

    public ExpenseFrame(Component parentFrame, ExpenseListener listener) {
        this.listener = listener;
        this.parentFrame = parentFrame;
        dao = dao.getInstance();
        initialize();
        initializeCardComboBox();
        initializeAccountsComboBox();

    }

    public void initialize() {

        setTitle("?????? ??????");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = parentFrame.getX();
        int y = parentFrame.getY();
        setBounds(x - 50, y + 100, 650, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblCardName = new JLabel("?????? ??????");
        lblCardName.setFont(new Font("?????? ??????", Font.PLAIN, 14));
        lblCardName.setBounds(12, 46, 61, 21);
        contentPane.add(lblCardName);

        comboBoxCardList = new JComboBox<>();
        comboBoxCardList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ifSelectFirstCard();
            }
        });
        comboBoxCardList.setFont(new Font("?????? ??????", Font.PLAIN, 14));
        comboBoxCardList.setBounds(85, 45, 202, 23);
        contentPane.add(comboBoxCardList);

        JLabel lblSelectAccount = new JLabel("?????? ??????");
        lblSelectAccount.setFont(new Font("?????? ??????", Font.PLAIN, 14));
        lblSelectAccount.setBounds(12, 77, 61, 21);
        contentPane.add(lblSelectAccount);

        comboBoxAccountList = new JComboBox<>();
        comboBoxAccountList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        comboBoxAccountList.setFont(new Font("?????? ??????", Font.PLAIN, 14));
        comboBoxAccountList.setBounds(85, 76, 202, 23);
        contentPane.add(comboBoxAccountList);

        JLabel lblSelectDate = new JLabel("?????? ??????");
        lblSelectDate.setFont(new Font("?????? ??????", Font.PLAIN, 14));
        lblSelectDate.setBounds(12, 12, 61, 21);
        contentPane.add(lblSelectDate);

        model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
        datePicker.getJFormattedTextField().setFont(new Font("?????? ??????", Font.PLAIN, 12));
        springLayout = (SpringLayout) datePicker.getLayout();
        datePicker.setBounds(85, 10, 209, 35);
        LocalDateTime today = LocalDateTime.now();
        model.setDate(today.getYear(), today.getMonthValue() - 1, today.getDayOfMonth());
        model.setSelected(true);
        getContentPane().add(datePicker);

        JLabel lblItem = new JLabel("?????? ??????");
        lblItem.setFont(new Font("?????? ??????", Font.PLAIN, 14));
        lblItem.setBounds(12, 112, 61, 21);
        contentPane.add(lblItem);

        comboBoxItemList = new JComboBox<>();
        List<Expense> expenseList = dao.readAllExpense();
        List<String> expenseNameList = new ArrayList<>();
        for (Expense e : expenseList) {
            expenseNameList.add(e.getName());
        }
        String[] expenseArr = expenseNameList.toArray(new String[expenseNameList.size()]);
        DefaultComboBoxModel<String> expenseComboBoxModel = new DefaultComboBoxModel<>(expenseArr);
        comboBoxItemList.setModel(expenseComboBoxModel);
        comboBoxItemList.setSelectedIndex(0);
        comboBoxItemList.setFont(new Font("?????? ??????", Font.PLAIN, 14));
        comboBoxItemList.setBounds(85, 111, 202, 23);
        contentPane.add(comboBoxItemList);

        // TODO ?????? ????????? ???????????????
//        JLabel lblItemDetail = new JLabel("?????? ??????");
//        lblItemDetail.setFont(new Font("?????? ??????", Font.PLAIN, 14));
//        lblItemDetail.setBounds(12, 111, 98, 21);
//        contentPane.add(lblItemDetail);
//
//        textItemDetail = new JTextField();
//        textItemDetail.setBounds(122, 109, 202, 23);
//        contentPane.add(textItemDetail);
//        textItemDetail.setColumns(10);

        JLabel lblExpense = new JLabel("?????? ??????");
        lblExpense.setFont(new Font("?????? ??????", Font.PLAIN, 14));
        lblExpense.setBounds(12, 230, 61, 24);
        contentPane.add(lblExpense);

        costField = new JTextField();
        costField.setColumns(10);
        costField.setBounds(85, 231, 202, 23);
        contentPane.add(costField);

        JLabel lblWon = new JLabel("???");
        lblWon.setFont(new Font("?????? ??????", Font.PLAIN, 14));
        lblWon.setBounds(291, 233, 29, 21);
        contentPane.add(lblWon);

        JButton btnSave = new JButton("????????????");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertPay();

            }
        });
        btnSave.setFont(new Font("?????? ??????", Font.BOLD, 20));
        btnSave.setBounds(406, 12, 216, 239);
        contentPane.add(btnSave);

        walletCheckBox = new JCheckBox("??? ?????? ??????");
        walletCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkWallet();
            }
        });
        walletCheckBox.setFont(new Font("?????? ??????", Font.PLAIN, 14));
        walletCheckBox.setBounds(295, 76, 110, 23);
        contentPane.add(walletCheckBox);

        transferCheckBox = new JCheckBox("?????? ??????");
        transferCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkTransfer();

            }
        });
        transferCheckBox.setFont(new Font("?????? ??????", Font.PLAIN, 14));
        transferCheckBox.setBounds(295, 45, 110, 23);
        contentPane.add(transferCheckBox);

    }

    protected void ifSelectFirstCard() {

        List<Accounts> list = dao.readAllAccounts();
        List<String> accNameList = new ArrayList<>();
        for (Accounts l : list) {
            if (l.getCardName().equals(comboBoxCardList.getSelectedItem().toString())) {
                accNameList.add(l.getAccountName());
            }
        }
        String[] accNameArr = accNameList.toArray(new String[accNameList.size()]);
        DefaultComboBoxModel<String> accountComboBoxModel = new DefaultComboBoxModel<>(accNameArr);
        comboBoxAccountList.setModel(accountComboBoxModel);

    }

    private void checkTransfer() {
        if (walletCheckBox.isSelected()) {
            return;
        }
        if (transferCheckBox.isSelected()) {

            comboBoxCardList.setModel(new DefaultComboBoxModel<>());
            comboBoxCardList.setEnabled(false);
        } else {
            initializeAccountsComboBox();
            initializeCardComboBox();
            comboBoxCardList.setEnabled(true);
        }
    }

    protected void checkWallet() {
        if (walletCheckBox.isSelected()) {
            List<Accounts> list = dao.readAllAccounts();
            List<String> accNameList = new ArrayList<>();
            for (Accounts l : list) {
                if (l.getAccountName().equals("??? ??????")) {
                    accNameList.add(l.getAccountName());
                }
            }
            String[] accNameArr = accNameList.toArray(new String[accNameList.size()]);
            DefaultComboBoxModel<String> accountComboBoxModel = new DefaultComboBoxModel<>(accNameArr);
            comboBoxAccountList.setModel(accountComboBoxModel);
            comboBoxCardList.setEnabled(false);
            comboBoxAccountList.setEnabled(false);
            List<String> cardNameList = new ArrayList<>();
            for (Accounts l : list) {
                if (l.getCardName() != null && l.getCardName().equals("??? ??????")) {
                    cardNameList.add(l.getCardName());
                }
            }

            String[] cardNameArr = cardNameList.toArray(new String[cardNameList.size()]);

            DefaultComboBoxModel<String> cardComboBoxModel = new DefaultComboBoxModel<>(cardNameArr);
            comboBoxCardList.setModel(cardComboBoxModel);
        } else {
            initializeAccountsComboBox();
            initializeCardComboBox();
            comboBoxCardList.setEnabled(true);
            comboBoxAccountList.setEnabled(true);
        }

    }

    private void insertPay() {
        LocalDateTime payDate = new Timestamp(model.getValue().getTime()).toLocalDateTime();
        String accName = comboBoxAccountList.getSelectedItem().toString();
        String expenseName = comboBoxItemList.getSelectedItem().toString();
        int cost = Integer.parseInt(costField.getText());
        String cardName = comboBoxCardList.getSelectedItem().toString();

        Spend spend = new Spend(payDate, accName, cardName, expenseName, cost);
        dao.insertSpend(spend);
        dao.updateSpend(accName, cost);

        dispose();
        listener.onExpense();

    }

//    private void initializeCheckBox() {
//        String[] accNameArr = { "??? ??????" };
//        DefaultComboBoxModel<String> accountComboBoxModel = new DefaultComboBoxModel<>(accNameArr);
//        comboBoxAccountList.setModel(accountComboBoxModel);
//        comboBoxCardList.setModel(accountComboBoxModel);
//        comboBoxCardList.setEnabled(false);
//        comboBoxAccountList.setEnabled(false);
//    }

    private void initializeCardComboBox() {
        List<Accounts> list = dao.readAllAccounts();
        List<String> cardNameList = new ArrayList<>();
        for (Accounts l : list) {
            if (l.getCardName() != null && !l.getCardName().equals("??? ??????")) {
                cardNameList.add(l.getCardName());
            }
        }

        String[] cardNameArr = cardNameList.toArray(new String[cardNameList.size()]);

        DefaultComboBoxModel<String> cardComboBoxModel = new DefaultComboBoxModel<>(cardNameArr);
        comboBoxCardList.setModel(cardComboBoxModel);

    }
//        List<OzAccount> accountList = dao.readCardAndAcc(comboBoxCardList.getSelectedItem().toString());
//        List<String> accNameList = new ArrayList<>();
//        for (OzAccount a : accountList) {
//            System.out.println(a);
//            accNameList.add(a.getAccName());
//        }
//        String[] accNameArr = accNameList.toArray(new String[accNameList.size()]);
//        DefaultComboBoxModel<String> accountComboBoxModel = new DefaultComboBoxModel<>(accNameArr);
//        comboBoxAccountList.setModel(accountComboBoxModel);
//        comboBoxAccountList.setFont(new Font("?????? ??????", Font.PLAIN, 14));
//        comboBoxAccountList.setBounds(85, 76, 202, 23);
//        contentPane.add(comboBoxAccountList);

    private void initializeAccountsComboBox() {

        List<Accounts> list = dao.readAllAccounts();
        List<String> accNameList = new ArrayList<>();
        for (Accounts l : list) {
            if (!l.getAccountName().equals("??? ??????")) {
                accNameList.add(l.getAccountName());
            }
        }
        String[] accNameArr = accNameList.toArray(new String[accNameList.size()]);
        DefaultComboBoxModel<String> accountComboBoxModel = new DefaultComboBoxModel<>(accNameArr);
        comboBoxAccountList.setModel(accountComboBoxModel);

    }
}

//        List<OzAccount> cardList = dao.readAllCards();
//        List<String> cardNameList = new ArrayList<>();
//        for (OzAccount a : cardList) {
//            cardNameList.add(a.getCardName());
//        }
//        String[] cardNameArr = cardNameList.toArray(new String[cardNameList.size()]);
//
//        DefaultComboBoxModel<String> cardComboBoxModel = new DefaultComboBoxModel<>(cardNameArr);
//        comboBoxCardList.setModel(cardComboBoxModel);
//
//        List<OzAccount> accountList = dao.readAllAccounts();
//        List<String> accNameList = new ArrayList<>();
//        for (OzAccount a : accountList) {
//            accNameList.add(a.getAccName());
//        }
//        String[] accNameArr = accNameList.toArray(new String[accNameList.size()]);
//
//        DefaultComboBoxModel<String> accountComboBoxModel = new DefaultComboBoxModel<>(accNameArr);
//        comboBoxAccountList.setModel(accountComboBoxModel);
//
//    }
