package com.votingcentral.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.AccessController;

import sun.security.action.GetPropertyAction;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
public class HtmlCodec {
    /**
     * It is the default array of symbols to be encoded into the html form.
     */
    public static char[] defSpecSymbols = new char[] {
            '"', '\'', '\\', ',', '<', '>', '(', ')', '.'
        };

    /**
     * This method is a wrapper for the <code>encode(String text , char[]
     * specSymbols)</code> method. It uses the defSpecialSymbols array as
     * special symbols.
     *
     * @param text It is the string to be encoded using the default array of
     *        special symbols.
     *
     * @return the encoded representation of the "text" parameter
     */
    public static String encode(final String text) {
        return encode(text, defSpecSymbols);
    }

    /**
     * This method is a wrapper for the <code>encode(String text , char[]
     * specSymbols)</code> method. It allows to specify special symbols as
     * elements of the String object ("String specSymbols" parameter).
     *
     * @param text It is the string to be encoded using special symbols from
     *        the "specSymbols" parameter.
     * @param specSymbols It is the set of special symbols to be encoded within
     *        the "text" parameter.
     *
     * @return the encoded representation of the "text" parameter
     */
    public static String encode(final String text, final String specSymbols) {
        final char[] cSpecSymbols;

        if ((specSymbols == null) || (specSymbols.length() == 0)) {
            cSpecSymbols = defSpecSymbols;
        } else {
            cSpecSymbols = specSymbols.toCharArray();
        }

        return encode(text, cSpecSymbols);
    }

    /**
     * It is the main encoding method. It encodes all special symbols in the
     * "text" parameter, using the specSymbols parameter as an array of
     * special symbols to be changed. AlSpecial symbols are translated to the
     * "&#xxx;" code, where the "xxx" part is the decimal code of the
     * correspondent special symbol.
     *
     * @param text It is the string to be encoded using special symbols from
     *        the "specSymbols" array.
     * @param specSymbols It is the array of special symbols to be encoded
     *        within the "text" parameter.
     *
     * @return the encoded representation of the "text" parameter
     */
    public static String encode(final String text, char[] specSymbols) {
        if (text == null) {
            return null;
        }

        if ((specSymbols == null) || (specSymbols.length == 0)) {
            specSymbols = defSpecSymbols;
        }

        StringBuffer buffer = new StringBuffer(text.length());
        char[] cText = text.toCharArray();

        for (int i = 0; i < cText.length; i++) {
            char cTextChar = cText[i]; // Speed up acess speed
            boolean isSpecial = false;

            for (int ss = 0; ss < specSymbols.length; ss++) {
                if (specSymbols[ss] == cTextChar) {
                    isSpecial = true;
                    buffer.append('&');
                    buffer.append('#');
                    buffer.append((int) cTextChar);
                    buffer.append(';');

                    break; // Exit the for cycle
                }
            }

            if (!isSpecial) {
                buffer.append(cTextChar);
            }
        }

        return buffer.toString();
    }

    /**
     * DOCUMENT ME!
     *
     * @param string DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static String trim(String string) {
        return string.replaceAll("&nbsp;", " ").replaceAll("&#xA0;", " ").trim();
    }

    /**
     * It is a wrapper method for converting URL strings into the
     * "x-www-form-urlencoded"  MIME format. This method is placed here to
     * standardize html encoding only, to access all functionality via one
     * class.
     *
     * @param url It is the string to be encoded using the standard
     *        <code>{@link java.net.URLEncoder}</code> class.
     *
     * @return DOCUMENT ME!
     */
    public static String encodeURL(final String url) {
        try {
            return URLEncoder.encode(url,
                (String) AccessController.doPrivileged(
                    new GetPropertyAction("file.encoding")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

            return url;
        }
    }
}
