package com.bigking123.rotp_whitesnake.action.stand;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.ActionTarget.TargetType;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.StandUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class RemovingTheMemoryDisk extends StandEntityAction {

    public RemovingTheMemoryDisk(StandEntityAction.Builder builder) {
        super(builder);
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide() && task.getTarget().getType() == TargetType.ENTITY && task.getTarget().getEntity() instanceof LivingEntity) {
            LivingEntity target = StandUtil.getStandUser((LivingEntity) task.getTarget().getEntity());
            target.addEffect(new EffectInstance(Effects.WEAKNESS, 200, 9));
            target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 200, 9));
            target.addEffect(new EffectInstance(Effects.BLINDNESS, 200, 9));
            if (target instanceof MobEntity) {
            }
            IStandPower.getStandPowerOptional(target).ifPresent(power -> {
                if (power.isActive() && power.getStandManifestation() instanceof StandEntity) {
                    ((StandEntity) power.getStandManifestation()).addEffect(new EffectInstance(Effects.WEAKNESS, 200, 9));
                    ((StandEntity) power.getStandManifestation()).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 200, 9));
                    ((StandEntity) power.getStandManifestation()).addEffect(new EffectInstance(Effects.BLINDNESS, 200, 9));
                }
            });
        }
    }

    @Override
    protected boolean standKeepsTarget(ActionTarget target) {
        return target.getType() == TargetType.ENTITY && target.getEntity() instanceof LivingEntity && !(target.getEntity() instanceof StandEntity);
    }

    @Override
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.ENTITY;
    }

}
