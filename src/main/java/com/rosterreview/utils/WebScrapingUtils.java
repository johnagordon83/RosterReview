package com.rosterreview.utils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * A utility class of static methods designed to facilitate web scraping.
 */
public class WebScrapingUtils {

    // Do not instantiate this utility class.
    private WebScrapingUtils() {}

    /**
     * Creates a configured WebClient.
     *
     * @return  a configured WebClient
     */
    public static WebClient getConfiguredWebClient() {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setCssEnabled(false);

        return webClient;
    }

    /**
     * Gets the text content from the first {@link HtmlElement} found on the indicated
     * {@link HtmlPage} that matches the passed xpath <code>String</code>.
     *
     * @param page   the HtmlPage to search for the HtmlElement
     * @param xpath  an XPath <code>String</code> identifying the target HtmlElement
     * @return       the text content of the HtmlElement if it was found, otherwise an
     *               empty string
     */
    public static String getElementText(HtmlPage page, String xpath) {
        HtmlElement elem = page.getFirstByXPath(xpath);

        return elem != null ? elem.getTextContent().trim() : "";
    }

    /**
     * Gets the value of the attribute argument from the first {@link HtmlElement} found
     * on the indicated {@link HtmlPage} that matches the passed xpath <code>String</code> .
     *
     * @param page       the HtmlPage to search for the HtmlElement
     * @param xpath      an XPath <code>String</code> identifying the target HtmlElement
     * @param attribute  an element attribute name
     * @return           the value of the attribute if it exists, otherwise an empty string
     */
    public static String getElementAttribute(HtmlPage page, String xpath, String attribute) {
        HtmlElement elem = page.getFirstByXPath(xpath);

        return (elem != null && elem.hasAttribute(attribute)) ? elem.getAttribute(attribute) : "";
    }

    /**
     * Parses the <code>String</code> argument to <code>Integer</code> with
     * {@link Integer#valueOf(String)}. If parse fails, return the specified default value.
     *
     * @param value       the string to parse
     * @param defaultVal  the value to use if the parse fails
     * @return            the parsed <code>Integer</code> value or the specified default
     */
    public static Integer parseIntegerWithDefault(String value, Integer defaultVal) {
        try {
            return Integer.valueOf(value);
        } catch(NumberFormatException e) {
            return defaultVal;
        }
    }

    /**
     * Parses the <code>String</code> argument to <code>Double</code> with
     * {@link Double#valueOf(String)}. If parse fails, return the specified default value.
     *
     * @param value       the string to parse
     * @param defaultVal  the value to use if the parse fails
     * @return            the parsed <code>Double</code> value or the specified default
     */
    public static Double parseDoubleWithDefault(String value, Double defaultVal) {
        try {
            return Double.valueOf(value);
        } catch(NumberFormatException e) {
            return defaultVal;
        }
    }
}
