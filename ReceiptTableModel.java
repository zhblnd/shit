package view.tables;

import database.models.Receipt;
import database.models.WriteOffAct;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class ReceiptTableModel extends DefaultTableModel {
    private final List<Receipt> receipts;

    public ReceiptTableModel(List<Receipt> receipts){
        this.receipts = receipts;
        String[] columnsNames = { "Дата", "ВРемя","Сумма","Инф. о поставщике" };
        ArrayList<Object[]> rows = new ArrayList<>();
        for (Receipt receipt : receipts) {
            rows.add(new Object[]{ receipt.getDate(), receipt.getTime(),receipt.getPrice(),receipt.getSupplierinfo() });
        }
        this.setDataVector(rows.toArray(new Object[0][]), columnsNames);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }
}
