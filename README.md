# Tarot
Use Android Jetpack to see the future!

<img src="https://github.com/SpectralFergus/tarot/blob/master/wiki_imgs/TarotAppSample.jpg" width="300">

This is an app I'm making for fun which fetches tarot card data from a REST API to be displayed on an Android phone. I'm using it as my anchor-project as I explore tutorials around Android Jetpack and other best-practices.

## Current Functionality
Upon launch, a call is made to ekelen's tarot-api to fetch 3 random cards, which are displayed in a recyclerview of images and a descripton box.
Tapping on any card's image updates the description box with the details of that card.

## Currently in Progress:
Working on implementing ViewModel so that loadCardData() is not triggered upon configuration changes (i.e. screen rotation).

---
Tarot card data was obtained through "tarot-api" by @ekelen: https://github.com/ekelen/tarot-api

Tarot card images are scans of the vintage "Pamela-A" deck provided by Holly Voley at sacred-texts.com, and are within the public domain: http://www.sacred-texts.com/tarot/pkt/index.htm
