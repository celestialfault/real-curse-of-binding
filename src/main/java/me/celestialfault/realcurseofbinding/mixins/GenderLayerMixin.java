package me.celestialfault.realcurseofbinding.mixins;

import com.wildfire.main.entitydata.EntityConfig;
import com.wildfire.render.GenderArmorLayer;
import com.wildfire.render.GenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GenderLayer.class, GenderArmorLayer.class})
public class GenderLayerMixin {
	@Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At("HEAD"), cancellable = true)
	public void hideWithCurseOfBinding(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int packedLightIn, LivingEntity ent, float limbAngle, float limbDistance, float partialTicks, float animationProgress, float headYaw, float headPitch, CallbackInfo ci) {
		ItemStack chestplate = ent.getEquippedStack(EquipmentSlot.CHEST);
		if(!chestplate.isEmpty() && EnchantmentHelper.hasBindingCurse(chestplate)) {
			EntityConfig config = EntityConfig.getEntity(ent);
			if(config == null || !config.getArmorPhysicsOverride()) {
				ci.cancel();
			}
		}
	}
}
