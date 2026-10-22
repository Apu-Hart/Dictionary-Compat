package dev.apuhart.dictionarycompat;

import net.neoforged.fml.loading.LoadingModList;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public final class CompatPlugin implements IMixinConfigPlugin {
    private static final Logger LOGGER = Logger.getLogger("dictionarycompat");
    private static final String TARGET = "com.github.alexthe666.alexsmobs.client.gui.GUIAnimalDictionary";
    private static final String ORIGINAL = "()Lnet/minecraft/resources/ResourceLocation;";
    private static final String COMPATIBLE = "()Ljava/lang/String;";

    public static boolean needsBridge(String alex, String citadel) {
        if (!citadel.equals("9e12468c49e5a95b7adbf22b3b4d05bc55565989b89c40b985cd73bdfe63c3c2")) {
            throw new IllegalStateException("Dictionary Compat: unreviewed Citadel SHA-256 " + citadel + "; remove this candidate and review the dependency combination.");
        }
        return switch (alex) {
            case "60ad623ae00f4d786e2290e9c1294607e37d69bd2c66175fdb15c0b2671e694e" -> true;
            case "6e502855f79e4c9f2a11d560a9b88a3ab295aa378c44b0f0a0dd95f95d0301a6" -> false;
            default -> throw new IllegalStateException("Dictionary Compat: unreviewed Alex's Mobs SHA-256 " + alex + "; remove this candidate and review the dependency combination.");
        };
    }

    public static void validateTarget(ClassNode target) {
        boolean original = target.methods.stream().anyMatch(method -> method.name.equals("getTextFileDirectory") && method.desc.equals(ORIGINAL)
                && (method.access & Opcodes.ACC_PUBLIC) != 0 && (method.access & (Opcodes.ACC_STATIC | Opcodes.ACC_ABSTRACT)) == 0);
        boolean conflict = target.methods.stream().anyMatch(method -> method.name.equals("getTextFileDirectory") && method.desc.equals(COMPATIBLE));
        if (!target.name.equals(TARGET.replace('.', '/')) || !target.superName.equals("com/github/alexthe666/citadel/client/gui/GuiBasicBook") || !original || conflict) {
            throw new IllegalStateException("Dictionary Compat: unexpected transformed target; refusing to overwrite another implementation.");
        }
    }

    private static String fingerprint(String modId) {
        var file = LoadingModList.get().getModFileById(modId);
        if (file == null) { throw new IllegalStateException("Dictionary Compat: missing dependency " + modId); }
        try (var input = Files.newInputStream(file.getFile().getFilePath())) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[65536];
            int count;
            while ((count = input.read(buffer)) != -1) { digest.update(buffer, 0, count); }
            return HexFormat.of().formatHex(digest.digest());
        } catch (IOException | NoSuchAlgorithmException error) {
            throw new IllegalStateException("Dictionary Compat: could not verify " + modId, error);
        }
    }

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (!targetClassName.equals(TARGET)) { throw new IllegalStateException("Dictionary Compat: unexpected target " + targetClassName); }
        boolean apply = needsBridge(fingerprint("alexsmobs"), fingerprint("citadel"));
        LOGGER.info(apply ? "Dictionary Compat: reviewed legacy binary, enabling client-only directory bridge." : "Dictionary Compat: original publisher binary already compatible; bridge not applied.");
        return apply;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        validateTarget(targetClass);
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        for (String descriptor : List.of(ORIGINAL, COMPATIBLE)) {
            if (targetClass.methods.stream().filter(method -> method.name.equals("getTextFileDirectory") && method.desc.equals(descriptor)).count() != 1) {
                throw new IllegalStateException("Dictionary Compat: bridge contract not satisfied " + descriptor);
            }
        }
    }
}
