/* Created by Sanjin Kurelić (kurelic@sanjin.eu) */

/*global google, $ */

/*
 * Google maps - used in JSP
 */

// noinspection JSUnusedGlobalSymbols
function initMap() {
    "use strict";
    var map, position, marker;
    position = {lat: 45.803934, lng: 15.993249};

    // noinspection JSUnresolvedVariable,JSUnresolvedFunction
    map = new google.maps.Map($("location-map"), {
        center: position,
        zoom: 16,
        mapTypeControlOptions: {
            mapTypeIds: [google.maps.MapTypeId.ROADMAP]
        },
        mapTypeControl: false,
        streetViewControl: false,
        styles: [
            {elementType: "geometry", stylers: [{color: "#242f3e"}]},
            {elementType: "labels.text.stroke", stylers: [{color: "#242f3e"}]},
            {elementType: "labels.text.fill", stylers: [{color: "#746855"}]},
            {
                featureType: "administrative.locality",
                stylers: [{visibility: "off"}]
            },
            {
                featureType: "poi",
                stylers: [{visibility: "off"}]
            },
            {
                featureType: "road",
                elementType: "geometry",
                stylers: [{color: "#38414e"}]
            },
            {
                featureType: "road",
                elementType: "geometry.stroke",
                stylers: [{color: "#212a37"}]
            },
            {
                featureType: "road",
                elementType: "labels.text.fill",
                stylers: [{color: "#9ca5b3"}]
            },
            {
                featureType: "road.highway",
                elementType: "geometry",
                stylers: [{color: "#746855"}]
            },
            {
                featureType: "road.highway",
                elementType: "geometry.stroke",
                stylers: [{color: "#1f2835"}]
            },
            {
                featureType: "road.highway",
                elementType: "labels.text.fill",
                stylers: [{color: "#f3d19c"}]
            },
            {
                featureType: "transit",
                stylers: [{visibility: "off"}]
            },
            {
                featureType: "water",
                elementType: "geometry",
                stylers: [{color: "#17263c"}]
            },
            {
                featureType: "water",
                elementType: "labels.text.fill",
                stylers: [{color: "#515c6d"}]
            },
            {
                featureType: "water",
                elementType: "labels.text.stroke",
                stylers: [{color: "#17263c"}]
            }
        ]
    });

    // marker
    // noinspection JSUnresolvedVariable,JSUnresolvedFunction
    marker = new google.maps.Marker({
        position: position,
        title: "Capital Bus Connect",
        icon: "http://maps.google.com/mapfiles/ms/icons/ylw-pushpin.png",
        size: new google.maps.Size(80, 80)
    });
    // noinspection JSUnresolvedFunction
    marker.setMap(map);
}