package com.yao.app.xml;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import java.util.Iterator;

/**
 * 没必要，直接使用{@link org.springframework.util.xml.SimpleNamespaceContext SimpleNamespaceContext}
 *
 * Created by yaolei02 on 2017/4/11.
 */
public class MyNamespaceContext implements NamespaceContext {

    private final String defaultNamespaceUri;

    public MyNamespaceContext(String defaultNamespaceUri) {
        this.defaultNamespaceUri = defaultNamespaceUri;
    }

    @Override
    public String getNamespaceURI(String prefix) {
        if (XMLConstants.XML_NS_PREFIX.equals(prefix)) {
            return XMLConstants.XML_NS_URI;
        } else if (XMLConstants.XMLNS_ATTRIBUTE.equals(prefix)) {
            return XMLConstants.XMLNS_ATTRIBUTE_NS_URI;
        } else if (XMLConstants.DEFAULT_NS_PREFIX.equals(prefix)) {
            return this.defaultNamespaceUri;
        }

        return XMLConstants.NULL_NS_URI;
    }

    @Override
    public String getPrefix(String namespaceURI) {
        return null;
    }

    @Override
    public Iterator getPrefixes(String namespaceURI) {
        return null;
    }
}
