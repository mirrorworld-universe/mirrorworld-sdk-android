package com.mirror.sdk.ui.market.widgets;

import com.mirror.sdk.ui.market.apis.responses.CollectionFilter;

public interface OnExpandedFilterClick {
    void OnExpand(CollectionFilter filter);

    void OnFold();
}
