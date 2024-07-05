package com.mclegoman.nosniffing.mixin;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.world.World;
import net.minecraft.world.event.Vibrations;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WardenEntity.class)
public abstract class WardenEntityMixin extends HostileEntity implements Vibrations {
	@Shadow public AnimationState roaringAnimationState;

	@Shadow public AnimationState diggingAnimationState;

	@Shadow public AnimationState emergingAnimationState;

	protected WardenEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}
	@Inject(method = "onTrackedDataSet", at = @At("HEAD"), cancellable = true)
	private void onTrackedDataSet(TrackedData<?> data, CallbackInfo ci) {
		if (POSE.equals(data)) {
			switch (this.getPose()) {
				case EMERGING -> this.emergingAnimationState.start(this.age);
				case DIGGING -> this.diggingAnimationState.start(this.age);
				case ROARING -> this.roaringAnimationState.start(this.age);
			}
		}
		super.onTrackedDataSet(data);
		ci.cancel();
	}
}
