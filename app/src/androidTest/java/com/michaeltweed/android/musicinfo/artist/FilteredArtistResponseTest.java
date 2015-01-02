package com.michaeltweed.android.musicinfo.artist;

import com.michaeltweed.android.musicinfo.apis.lastfm.pojos.Artist;
import com.michaeltweed.android.musicinfo.apis.lastfm.pojos.ArtistResponse;
import com.michaeltweed.android.musicinfo.apis.lastfm.pojos.Bio;
import com.michaeltweed.android.musicinfo.apis.lastfm.pojos.Image;
import com.michaeltweed.android.musicinfo.apis.lastfm.pojos.Stats;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class FilteredArtistResponseTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();

    }

    public void testArtistBioOfNullParsedSuccessfully() {
        ArtistResponse response = getArtistResponseWithBio(null);

        FilteredArtistResponse filteredResponse = new FilteredArtistResponse(response);

        assertEquals("No artist biography available", filteredResponse.getArtistBio());
    }

    public void testArtistBioOfEmptyParsedSuccessfully() {
        ArtistResponse response = getArtistResponseWithBio("");

        FilteredArtistResponse filteredResponse = new FilteredArtistResponse(response);

        assertEquals("No artist biography available", filteredResponse.getArtistBio());
    }

    public void testArtistBioOfValidStringParsedSuccessfully() {
        ArtistResponse response = getArtistResponseWithBio("Test artist bio");

        FilteredArtistResponse filteredResponse = new FilteredArtistResponse(response);

        assertEquals("Test artist bio", filteredResponse.getArtistBio());
    }

    public void testArtistPlayCountOfNullParsedSuccessfully() {
        ArtistResponse response = getArtistResponseWithPlayCount(null);

        FilteredArtistResponse filteredResponse = new FilteredArtistResponse(response);

        assertEquals("No personal statistics available", filteredResponse.getArtistPlayCount());
    }

    public void testArtistPlayCountOfEmptyParsedSuccessfully() {
        ArtistResponse response = getArtistResponseWithPlayCount("");

        FilteredArtistResponse filteredResponse = new FilteredArtistResponse(response);

        assertEquals("No personal statistics available", filteredResponse.getArtistPlayCount());
    }

    public void testArtistPlayCountOfZeroParsedSuccessfully() {
        ArtistResponse response = getArtistResponseWithPlayCount("0");

        FilteredArtistResponse filteredResponse = new FilteredArtistResponse(response);

        assertEquals("You have listened to this artist 0 times", filteredResponse.getArtistPlayCount());
    }

    public void testArtistPlayCountOfOneParsedSuccessfully() {
        ArtistResponse response = getArtistResponseWithPlayCount("1");

        FilteredArtistResponse filteredResponse = new FilteredArtistResponse(response);

        assertEquals("You have listened to this artist 1 time", filteredResponse.getArtistPlayCount());
    }

    public void testArtistPlayCountGreaterThanOneParsedSuccessfully() {
        ArtistResponse response = getArtistResponseWithPlayCount("100");

        FilteredArtistResponse filteredResponse = new FilteredArtistResponse(response);

        assertEquals("You have listened to this artist 100 times", filteredResponse.getArtistPlayCount());
    }

    public void testArtistImageListWithNullDataParsedSuccessfully() {
        ArtistResponse response = getArtistResponseWithImages(null);

        FilteredArtistResponse filteredResponse = new FilteredArtistResponse(response);

        assertEquals(false, filteredResponse.hasArtistImageUrl());
        assertEquals(null, filteredResponse.getArtistImageUrl());
    }

    public void testArtistImageListWithEmptyStringDataParsedSuccessfully() {
        List<Image> images = new ArrayList<>();
        images.add(new Image("", "small"));

        ArtistResponse response = getArtistResponseWithImages(images);

        FilteredArtistResponse filteredResponse = new FilteredArtistResponse(response);

        assertEquals(false, filteredResponse.hasArtistImageUrl());
        assertEquals(null, filteredResponse.getArtistImageUrl());
    }

    public void testArtistImageListWithEmptyArrayDataParsedSuccessfully() {

        ArtistResponse response = getArtistResponseWithImages(new ArrayList<Image>());

        FilteredArtistResponse filteredResponse = new FilteredArtistResponse(response);

        assertEquals(false, filteredResponse.hasArtistImageUrl());
        assertEquals(null, filteredResponse.getArtistImageUrl());
    }

    public void testArtistImageListWithValidDataParsedSuccessfully() {
        List<Image> images = new ArrayList<>();
        images.add(new Image("url1", "small"));
        images.add(new Image("url2", "medium"));
        images.add(new Image("url3", "large"));

        ArtistResponse response = getArtistResponseWithImages(images);

        FilteredArtistResponse filteredResponse = new FilteredArtistResponse(response);

        assertEquals(true, filteredResponse.hasArtistImageUrl());
        assertEquals("url3", filteredResponse.getArtistImageUrl());
    }

    public void testIsEmptyResponseWithValidData() {
        FilteredArtistResponse response1 = new FilteredArtistResponse("test", "test2", "test3");
        assertEquals(false, response1.isEmptyResponse());

        FilteredArtistResponse response2 = new FilteredArtistResponse(null, "test2", "test3");
        assertEquals(false, response2.isEmptyResponse());

        FilteredArtistResponse response3 = new FilteredArtistResponse("test", null, "test3");
        assertEquals(false, response3.isEmptyResponse());

        FilteredArtistResponse response4 = new FilteredArtistResponse("test", "test2", null);
        assertEquals(false, response4.isEmptyResponse());

        FilteredArtistResponse response5 = new FilteredArtistResponse(null, null, "test3");
        assertEquals(false, response5.isEmptyResponse());

        FilteredArtistResponse response6 = new FilteredArtistResponse(null, "test2", null);
        assertEquals(false, response6.isEmptyResponse());

        FilteredArtistResponse response7 = new FilteredArtistResponse("test", null, null);
        assertEquals(false, response7.isEmptyResponse());

        FilteredArtistResponse response8 = new FilteredArtistResponse("test", "test2", null);
        assertEquals(false, response8.isEmptyResponse());

        FilteredArtistResponse response9 = new FilteredArtistResponse(null, null, null);
        assertEquals(true, response9.isEmptyResponse());

        FilteredArtistResponse response10 = new FilteredArtistResponse("", "", "");
        assertEquals(true, response10.isEmptyResponse());

        FilteredArtistResponse response11 = new FilteredArtistResponse("No artist biography available", "test2", "test3");
        assertEquals(false, response11.isEmptyResponse());

        FilteredArtistResponse response12 = new FilteredArtistResponse("No artist biography available", null, null);
        assertEquals(true, response12.isEmptyResponse());

        FilteredArtistResponse response13 = new FilteredArtistResponse(null, "No personal statistics available", null);
        assertEquals(true, response13.isEmptyResponse());
    }





    /* PRIVATE HELPER METHODS */

    private ArtistResponse getArtistResponseWithPlayCount(String playCount) {
        Stats stats = new Stats(null, null, playCount);
        Artist artist = new Artist(null, null, null, null, null, null, stats, null, null, null);
        return new ArtistResponse(artist);
    }

    private ArtistResponse getArtistResponseWithBio(String bio) {
        Bio artistBio = new Bio(null, null, null, bio, null, null);
        Artist artist = new Artist(null, null, null, null, null, null, null, null, null, artistBio);
        return new ArtistResponse(artist);
    }

    private ArtistResponse getArtistResponseWithImages(List<Image> images) {
        Artist artist = new Artist(null, null, null, images, null, null, null, null, null, null);
        return new ArtistResponse(artist);
    }
}