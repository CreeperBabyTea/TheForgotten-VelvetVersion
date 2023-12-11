package creeperbabytea.phlib.common.magic.general.particles;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.IParticleData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class ParticleSet {
    private final List<ParticleEntry> particles = new ArrayList<>();

    public void add(ParticleEntry entry) {
        this.particles.add(entry);
    }

    public void draw(ClientWorld world, boolean alwaysShow, double x, double y, double z, double dx, double dy, double dz) {
        AtomicBoolean b = new AtomicBoolean(false);
        this.particles.forEach(entry -> {
            if (world.rand.nextFloat() <= entry.getWeight()) {
                world.addParticle(entry.getParticle(), alwaysShow, x, y, z, dx, dy, dz);
                b.set(true);
            }
        });
        if (!b.get()) {
            world.addParticle(particles.get(particles.size() - 1).getParticle(), alwaysShow, x, y, z, dx, dy, dz);
        }
    }

    public void forEach(Consumer<ParticleEntry> consumer) {
        particles.forEach(consumer);
    }

    public static class ParticleEntry {
        private final IParticleData particle;
        private final float weight;

        public ParticleEntry(IParticleData particle, float weight) {
            this.particle = particle;
            this.weight = weight;
        }

        public IParticleData getParticle() {
            return particle;
        }

        public float getWeight() {
            return weight;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Map<Integer, IParticleData> particles = new HashMap<>();
        private int total;

        private Builder() {
        }

        public Builder put(int weight, IParticleData particle) {
            this.total += weight;
            this.particles.put(weight, particle);
            return this;
        }

        public ParticleSet build() {
            List<Map.Entry<Integer, IParticleData>> entries = new ArrayList<>(particles.entrySet());
            entries.sort(Map.Entry.comparingByKey());

            ParticleSet result = new ParticleSet();
            entries.forEach(entry -> {
                result.add(new ParticleEntry(entry.getValue(), (float) entry.getKey() / total));
            });
            return result;
        }
    }
}
