package view.tables;

import database.models.Good;
import database.models.GoodType;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class GoodTableModel extends DefaultTableModel {
    private final List<Good> goods;

    public GoodTableModel(List<Good> goods){
        this.goods = goods;
        String[] columnsNames = { "Товарная группа", "Название", "Номенклатурный номер","Цена","Кол-во" };
        ArrayList<Object[]> rows = new ArrayList<>();
        for (Good good : goods) {
            rows.add(new Object[]{ good.getGoodType().getName(), good.getName(), good.getNomenclaturenumber(),good.getPrice(),good.getAmount() });
        }
        this.setDataVector(rows.toArray(new Object[0][]), columnsNames);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public List<Good> getGoods() {
        return goods;
    }
}
