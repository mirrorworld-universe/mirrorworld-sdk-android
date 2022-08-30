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
public class MultiItemData {

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<MultiItem> ITEMS = new ArrayList<MultiItem>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<String, MultiItem> ITEM_MAP = new HashMap<String, MultiItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPlaceholderItem(i));
        }
    }

    private static void addItem(MultiItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id+"", item);
    }

    private static MultiItem createPlaceholderItem(int position) {
        return new MultiItem((position), "Item " + position, makeDetails(position),"","",null,null,null,null,null);
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
    public static class MultiItem {
        public final int id;
        public final String name;
        public final String resultDefault;
        public final String buttonText;
        public final String et1Hint;
        public final String et2Hint;
        public final String et3Hint;
        public final String et4Hint;
        public final String et5Hint;
        public final String et6Hint;

        public MultiItem(int id, String name, String details, String buttonText, String et1Hint
                , String et2Hint, String et3Hint, String et4Hint, String et5Hint, String et6Hint) {
            this.id = id;
            this.name = name;
            this.resultDefault = details;
            this.buttonText = buttonText;
            this.et1Hint = et1Hint;
            this.et2Hint = et2Hint;
            this.et3Hint = et3Hint;
            this.et4Hint = et4Hint;
            this.et5Hint = et5Hint;
            this.et6Hint = et6Hint;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}