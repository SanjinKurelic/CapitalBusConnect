package eu.sanjin.kurelic.cbc.business.viewmodel.promotion;

public class PromotionItem {

    private String fromCity;
    private String toCity;
    private String imageUrl;

    public PromotionItem() {
    }

    public PromotionItem(String fromCity, String toCity, double price, String imageUrl) {
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.imageUrl = imageUrl;
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

}
