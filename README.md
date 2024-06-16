# NextToGo
Android app that displays the next to go races using the Neds API.

The filter allows the races to be filtered by category. If a filter is unchecked, the races in that
category will be undisplayed.

If all of the filters are unchecked, the filter will revert to all filters being checked again as the
default setting is to display all categories.

Races are displayed in ascending order of start time and the countdown time shows the hours, minutes
and seconds between now and the start time. The countdown timer will descend to -60s before a race is
cleared from the list.
