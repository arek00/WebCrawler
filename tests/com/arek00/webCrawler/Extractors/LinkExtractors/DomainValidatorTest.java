package com.arek00.webCrawler.Extractors.LinkExtractors;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Admin on 2015-09-02.
 */
public class DomainValidatorTest {

    private final String straightLink = "http://www.example.com";
    private final String ftpStraightLink = "ftp://www.example.com";
    private final String torrentStraightLink = "torrent://www.example.com";
    private final String urlWithPath = "torrent://www.example.com/index.php";
    private final String urlWithPathAndParameter = "torrent://www.example.com/index.php?true=false";
    private final String wrongDomain = "torrent://www.examples.com/";
    private final String wrongDomainWithRedirect = "torrent://www.examples.com/redirectLink=www.example.com";
    private final String emptyAddress = "";


    private final String domain = "example.com";

    @Test
    public void linksShouldBeInsideDomain() {
        assertTrue(DomainValidator.isInsideDomain(straightLink, domain));
        assertTrue(DomainValidator.isInsideDomain(ftpStraightLink, domain));
        assertTrue(DomainValidator.isInsideDomain(torrentStraightLink, domain));
        assertTrue(DomainValidator.isInsideDomain(urlWithPath, domain));
        assertTrue(DomainValidator.isInsideDomain(urlWithPathAndParameter, domain));
    }

    @Test
    public void linksShouldNotBeInsideDomain() {
        assertFalse(DomainValidator.isInsideDomain(wrongDomain, domain));
        assertFalse(DomainValidator.isInsideDomain(wrongDomainWithRedirect, domain));
        assertFalse(DomainValidator.isInsideDomain(emptyAddress, domain));
    }
}