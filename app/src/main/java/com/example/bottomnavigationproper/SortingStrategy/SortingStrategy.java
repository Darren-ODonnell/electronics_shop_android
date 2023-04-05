package com.example.bottomnavigationproper.SortingStrategy;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SortingStrategy<T> {
        protected abstract Comparator<T> getAscendingComparator();

        @RequiresApi(api = Build.VERSION_CODES.N)
        public List<T> sort(List<T> list) {
                Comparator<T> ascendingComparator = getAscendingComparator();
                Comparator<T> descendingComparator = getDescendingComparator(ascendingComparator);

                if (isSortedAscending(list, ascendingComparator)) {
                        return sortDescending(list, descendingComparator);
                } else {
                        return sortAscending(list, ascendingComparator);
                }
        }
        @RequiresApi(api = Build.VERSION_CODES.N)
        private Comparator<T> getDescendingComparator(Comparator<T> comparator) {
                return comparator.reversed();
        }
        private boolean isSortedAscending(List<T> list, Comparator<T> comparator) {
                for (int i = 0; i < list.size() - 1; i++) {
                        if (comparator.compare(list.get(i), list.get(i + 1)) > 0) {
                                return false;
                        }
                }
                return true;
        }
        @RequiresApi(api = Build.VERSION_CODES.N)
        protected List<T> sortAscending(List<T> list, Comparator<T> comparator) {
                return list.stream()
                        .sorted(comparator)
                        .collect(Collectors.toList());
        }
        @RequiresApi(api = Build.VERSION_CODES.N)
        protected List<T> sortDescending(List<T> list, Comparator<T> comparator) {
                return list.stream()
                        .sorted(comparator)
                        .collect(Collectors.toList());
        }
}
