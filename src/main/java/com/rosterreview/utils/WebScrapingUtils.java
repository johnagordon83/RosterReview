package com.rosterreview.utils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class WebScrapingUtils {

    // Do not instantiate this utility class.
    private WebScrapingUtils() {}

    /**
     * Creates a configured WebClient.
     *
     * @return  A configured WebClient
     */
    public static WebClient getConfiguredWebClient() {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setCssEnabled(false);

        return webClient;
    }

    /**
     * Get the text content from the first HtmlElement found on the passed HtmlPage that
     * matches the passed xpath String.
     *
     * @param page The HtmlPage to search for the HtmlElement
     * @param xpath An XPath string identifying the target HtmlElement
     * @return The text content of the HtmlElement if it was found, otherwise an empty string
     */
    public static String getElementText(HtmlPage page, String xpath) {
        HtmlElement elem = page.getFirstByXPath(xpath);

        return elem != null ? elem.getTextContent().trim() : "";
    }

    /**
     * Get the value of the passed attribute from the first HtmlElement found on the passed
     * HtmlPage that matches the passed xpath String.
     *
     * @param page The HtmlPage to search for the HtmlElement
     * @param xpath An XPath string identifying the target HtmlElement
     * @param attribute
     * @return The value of the attribute if it exists, otherwise an empty string
     */
    public static String getElementAttribute(HtmlPage page, String xpath, String attribute) {
        HtmlElement elem = page.getFirstByXPath(xpath);

        return (elem != null && elem.hasAttribute(attribute)) ? elem.getAttribute(attribute) : "";
    }

    /**
     * Parse string argument to Integer with Integer.valueOf().  If parse fails, return the
     * specified default value.
     *
     * @param value The string to parse
     * @param defaultVal  The value to use if the parse fails
     * @return  The parsed Double value or the specified default
     */
    public static Integer parseIntegerWithDefault(String value, Integer defaultVal) {
        try {
            return Integer.valueOf(value);
        } catch(NumberFormatException e) {
            return defaultVal;
        }
    }

    /**
     * Parse string argument to Double with Double.valueOf().  If parse fails, return the
     * specified default value.
     *
     * @param value The string to parse
     * @param defaultVal  The value to use if the parse fails
     * @return  The parsed Double value or the specified default
     */
    public static Double parseDoubleWithDefault(String value, Double defaultVal) {
        try {
            return Double.valueOf(value);
        } catch(NumberFormatException e) {
            return defaultVal;
        }
    }
}
