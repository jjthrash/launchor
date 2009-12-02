package com.jimmythrasher.launchor;

import java.util.*;

import net.rim.device.api.util.Comparator;
import net.rim.device.api.collection.util.SortedReadableList;

import net.rim.device.api.ui.component.KeywordProvider;

public class CommandList extends SortedReadableList implements KeywordProvider {
    public CommandList(Vector countries) {
        super(new CommandListComparator());
        loadFrom(countries.elements());
    }

    public String[] getKeywords(Object element) {
        return new String[] { element.toString() };
    }

    final static class CommandListComparator implements Comparator {
        public int compare(Object o1, Object o2) {
            return o1.toString().toLowerCase().compareTo(o2.toString().toLowerCase());
        }
    }
}
