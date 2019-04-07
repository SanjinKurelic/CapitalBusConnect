package eu.sanjin.kurelic.cbc.business.viewmodel.info;

public class InfoItemButtonColumn implements InfoItemColumn {

    private InfoItemButtonType value;
    private String urlId;
    private InfoItemColumnType columnType;

    public InfoItemButtonColumn(InfoItemButtonType value, String urlId) {
        this.value = value;
        this.urlId = urlId;
        columnType = InfoItemColumnType.BUTTON;
    }

    @Override
    public InfoItemColumnType getColumnType() {
        return columnType;
    }

    @Override
    public InfoItemButtonType getValue() {
        return value;
    }

    public void setValue(InfoItemButtonType value) {
        this.value = value;
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

}
