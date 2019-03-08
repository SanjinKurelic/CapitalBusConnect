package eu.sanjin.kurelic.cbc.business.viewmodel.city;

public class CityInfoItem {

    private String name;
    private String description;
    private String imageUrl;

    public CityInfoItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageUrl;
    }

    public void setImageName(String imageName) {
        this.imageUrl = imageName;
    }
}
