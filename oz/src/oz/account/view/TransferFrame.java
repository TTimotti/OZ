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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import oz.account.controller.DaoImpl;
import oz.account.model.Accounts;
import oz.account.model.Transfer;

public class TransferFrame extends JFrame {
    public interface TransferListener {
        void onTransfer();
    }

    private JPanel contentPane;
    private Component parentFrame;
    private SpringLayout springLayout;
    private JTextField detailField;
    private JTextField textTransfer;
    private UtilDateModel model;
    private DaoImpl dao;
    private JComboBox<String> comboBoxAccountList;
    private JComboBox<String> comboBoxAccountList_1;
    private TransferListener listener;

    /**
     * Launch the application.
     */
    public static void newTransferFrame(Component parentFrame, TransferListener listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TransferFrame frame = new TransferFrame(parentFrame, listener);
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
    public TransferFrame(Component parentFrame, TransferListener listener) {
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
            if (!l.getAccountName().equals("내 지갑")) {
                accNameList.add(l.getAccountName());
            }
        }
        String[] accNameArr = accNameList.toArray(new String[accNameList.size()]);
        DefaultComboBoxModel<String> accountComboBoxModel = new DefaultComboBoxModel<>(accNameArr);
        comboBoxAccountList.setModel(accountComboBoxModel);
        

        for (Accounts l : list) {
            if (!l.getAccountName().equals("내 지갑")) {
                accNameList.add(l.getAccountName());
            }
        }
        String[] accNameArr1 = accNameList.toArray(new String[accNameList.size()]);
        DefaultComboBoxModel<String> accountComboBoxModel1 = new DefaultComboBoxModel<>(accNameArr1);
        comboBoxAccountList_1.setModel(accountComboBoxModel1);
        
    }

    public void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("계좌 이동");
        int x = parentFrame.getX();
        int y = parentFrame.getY();
        setBounds(x - 50, y + 100, 650, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblSelectDate = new JLabel("날짜 선택");
        lblSelectDate.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        lblSelectDate.setBounds(12, 12, 98, 21);
        contentPane.add(lblSelectDate);

        model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
        datePicker.getJFormattedTextField().setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        springLayout = (SpringLayout) datePicker.getLayout();
        datePicker.setBounds(85, 10, 209, 35);
        LocalDateTime today = LocalDateTime.now();
        model.setDate(today.getYear(), today.getMonthValue() - 1, today.getDayOfMonth());
        model.setSelected(true);
        getContentPane().add(datePicker);

        JLabel lblFromAccount = new JLabel("출금 계좌 선택");
        lblFromAccount.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        lblFromAccount.setBounds(12, 50, 98, 21);
        contentPane.add(lblFromAccount);

        comboBoxAccountList = new JComboBox<>();
        comboBoxAccountList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ifSendAccSelected();
            }
        });
        comboBoxAccountList.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        comboBoxAccountList.setBounds(122, 49, 202, 23);
        contentPane.add(comboBoxAccountList);
        
        

        JLabel lblToAccount = new JLabel("입금 계좌 선택");
        lblToAccount.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        lblToAccount.setBounds(12, 83, 98, 21);
        contentPane.add(lblToAccount);

        comboBoxAccountList_1 = new JComboBox<>();
        comboBoxAccountList_1.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        comboBoxAccountList_1.setBounds(122, 82, 202, 23);
        contentPane.add(comboBoxAccountList_1);

        JLabel lblItemDetail = new JLabel("세부 내용");
        lblItemDetail.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        lblItemDetail.setBounds(12, 115, 98, 21);
        contentPane.add(lblItemDetail);

        detailField = new JTextField();
        detailField.setColumns(10);
        detailField.setBounds(122, 116, 202, 23);
        contentPane.add(detailField);

        JLabel lblTransfer = new JLabel("송금 금액");
        lblTransfer.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        lblTransfer.setBounds(12, 230, 98, 21);
        contentPane.add(lblTransfer);

        textTransfer = new JTextField();
        textTransfer.setColumns(10);
        textTransfer.setBounds(122, 228, 202, 23);
        contentPane.add(textTransfer);

        JLabel lblWon = new JLabel("원");
        lblWon.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        lblWon.setBounds(336, 230, 29, 21);
        contentPane.add(lblWon);

        JButton btnSave = new JButton("등록하기");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                transferAccount();
            }
        });
        btnSave.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        btnSave.setBounds(406, 13, 216, 239);
        contentPane.add(btnSave);
    }

    protected void ifSendAccSelected() {
        List<Accounts> list = dao.readAllAccounts();
        List<String> accNameList = new ArrayList<>();
        for (Accounts l : list) {
            if (!l.getAccountName().equals("내 지갑") && !l.getAccountName().equals(comboBoxAccountList.getSelectedItem().toString())) {
                accNameList.add(l.getAccountName());
            }
        }
        String[] accNameArr1 = accNameList.toArray(new String[accNameList.size()]);
        DefaultComboBoxModel<String> accountComboBoxModel1 = new DefaultComboBoxModel<>(accNameArr1);
        comboBoxAccountList_1.setModel(accountComboBoxModel1);
        
    }

    protected void transferAccount() {
        LocalDateTime payDate = new Timestamp(model.getValue().getTime()).toLocalDateTime();
        String sendingAccount = comboBoxAccountList.getSelectedItem().toString();
        String receivingAccount = comboBoxAccountList_1.getSelectedItem().toString();
        String detail = detailField.getText();
        int cost = Integer.parseInt(textTransfer.getText());
        
        Transfer transfer = new Transfer(payDate, sendingAccount, receivingAccount, cost, detail);
        
        dao.insertTransfer(transfer);
        dao.updateImport(receivingAccount, cost);
        dao.updateSpend(sendingAccount, cost);
        
//        dao.payTransferSend(sendingOz);
//        dao.payTransferReceive(receivingOz);
        JOptionPane.showMessageDialog(this, "자산 이동 완료");
        listener.onTransfer();
        dispose();
        
        
        
    }
}
