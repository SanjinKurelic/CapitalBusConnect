package eu.sanjin.kurelic.cbc.business.viewmodel.info;

import java.util.ArrayList;

public class InfoItem {

    private ArrayList<InfoItemColumn> columns;

    public InfoItem() {
        columns = new ArrayList<>();
    }

    public void addColumn(InfoItemColumn column) {
        columns.add(column);
    }

    public ArrayList<InfoItemColumn> getColumns() {
        return columns;
    }
}
