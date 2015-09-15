package com.arek00.webCrawler.Model;

/**
 * Value object to store and pass information about downloaded files
 */
public class DownloadingStatistic {

    private int downloadedFiles;
    private int queueLength;
    private int visitedLinks;
    private int rejectedLinks;

    public void setRejectedLinks(int rejectedLinks) {
        this.rejectedLinks = rejectedLinks;
    }

    public int getDownloadedFiles() {
        return downloadedFiles;
    }

    public void setDownloadedFiles(int downloadedFiles) {
        this.downloadedFiles = downloadedFiles;
    }

    public int getQueueLength() {
        return queueLength;
    }

    public void setQueueLength(int queueLength) {
        this.queueLength = queueLength;
    }

    public int getVisitedLinks() {
        return visitedLinks;
    }

    public void setVisitedLinks(int visitedLinks) {
        this.visitedLinks = visitedLinks;
    }

    public int getRejectedLinks() {
        return visitedLinks - downloadedFiles;
    }

}
