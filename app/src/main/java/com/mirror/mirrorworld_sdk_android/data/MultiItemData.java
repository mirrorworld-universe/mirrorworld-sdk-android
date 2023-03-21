package com.mirror.mirrorworld_sdk_android.data;

import com.mirror.mirrorworld_sdk_android.enums.DemoAPI;

import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class MultiItemData {
    /**
     * A placeholder item representing a piece of content.
     */
    public static class MultiItem {
        public final DemoAPI id;
        public final String name;
        public final String resultDefault;
        public final String buttonText;
        public final String et1Hint;
        public final String et2Hint;
        public final String et3Hint;
        public final String et4Hint;
        public final String et5Hint;
        public final String et6Hint;
        public MultiItemSpinnerData spinnerData;
        public MultiItemSpinnerData spinnerData2;


        public MultiItem(DemoAPI id, String name, String details, String buttonText, String et1Hint
                , String et2Hint, String et3Hint, String et4Hint, String et5Hint, String et6Hint, MultiItemSpinnerData spinnerItems, MultiItemSpinnerData spinnerItems2) {
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
            this.spinnerData = spinnerItems;
            this.spinnerData2 = spinnerItems2;
        }

        public MultiItem(DemoAPI id, String name, String details, String buttonText, String et1Hint
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

    public static class MultiItemSpinnerData {
        public List<MultiItemSpinnerItem> items;
        public MultiItemSpinnerData(List<MultiItemSpinnerItem> items){
            this.items = items;
        }
    }
    public static class MultiItemSpinnerItem {
        public String ItemName;
        public int number;
        public MultiItemSpinnerItem(String ItemName, int number){
            this.ItemName = ItemName;
            this.number = number;
        }
    }
}