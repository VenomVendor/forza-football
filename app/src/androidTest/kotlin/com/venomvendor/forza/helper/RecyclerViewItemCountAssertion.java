/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 16-Sep-2019.
 */

package com.venomvendor.forza.helper;

import android.view.View;

import org.hamcrest.Matcher;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.matcher.ViewMatchers;

import static org.hamcrest.Matchers.is;

/**
 * Credits to https://stackoverflow.com/a/43207009/1008278
 */
public class RecyclerViewItemCountAssertion implements ViewAssertion {

    private final Matcher<Integer> matcher;

    private RecyclerViewItemCountAssertion(Matcher<Integer> matcher) {
        this.matcher = matcher;
    }

    public static RecyclerViewItemCountAssertion withItemCount(int expectedCount) {
        return withItemCount(is(expectedCount));
    }

    public static RecyclerViewItemCountAssertion withItemCount(Matcher<Integer> matcher) {
        return new RecyclerViewItemCountAssertion(matcher);
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        ViewMatchers.assertThat(adapter.getItemCount(), matcher);
    }
}
