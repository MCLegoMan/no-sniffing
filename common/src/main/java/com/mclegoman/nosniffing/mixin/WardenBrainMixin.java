package com.mclegoman.nosniffing.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.mob.WardenBrain;
import net.minecraft.entity.mob.WardenEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WardenBrain.class)
public abstract class WardenBrainMixin {
	@Inject(method = "addSniffActivities", at = @At("HEAD"), cancellable = true)
	private static void disableSniffing(Brain<WardenEntity> brain, CallbackInfo ci) {
		brain.setTaskList(Activity.SNIFF, 5, ImmutableList.of(StrollTask.create(0.5F), new WaitTask(30, 60)), MemoryModuleType.IS_SNIFFING);
		ci.cancel();
	}
}
