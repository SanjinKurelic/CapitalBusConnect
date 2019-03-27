package eu.sanjin.kurelic.cbc.business.viewmodel.info;

public class InfoItem {

    private InfoItemColumnType columnType1;
    private String column1;

    private InfoItemColumnType columnType2;
    private String column2;

    private InfoItemColumnType columnType3;
    private String column3;

    private InfoItemButtonType buttonType;

    public InfoItem() {
        columnType1 = InfoItemColumnType.NONE;
        columnType2 = InfoItemColumnType.NONE;
        columnType3 = InfoItemColumnType.NONE;
        buttonType = InfoItemButtonType.NONE;
    }

    public InfoItemColumnType getColumnType1() {
        return columnType1;
    }

    public void setColumnType1(InfoItemColumnType columnType1) {
        this.columnType1 = columnType1;
    }

    public String getColumn1() {
        return column1;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public InfoItemColumnType getColumnType2() {
        return columnType2;
    }

    public void setColumnType2(InfoItemColumnType columnType2) {
        this.columnType2 = columnType2;
    }

    public String getColumn2() {
        return column2;
    }

    public void setColumn2(String column2) {
        this.column2 = column2;
    }

    public InfoItemColumnType getColumnType3() {
        return columnType3;
    }

    public void setColumnType3(InfoItemColumnType columnType3) {
        this.columnType3 = columnType3;
    }

    public String getColumn3() {
        return column3;
    }

    public void setColumn3(String column3) {
        this.column3 = column3;
    }

    public InfoItemButtonType getButtonType() {
        return buttonType;
    }

    public void setButtonType(InfoItemButtonType buttonType) {
        this.buttonType = buttonType;
    }
}
