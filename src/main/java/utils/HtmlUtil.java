package utils;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;


public class HtmlUtil {


    @SneakyThrows
    public String getContentFromPage(String url)  {
           return Jsoup.connect(getUri(url))
                   .sslSocketFactory(socketFactory())
                   .ignoreHttpErrors(true)
                   .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                   .get()
                   .body()
                   .html();

    }

    @SneakyThrows
    public String getUri(String url)  {
        return new URI(url).normalize().toURL().toString();
    }


//    private  String normalizeUrl(String urlString) throws URISyntaxException, MalformedURLException {
//        if (urlString == null || urlString.length()==0) {
//            return null;
//        }
//        URI uri = new URI(urlString);
//
//        if (!uri.isAbsolute()) {
//            throw new URISyntaxException(urlString, "Must provide an absolute URI for repositories");
//        }
//
//        uri = uri.normalize();
//        String path = uri.getPath();
//        if (path != null) {
//            path = path.replaceAll("//*/", "/"); // Collapse multiple forward slashes into 1.
//            if (path.length() > 0 && path.charAt(path.length() - 1) == '/') {
//                path = path.substring(0, path.length() - 1);
//            }
//        }
//       String uril = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(),
//               path, uri.getQuery(), uri.getFragment()).toURL().toString();
//        System.out.println(uril);
//        return uril;
//    }

    static private SSLSocketFactory socketFactory() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};

        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            SSLSocketFactory result = sslContext.getSocketFactory();

            return result;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException("Failed to create a SSL socket factory", e);
        }
    }
}
