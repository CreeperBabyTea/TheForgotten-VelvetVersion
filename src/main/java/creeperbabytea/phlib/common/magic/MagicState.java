package creeperbabytea.phlib.common.magic;

import creeperbabytea.phlib.common.magic.spellwork.SpellState;

public class MagicState {
    /**
     * <p>The complexity decides the "difficulty" for a wizard to use the very magic.</p>
     * <p>Range: 0.0(Succeeds the first try) ~ 10.0(Almost impossible) </p>
     */
    private final float COMPLEXITY;
    /**
     * <p>The way it works depends on the type of magic.</p>
     * <p>Range:-1.0 ~ 1.0</p>
     * <p>See {@link SpellState}</p>
     */
    private final float GRAYSCALE;

    public MagicState(float complexity, float grayscale) {
        COMPLEXITY = complexity;
        GRAYSCALE = grayscale;
    }

    public float getComplexity() {
        return COMPLEXITY;
    }

    public float getGrayscale() {
        return GRAYSCALE;
    }
}
