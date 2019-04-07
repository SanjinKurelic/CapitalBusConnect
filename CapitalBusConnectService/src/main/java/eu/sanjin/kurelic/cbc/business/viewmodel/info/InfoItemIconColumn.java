package eu.sanjin.kurelic.cbc.business.viewmodel.info;

public class InfoItemIconColumn implements InfoItemColumn {

    private InfoItemIconType value;
    private InfoItemColumnType columnType;

    public InfoItemIconColumn(InfoItemIconType value) {
        this.value = value;
        columnType = InfoItemColumnType.ICON;
    }

    @Override
    public InfoItemColumnType getColumnType() {
        return columnType;
    }

    @Override
    public InfoItemIconType getValue() {
        return value;
    }

    public void setValue(InfoItemIconType value) {
        this.value = value;
    }

}
