package dev.apuhart.dictionarycompat;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public final class PolicyCheck {
    public static void main(String[] arguments) {
        String citadel = "9e12468c49e5a95b7adbf22b3b4d05bc55565989b89c40b985cd73bdfe63c3c2";
        if (!CompatPlugin.needsBridge("60ad623ae00f4d786e2290e9c1294607e37d69bd2c66175fdb15c0b2671e694e", citadel)) { throw new AssertionError("Legacy not enabled"); }
        if (CompatPlugin.needsBridge("6e502855f79e4c9f2a11d560a9b88a3ab295aa378c44b0f0a0dd95f95d0301a6", citadel)) { throw new AssertionError("Publisher binary modified"); }
        reject(() -> CompatPlugin.needsBridge("unknown", citadel));
        reject(() -> CompatPlugin.needsBridge("60ad623ae00f4d786e2290e9c1294607e37d69bd2c66175fdb15c0b2671e694e", "unknown"));
        ClassNode target = new ClassNode();
        target.name = "com/github/alexthe666/alexsmobs/client/gui/GUIAnimalDictionary";
        target.superName = "com/github/alexthe666/citadel/client/gui/GuiBasicBook";
        target.methods.add(new MethodNode(Opcodes.ACC_PUBLIC, "getTextFileDirectory", "()Lnet/minecraft/resources/ResourceLocation;", null, null));
        CompatPlugin.validateTarget(target);
        target.methods.add(new MethodNode(Opcodes.ACC_PUBLIC, "getTextFileDirectory", "()Ljava/lang/String;", null, null));
        reject(() -> CompatPlugin.validateTarget(target));
        target.methods.clear();
        reject(() -> CompatPlugin.validateTarget(target));
        System.out.println("POLICY_CHECK PASS 7 cases; runtime linking/render tests remain separate");
    }

    private static void reject(Runnable operation) {
        try { operation.run(); }
        catch (IllegalStateException expected) { return; }
        throw new AssertionError("Unsafe input was accepted");
    }
}
