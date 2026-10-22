package dev.apuhart.dictionarycompat.mixin;

import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(targets = "com.github.alexthe666.alexsmobs.client.gui.GUIAnimalDictionary", remap = false)
public abstract class AnimalDictionaryMixin {
    @Invoker(value = "getTextFileDirectory", remap = false)
    public abstract ResourceLocation dictionarycompat$resourceDirectory();

    public String getTextFileDirectory() {
        return dictionarycompat$resourceDirectory().toString();
    }
}
