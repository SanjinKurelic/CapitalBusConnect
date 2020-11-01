package eu.sanjin.kurelic.cbc.business.viewmodel.promotion;

public class PromotionItem {

  private String fromCity;
  private String fromCityUrl;
  private String toCity;
  private String toCityUrl;
  private String imageUrl;

  public PromotionItem() {
  }

  public String getFromCity() {
    return fromCity;
  }

  public void setFromCity(String fromCity) {
    this.fromCity = fromCity;
  }

  public String getToCity() {
    return toCity;
  }

  public void setToCity(String toCity) {
    this.toCity = toCity;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getToCityUrl() {
    return toCityUrl;
  }

  public void setToCityUrl(String toCityUrl) {
    this.toCityUrl = toCityUrl;
  }

  public String getFromCityUrl() {
    return fromCityUrl;
  }

  public void setFromCityUrl(String fromCityUrl) {
    this.fromCityUrl = fromCityUrl;
  }
}
