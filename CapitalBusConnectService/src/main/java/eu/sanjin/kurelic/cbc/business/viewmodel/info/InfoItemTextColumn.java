package eu.sanjin.kurelic.cbc.business.viewmodel.info;

public class InfoItemTextColumn implements InfoItemColumn {

  private String value;
  private InfoItemColumnType columnType;

  public InfoItemTextColumn(String value) {
    this.value = value;
    columnType = InfoItemColumnType.TEXT;
  }

  @Override
  public InfoItemColumnType getColumnType() {
    return columnType;
  }

  @Override
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
