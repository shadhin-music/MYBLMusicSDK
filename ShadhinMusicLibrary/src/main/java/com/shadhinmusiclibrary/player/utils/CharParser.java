package com.shadhinmusiclibrary.player.utils;

public class CharParser {

    /**
     * Replaces <...> tag inside the url with a image size
     *
     * @param url  of the image
     * @param type of the image
     * @return replaced url
     */
    public static String getImageFromTypeUrl(String url, String type) {
        if (url == null || url.isEmpty()) {
            return "";
        }

        switch (type) {
            case "A":
                return url.replaceAll("</?.*?>", "300");
            case "PDB":
                return url.replaceAll("</?.*?>", "450");
            case "M":
                return url.replaceAll("</?.*?>", "1200");
            case "V":
                return url.replaceAll("</?.*?>", "1280");
            case "PP":
                return url.replaceAll("</?.*?>", "320");
            case "PL":
                return url.replaceAll("</?.*?>", "225");

            case "PLB":
                return url.replaceAll("</?.*?>", "780");
            default:
                return url.replaceAll("</?.*?>", "300");
        }
    }

    /**
     * Removes empty spaces
     *
     * @param data to be processed
     * @return replaced data
     */
    public static String removeEmptySpaces(String data) {
        return data.replaceAll("\\s+", "");
    }

    /**
     * Removes multiple spaces and replaces them with a single space
     * <p>
     * https://stackoverflow.com/questions/2932392/java-how-to-replace-2-or-more-spaces-with-single-space-in-string-and-delete-lead
     *
     * @param data to be processed
     * @return replaced data
     */
    public static String replaceMultipleSpaces(String data) {
        return data.replaceAll("\\s{2,}", " ").trim();
        //return data.replaceAll("^ +| +$|( )+", "$1");
    }
}