package com.fuping;

import org.apache.http.conn.ssl.TrustStrategy;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class MyTrustStrategy
        implements TrustStrategy
{
    public boolean isTrusted(X509Certificate[] chain, String authType)
            throws CertificateException
    {
        return true;
    }
}