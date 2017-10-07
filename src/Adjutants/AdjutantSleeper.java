package Adjutants;

import org.jetbrains.annotations.NotNull;

/**
 * Класс AdjutantSleeper с помощью метода sleepUnity деактивирует выбранного юнита.
 */
public final class AdjutantSleeper {
    //Усыпляет юнита:
    @NotNull
    public static String sleepUnity(String string) {
        return string.substring(0, 2) + "?" + string.substring(3);
    }
}
