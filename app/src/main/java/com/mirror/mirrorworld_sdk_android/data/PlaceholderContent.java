package com.mirror.mirrorworld_sdk_android.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<PlaceholderItem> ITEMS = new ArrayList<PlaceholderItem>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<String, PlaceholderItem> ITEM_MAP = new HashMap<String, PlaceholderItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPlaceholderItem(i));
        }
    }

    private static void addItem(PlaceholderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id+"", item);
    }

    private static PlaceholderItem createPlaceholderItem(int position) {
        return new PlaceholderItem((position), "Item " + position, makeDetails(position),"","");
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A placeholder item representing a piece of content.
     */
    public static class PlaceholderItem {
        public final int id;
        public final String content;
        public final String details;
        public final String buttonText;
        public final String inputHint;

        public PlaceholderItem(int id, String content, String details,String buttonText,String inputHint) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.buttonText = buttonText;
            this.inputHint = inputHint;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}