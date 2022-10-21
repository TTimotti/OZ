package oz.account.view;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import oz.account.controller.DaoImpl;
import oz.account.model.Accounts;
import oz.account.model.Import;
import oz.account.model.Spend;
import oz.account.model.Transfer;

public class SearchFrame extends JFrame {

    private JPanel contentPane;
    private Component parentFrame;
    private SpringLayout sl_datePicker;
    private JTable tableResult;
    private DefaultTableModel model;
    private JCheckBox chckbxIncome;
    private JCheckBox chckbxExpense;
    private JCheckBox chckbxTransfer;
    private JComboBox<String> comboBoxAccountList;
    private JDatePickerImpl datePicker;
    private String[] colMain = { "등록 날짜", "계좌 이름", "수입/지출/자금이동", "금 액", "항 목" };
    private DaoImpl dao;
    private DefaultTableCellRenderer dtcr;
    private DefaultTableCellRenderer dtcr2;
    private UtilDateModel utilDateModel;
    private JCheckBox chckbxAllAccounts;
    private JScrollPane scrollPane;

    /**
     * Launch the application.
     */
    public static void newSearchFrame(Component parentFrame) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SearchFrame frame = new SearchFrame(parentFrame);
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
    public SearchFrame(Component parentFrame) {
        dao = dao.getInstance();
        this.parentFrame = parentFrame;
        initialize();
        initializeAccList();
        initializeTable();
    }

    private void initializeAccList() {
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

    }

    private void initializeTable() {
        model = new DefaultTableModel(null, colMain);
        tableResult.setModel(model);
        List<Spend> spendList = dao.readAllSpend();
        List<Import> importList = dao.readAllImport();
        List<Transfer> transferList = dao.readAllTransfer();
        for (Spend l : spendList) {

            Object[] row = {
                    LocalDateTime.parse(l.getPayDate().toString()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    l.getAccountName(), "지출", l.getCost(), l.getExpense() };
            model.addRow(row);
        }
        for (Import l : importList) {
            Object[] row = {
                    LocalDateTime.parse(l.getPayDate().toString()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    l.getAccountName(), "수입", l.getCost(), l.getIncome() };
            model.addRow(row);
        }
        for (Transfer l : transferList) {
            Object[] row = {
                    LocalDateTime.parse(l.getPayDate().toString()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    l.getSendAccountName() + " → " + l.getReceiveAccountName(), "자금이동", "- " + l.getCash(),
                    l.getMemo() };
            Object[] row2 = {
                    LocalDateTime.parse(l.getPayDate().toString()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    l.getReceiveAccountName(), "입금", l.getCash(), l.getMemo() };
            model.addRow(row);
            model.addRow(row2);
        }
//        tableResult.setRowSorter(new TableRowSorter<>(model));
//      LocalDateTime.parse(payDate.toString()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//      LocalDateTime.parse(payDate.toString()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        tableResult.getColumn("등록 날짜").setPreferredWidth(60);
        tableResult.getColumn("계좌 이름").setPreferredWidth(35);
        tableResult.getColumn("수입/지출/자금이동").setPreferredWidth(40);
        tableResult.getColumn("금 액").setPreferredWidth(35);
        tableResult.getColumn("항 목").setPreferredWidth(35);
        dtcr = new DefaultTableCellRenderer();
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);
        TableColumnModel tcm = tableResult.getColumnModel();
        tcm.getColumn(0).setCellRenderer(dtcr);
        tcm.getColumn(1).setCellRenderer(dtcr);
        tcm.getColumn(2).setCellRenderer(dtcr);
        tcm.getColumn(4).setCellRenderer(dtcr);
        dtcr2 = new DefaultTableCellRenderer();
        dtcr2.setHorizontalAlignment(SwingConstants.RIGHT);
        tcm = tableResult.getColumnModel();
        tcm.getColumn(3).setCellRenderer(dtcr2);
    }

    public void initialize() {
        setTitle("내역 검색");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = parentFrame.getX();
        int y = parentFrame.getY();
        setBounds(x - 50, y, 750, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        chckbxIncome = new JCheckBox("수입");
        chckbxIncome.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        chckbxIncome.setBounds(8, 9, 66, 23);
        contentPane.add(chckbxIncome);

        chckbxExpense = new JCheckBox("지출");
        chckbxExpense.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        chckbxExpense.setBounds(78, 9, 66, 23);
        contentPane.add(chckbxExpense);

        chckbxTransfer = new JCheckBox("자금이동");
        chckbxTransfer.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        chckbxTransfer.setBounds(148, 9, 85, 23);
        contentPane.add(chckbxTransfer);

        JLabel lblSelectDate = new JLabel("날짜 선택");
        lblSelectDate.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        lblSelectDate.setBounds(8, 38, 66, 21);
        contentPane.add(lblSelectDate);

        utilDateModel = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(utilDateModel);
        datePicker = new JDatePickerImpl(datePanel);
        datePicker.getJFormattedTextField().setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        sl_datePicker = (SpringLayout) datePicker.getLayout();
        datePicker.setBounds(113, 38, 209, 27);
        LocalDateTime today = LocalDateTime.now();
        utilDateModel.setDate(today.getYear(), today.getMonthValue() - 1, today.getDayOfMonth());
        utilDateModel.setSelected(true);
        getContentPane().add(datePicker);

        JLabel lblSelectAccount = new JLabel("계좌별 검색");
        lblSelectAccount.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        lblSelectAccount.setBounds(8, 74, 98, 21);
        contentPane.add(lblSelectAccount);

        comboBoxAccountList = new JComboBox<>();
        comboBoxAccountList.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        comboBoxAccountList.setBounds(113, 73, 202, 23);
        contentPane.add(comboBoxAccountList);

        JButton btnSearch = new JButton("내역검색");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
        btnSearch.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        btnSearch.setBounds(603, 9, 119, 117);
        contentPane.add(btnSearch);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(8, 136, 714, 415);
        contentPane.add(scrollPane);

        tableResult = new JTable();
        tableResult.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        tableResult.setAutoCreateRowSorter(true);
        scrollPane.setViewportView(tableResult);

        model = new DefaultTableModel(null, colMain);
        tableResult.setModel(model);
        scrollPane.setViewportView(tableResult);

        model = new DefaultTableModel(null, colMain);
        tableResult.setModel(model);

        chckbxAllAccounts = new JCheckBox("전체 계좌");
        chckbxAllAccounts.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        chckbxAllAccounts.setBounds(323, 71, 91, 23);
        contentPane.add(chckbxAllAccounts);

//        String category = null;
//        String detail = null;

//        List<OzPay> list = dao.readHistory();
//        for (OzPay o : list) {
//            if (o.getTransfer() != null && o.getExpenseName() == null) {
//                category = "송금 완료";
//                detail = o.getTransfer();
//            } else if (o.getTransfer() != null && o.getIncomeName() == null) {
//                category = "내 계좌로 송금";
//                detail = o.getTransfer();
//            } else if (o.getExpenseName() != null) {
//
//                category = "지출";
//                detail = o.getExpenseName();
//            } else if (o.getIncomeName() != null) {
//                category = "수입";
//                detail = o.getIncomeName();
//            }
//
//            Object[] row = {
//                    LocalDateTime.parse(o.getPayDate().toString())
//                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
//                    o.getAccName(), category, o.getCost(), detail };
//            model.addRow(row);
//            tableResult.getColumn("등록 날짜").setPreferredWidth(60);
//            tableResult.getColumn("계좌 이름").setPreferredWidth(35);
//            tableResult.getColumn("수입/지출/자금이동").setPreferredWidth(40);
//            tableResult.getColumn("금 액").setPreferredWidth(35);
//            tableResult.getColumn("항 목").setPreferredWidth(35);
//            dtcr = new DefaultTableCellRenderer();
//            dtcr.setHorizontalAlignment(SwingConstants.CENTER);
//            TableColumnModel tcm = tableResult.getColumnModel();
//            tcm.getColumn(0).setCellRenderer(dtcr);
//            tcm.getColumn(1).setCellRenderer(dtcr);
//            tcm.getColumn(2).setCellRenderer(dtcr);
//            tcm.getColumn(4).setCellRenderer(dtcr);
//            dtcr2 = new DefaultTableCellRenderer();
//            dtcr2.setHorizontalAlignment(SwingConstants.RIGHT);
//            tcm = tableResult.getColumnModel();
//            tcm.getColumn(3).setCellRenderer(dtcr2);
//        }

    }

    private void search() {
        boolean readAllAcc = chckbxAllAccounts.isSelected();
        if (readAllAcc) {
            search_2();
        } else {
            search_1();
        }

    }

    private void search_2() {
        boolean income = chckbxIncome.isSelected();
        boolean expense = chckbxExpense.isSelected();
        boolean transfer = chckbxTransfer.isSelected();

        List<Spend> spendList = dao.readAllSpend();
        List<Import> importList = dao.readAllImport();
        List<Transfer> transferList = dao.readAllTransfer();

        if (income) {
            for (Import l : importList) {
                if (l.getAccountName().equals(comboBoxAccountList.getSelectedItem().toString())) {
                    Object[] row = {
                            LocalDateTime.parse(l.getPayDate().toString())
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            l.getAccountName(), "수입", l.getCost(), l.getIncome() };
                    model.addRow(row);
                }
            }
            if (expense) {
                for (Spend l : spendList) {
                    if (l.getAccountName().equals(comboBoxAccountList.getSelectedItem().toString())) {
                        Object[] row = {
                                LocalDateTime.parse(l.getPayDate().toString())
                                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                l.getAccountName(), "지출", l.getCost(), l.getExpense() };
                        model.addRow(row);
                    }
                }
                if (transfer) {
                    for (Transfer l : transferList) {
                        if (l.getSendAccountName().equals(comboBoxAccountList.getSelectedItem().toString()) || (l
                                .getReceiveAccountName().equals(comboBoxAccountList.getSelectedItem().toString()))) {
                            Object[] row = {
                                    LocalDateTime.parse(l.getPayDate().toString())
                                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                    l.getSendAccountName() + " → " + l.getReceiveAccountName(), "자금이동",
                                    "- " + l.getCash(), l.getMemo() };
                            Object[] row2 = {
                                    LocalDateTime.parse(l.getPayDate().toString())
                                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                    l.getReceiveAccountName(), "입금", l.getCash(), l.getMemo() };
                            model.addRow(row);
                            model.addRow(row2);
                        }
                    }
                }

            }
            if (transfer) {
                for (Transfer l : transferList) {
                    if (l.getSendAccountName().equals(comboBoxAccountList.getSelectedItem().toString())
                            || (l.getReceiveAccountName().equals(comboBoxAccountList.getSelectedItem().toString()))) {
                        Object[] row = {
                                LocalDateTime.parse(l.getPayDate().toString())
                                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                l.getSendAccountName() + " → " + l.getReceiveAccountName(), "자금이동", "- " + l.getCash(),
                                l.getMemo() };
                        Object[] row2 = {
                                LocalDateTime.parse(l.getPayDate().toString())
                                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                l.getReceiveAccountName(), "입금", l.getCash(), l.getMemo() };
                        model.addRow(row);
                        model.addRow(row2);
                    }
                }
            }
        } else if (expense) {
            for (Spend l : spendList) {
                if (l.getAccountName().equals(comboBoxAccountList.getSelectedItem().toString())) {

                    Object[] row = {
                            LocalDateTime.parse(l.getPayDate().toString())
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            l.getAccountName(), "지출", l.getCost(), l.getExpense() };
                    model.addRow(row);
                }
            }
            if (transfer) {
                for (Transfer l : transferList) {
                    if (l.getSendAccountName().equals(comboBoxAccountList.getSelectedItem().toString())
                            || (l.getReceiveAccountName().equals(comboBoxAccountList.getSelectedItem().toString()))) {
                        Object[] row = {
                                LocalDateTime.parse(l.getPayDate().toString())
                                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                l.getSendAccountName() + " → " + l.getReceiveAccountName(), "자금이동", "- " + l.getCash(),
                                l.getMemo() };
                        Object[] row2 = {
                                LocalDateTime.parse(l.getPayDate().toString())
                                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                l.getReceiveAccountName(), "입금", l.getCash(), l.getMemo() };
                        model.addRow(row);
                        model.addRow(row2);
                    }
                }
            }
        } else if (transfer) {
            for (Transfer l : transferList) {
                if (l.getSendAccountName().equals(comboBoxAccountList.getSelectedItem().toString())
                        || (l.getReceiveAccountName().equals(comboBoxAccountList.getSelectedItem().toString()))) {
                    Object[] row = {
                            LocalDateTime.parse(l.getPayDate().toString())
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            l.getSendAccountName() + " → " + l.getReceiveAccountName(), "자금이동", "- " + l.getCash(),
                            l.getMemo() };
                    Object[] row2 = {
                            LocalDateTime.parse(l.getPayDate().toString())
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            l.getReceiveAccountName(), "입금", l.getCash(), l.getMemo() };
                    model.addRow(row);
                    model.addRow(row2);
                }
            }
        }

    }

    private void search_1() {
        boolean income = chckbxIncome.isSelected();
        boolean expense = chckbxExpense.isSelected();
        boolean transfer = chckbxTransfer.isSelected();

        List<Spend> spendList = dao.readAllSpend();
        List<Import> importList = dao.readAllImport();
        List<Transfer> transferList = dao.readAllTransfer();

        if (income) {
            for (Import l : importList) {
                Object[] row = {
                        LocalDateTime.parse(l.getPayDate().toString())
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        l.getAccountName(), "수입", l.getCost(), l.getIncome() };
                model.addRow(row);
            }
            if (expense) {
                for (Spend l : spendList) {

                    Object[] row = {
                            LocalDateTime.parse(l.getPayDate().toString())
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            l.getAccountName(), "지출", l.getCost(), l.getExpense() };
                    model.addRow(row);
                }
                if (transfer) {
                    for (Transfer l : transferList) {
                        Object[] row = {
                                LocalDateTime.parse(l.getPayDate().toString())
                                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                l.getSendAccountName() + " → " + l.getReceiveAccountName(), "자금이동", "- " + l.getCash(),
                                l.getMemo() };
                        Object[] row2 = {
                                LocalDateTime.parse(l.getPayDate().toString())
                                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                l.getReceiveAccountName(), "입금", l.getCash(), l.getMemo() };
                        model.addRow(row);
                        model.addRow(row2);
                    }
                }

            }
            if (transfer) {
                for (Transfer l : transferList) {
                    Object[] row = {
                            LocalDateTime.parse(l.getPayDate().toString())
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            l.getSendAccountName() + " → " + l.getReceiveAccountName(), "자금이동", "- " + l.getCash(),
                            l.getMemo() };
                    Object[] row2 = {
                            LocalDateTime.parse(l.getPayDate().toString())
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            l.getReceiveAccountName(), "입금", l.getCash(), l.getMemo() };
                    model.addRow(row);
                    model.addRow(row2);
                }
            }
        } else if (expense) {
            for (Spend l : spendList) {

                Object[] row = {
                        LocalDateTime.parse(l.getPayDate().toString())
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        l.getAccountName(), "지출", l.getCost(), l.getExpense() };
                model.addRow(row);
            }
            if (transfer) {
                for (Transfer l : transferList) {
                    Object[] row = {
                            LocalDateTime.parse(l.getPayDate().toString())
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            l.getSendAccountName() + " → " + l.getReceiveAccountName(), "자금이동", "- " + l.getCash(),
                            l.getMemo() };
                    Object[] row2 = {
                            LocalDateTime.parse(l.getPayDate().toString())
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            l.getReceiveAccountName(), "입금", l.getCash(), l.getMemo() };
                    model.addRow(row);
                    model.addRow(row2);
                }
            }
        } else if (transfer) {
            for (Transfer l : transferList) {
                Object[] row = {
                        LocalDateTime.parse(l.getPayDate().toString())
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        l.getSendAccountName() + " → " + l.getReceiveAccountName(), "자금이동", "- " + l.getCash(),
                        l.getMemo() };
                Object[] row2 = {
                        LocalDateTime.parse(l.getPayDate().toString())
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        l.getReceiveAccountName(), "입금", l.getCash(), l.getMemo() };
                model.addRow(row);
                model.addRow(row2);
            }
        }

    }

}
