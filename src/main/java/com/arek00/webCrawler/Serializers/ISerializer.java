package com.arek00.webCrawler.Serializers;

import java.io.File;
import java.io.InputStream;

/**
 */
public interface ISerializer {
    public void serialize(Object objectToSerialize, File file) throws Exception;

    public <T> T deserialize(Class<T> objectClass, File source) throws Exception;

    public <T> T deserialize(Class<T> objectClass, InputStream source) throws Exception;

}
