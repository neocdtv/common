/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.stringutilsescapebash;

/**
 *
 * @author xix
 */
/**
 * Utility class for escaping strings for specific formats. Include methods to
 * escape strings that are being passed to the Android Shell.
 */
public class StringEscapeUtils {

    /**
     * Escapes a {@link String} for use in an Android shell command.
     *
     * @param str the {@link String} to escape
     * @return the Android shell escaped {@link String}
     */
    public static String escapeShell(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            switch (ch) {
                case '$':
                    out.append("\\$");
                    break;
                case '\\':
                    out.append("\\\\");
                    break;
                case '\'':
                    out.append("\\'");
                    break;
                case '"':
                    out.append("\\\"");
                    break;
                case '`':
                    out.append("\\`");
                    break;
                case '´':
                    out.append("\\´");
                    break;
                default:
                    out.append(ch);
                    break;
            }
        }
        return String.format("\"%s\"", out.toString());
    }
}
