package eu.sanjin.kurelic.cbc.business.viewmodel.search;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CitySearchResult {

    @JsonProperty("name")
    private String friendlyCityName;

    @JsonProperty("url")
    private String urlCityName;

    public CitySearchResult() {
    }

    public CitySearchResult(String friendlyCityName, String urlCityName) {
        this.friendlyCityName = friendlyCityName;
        this.urlCityName = urlCityName;
    }

    public String getFriendlyCityName() {
        return friendlyCityName;
    }

    public void setFriendlyCityName(String friendlyCityName) {
        this.friendlyCityName = friendlyCityName;
    }

    public String getUrlCityName() {
        return urlCityName;
    }

    public void setUrlCityName(String urlCityName) {
        this.urlCityName = urlCityName;
    }
}
