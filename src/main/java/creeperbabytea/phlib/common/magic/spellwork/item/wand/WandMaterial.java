package creeperbabytea.phlib.common.magic.spellwork.item.wand;

public class WandMaterial {
    public static class WandWood {
        private final float stiffness;
        private final float betrayal;
        private final short sustainability;

        public WandWood(float stiffness, float betrayal, short sustainability) {
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

        public short getSustainability() {
            return sustainability;
        }
    }

    public static class WandCore {
        private final float strength;
        private final float loyalty;
        private final short sustainability;

        public WandCore(float strength, float loyalty, short sustainability) {
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

        public short getSustainability() {
            return sustainability;
        }
    }
}
