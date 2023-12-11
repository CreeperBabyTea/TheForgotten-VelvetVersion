package creeperbabytea.phlib.common.magic.spellwork.item.wand;

/**
 * See {@link WandState}
 */
public class WandMaterial {
    public static class WandWood {
        private final float stiffness;
        private final float betrayal;
        private final int sustainability;

        public WandWood(float stiffness, float betrayal, int sustainability) {
            this.stiffness = stiffness;
            this.betrayal = betrayal;
            this.sustainability = sustainability;
        }

        public float getStiffness() {
            return stiffness;
        }

        public float getBetrayal() {
            return betrayal;
        }

        public int getSustainability() {
            return sustainability;
        }
    }

    public static class WandCore {
        private final float strength;
        private final float loyalty;
        private final int sustainability;

        public WandCore(float strength, float loyalty, int sustainability) {
            this.strength = strength;
            this.loyalty = loyalty;
            this.sustainability = sustainability;
        }

        public float getStrength() {
            return strength;
        }

        public float getLoyalty() {
            return loyalty;
        }

        public int getSustainability() {
            return sustainability;
        }
    }
}
