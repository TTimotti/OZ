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
import oz.account.model.Import;
import oz.account.model.Income;

public class IncomeFrame extends JFrame {

    public interface IncomeListener {
        void onIncome();
    }

    private JPanel contentPane;
    private SpringLayout springLayout;
    private JTextField costField;
    private Component parentFrame;
    private JComboBox<String> comboBoxAccountList;
    private JComboBox<String> comboBoxItemList;
    private IncomeListener listener;
    private DaoImpl dao;
    private UtilDateModel model;
    private JCheckBox walletCheckBox;

    /**
     * Launch the application.
     */
    public static void newIncomeFrame(Component parentFrame, IncomeListener listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    IncomeFrame frame = new IncomeFrame(parentFrame, listener);
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

    public IncomeFrame(Component parentFrame, IncomeListener listener) {
        this.listener = listener;
        this.parentFrame = parentFrame;
        dao = dao.getInstance();
        initialize();
        initializeAccListComboBox();

    }

    private void initializeAccListComboBox() {
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

        JLabel lblSelectAccount = new JLabel("?????? ??????");
        lblSelectAccount.setFont(new Font("?????? ??????", Font.PLAIN, 14));
        lblSelectAccount.setBounds(12, 46, 61, 21);
        contentPane.add(lblSelectAccount);

        comboBoxAccountList = new JComboBox<>();
        comboBoxAccountList.setFont(new Font("?????? ??????", Font.PLAIN, 14));
        comboBoxAccountList.setBounds(85, 45, 202, 23);
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
        lblItem.setBounds(12, 77, 61, 21);
        contentPane.add(lblItem);

        comboBoxItemList = new JComboBox<String>();
        List<Income> incomeList = dao.readAllIncome();
        List<String> incomeNameList = new ArrayList<>();
        for (Income e : incomeList) {
            incomeNameList.add(e.getName());
        }
        String[] incomeArr = incomeNameList.toArray(new String[incomeNameList.size()]);
        DefaultComboBoxModel<String> incomeComboBoxModel = new DefaultComboBoxModel<>(incomeArr);
        comboBoxItemList.setModel(incomeComboBoxModel);
        comboBoxItemList.setSelectedIndex(0);
        comboBoxItemList.setFont(new Font("?????? ??????", Font.PLAIN, 14));
        comboBoxItemList.setBounds(85, 76, 202, 23);
        contentPane.add(comboBoxItemList);

        JLabel lblIncome = new JLabel("?????? ??????");
        lblIncome.setFont(new Font("?????? ??????", Font.PLAIN, 14));
        lblIncome.setBounds(12, 230, 61, 21);
        contentPane.add(lblIncome);

        costField = new JTextField();
        costField.setColumns(10);
        costField.setBounds(85, 231, 202, 23);
        contentPane.add(costField);

        JLabel lblWon = new JLabel("???");
        lblWon.setFont(new Font("?????? ??????", Font.PLAIN, 14));
        lblWon.setBounds(288, 230, 29, 21);
        contentPane.add(lblWon);

        JButton btnSave = new JButton("????????????");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertPay();
            }
        });
        btnSave.setFont(new Font("?????? ??????", Font.BOLD, 20));
        btnSave.setBounds(401, 12, 221, 239);
        contentPane.add(btnSave);

        walletCheckBox = new JCheckBox("??? ?????? ??????");
        walletCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isWalletCheckBoxSelect();
            }
        });
        walletCheckBox.setFont(new Font("?????? ??????", Font.PLAIN, 14));
        walletCheckBox.setBounds(295, 45, 105, 23);
        contentPane.add(walletCheckBox);

    }

    protected void isWalletCheckBoxSelect() {
        if (walletCheckBox.isSelected()) {
            String[] accNameArr = { "??? ??????" };
            DefaultComboBoxModel<String> accountComboBoxModel = new DefaultComboBoxModel<>(accNameArr);
            comboBoxAccountList.setModel(accountComboBoxModel);
            comboBoxAccountList.setEnabled(false);
            return;
        }

        comboBoxAccountList.setEnabled(true);
        initializeAccListComboBox();
    }

    protected void insertPay() {
        LocalDateTime payDate = new Timestamp(model.getValue().getTime()).toLocalDateTime();
        String accountName = comboBoxAccountList.getSelectedItem().toString();
        String incomeName = comboBoxItemList.getSelectedItem().toString();
        int cost = Integer.parseInt(costField.getText());

        Import impor = new Import(payDate, accountName, incomeName, cost);

        dao.insertImport(impor);
        dao.updateImport(accountName, cost);
        dispose();
        listener.onIncome();

    }
}
