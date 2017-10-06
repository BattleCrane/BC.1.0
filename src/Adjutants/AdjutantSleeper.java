package Adjutants;

import org.jetbrains.annotations.NotNull;

/**
 * Created by мсиайнина on 05.10.2017.
 */
public final class AdjutantSleeper {
    //Усыпляет юнита:
    @NotNull
    public static String sleepUnity(String string) {
        return string.substring(0, 2) + "?" + string.substring(3);
    }
}
