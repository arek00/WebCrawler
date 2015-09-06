package com.arek00.webCrawler.Queues;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 */
public class SimpleLinksQueueTest {

    private static SimpleLinksQueue queue;

    private static String[] links = {
            "http://www.example.com",
            "http://www.example2.com",
            "http://www.example3.com",
            "http://www.example4.com",
            "http://www.example5.com",
            "http://www.example6.com",
            "http://www.example7.com"
    };

    @BeforeClass
    public static void initQueue() {
        queue = new SimpleLinksQueue(new VisitedLinkRegister());

        for (String link : links) {
            queue.add(link);
        }
    }


    @Test
    public void shouldNotLetAddDuplicatedLink() {
        int startingSize = queue.size();

        queue.add(links[0]);
        queue.add(links[1]);
        queue.add(links[2]);

        int endingSize = queue.size();

        assertTrue(startingSize == endingSize);
    }

}