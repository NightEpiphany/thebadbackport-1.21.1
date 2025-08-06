package com.moigferdsrte.pale_forest;

import com.google.common.reflect.Reflection;
import com.moigferdsrte.pale_forest.block.ModBlocks;
import com.moigferdsrte.pale_forest.effect.PaleErosionStatusEffect;
import com.moigferdsrte.pale_forest.entity.ModBlockEntities;
import com.moigferdsrte.pale_forest.entity.ModBoats;
import com.moigferdsrte.pale_forest.entity.ModEntities;
import com.moigferdsrte.pale_forest.entity.custom.CreakingEntity;
import com.moigferdsrte.pale_forest.entity.custom.HappyGhastEntity;
import com.moigferdsrte.pale_forest.group.ModItemGroup;
import com.moigferdsrte.pale_forest.item.ModArmorTrimMaterials;
import com.moigferdsrte.pale_forest.item.ModCompostableItems;
import com.moigferdsrte.pale_forest.item.ModItems;
import com.moigferdsrte.pale_forest.item.ModPaintings;
import com.moigferdsrte.pale_forest.particles.FireflyParticle;
import com.moigferdsrte.pale_forest.particles.FireflyParticleFactory;
import com.moigferdsrte.pale_forest.poi.PointOfInterests;
import com.moigferdsrte.pale_forest.sound.ModSoundEvents;
import com.moigferdsrte.pale_forest.tags.ModTags;
import com.moigferdsrte.pale_forest.world.ModFeatures;
import com.moigferdsrte.pale_forest.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThePaleForest implements ModInitializer {

	public static final String MOD_ID = "pale_forest";

	public static final ParticleType<FireflyParticle> FIREFLY_PARTICLE = FabricParticleTypes.complex(
			false,
			FireflyParticle::createCodec,
			FireflyParticle::createPacketCodec
	);

	public static final Style ARISU_STYLE = Style.EMPTY
			.withColor(TextColor.parse("#33ddff").getOrThrow());

	public static final GameRules.Key<GameRules.BooleanRule> TNT_EXPLOSION =
			GameRuleRegistry.register("tntExplodes", GameRules.Category.UPDATES, GameRuleFactory.createBooleanRule(true));

    public static final SimpleParticleType GREEN_FLAME = FabricParticleTypes.simple();

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final  RegistryEntry.Reference<StatusEffect> PALE_EROSION = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "pale_erosion"), new PaleErosionStatusEffect());

	@Override
	public void onInitialize() {
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "copper_fire_flame"), GREEN_FLAME);
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModItemGroup.registerItemGroup();
		ModBoats.registerModBoats();
		ModSoundEvents.registerModSoundEvents();
		ModWorldGeneration.registerWorldGenerations();
		ModCompostableItems.registerDefaultCompostableItems();
		PointOfInterests.init();
		ModPaintings.init();
		ModBlockEntities.init();
		ModTags.register();
		ModFeatures.init();
		FireflyParticleFactory.init();
		Reflection.initialize(ModArmorTrimMaterials.class);

//		TaskManager.INSTANCE.startClearItemsTask(DEFAULT_CLEAR_TICKS, DEFAULT_TIPS_TICKS);
//		CommandRegistrationCallback.EVENT.register((commandDispatcher, commandRegistryAccess, registrationEnvironment) -> {
//			commandDispatcher.register(
//					CommandManager.literal("clearItems")
//							.requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
//							.then(CommandManager.argument("clearTicks", IntegerArgumentType.integer(1))
//									.executes(ClearItemsCommand::executeWithOneArg)
//									.then(CommandManager.argument("tipsTicks", IntegerArgumentType.integer(1))
//											.executes(ClearItemsCommand::executeWithTwoArg))
//							)
//
//			);
//
//		});


        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(ThePaleForest.MOD_ID, "firefly"), FIREFLY_PARTICLE);

		StrippableBlockRegistry.register(ModBlocks.PALE_OAK_LOG, ModBlocks.STRIPPED_PALE_OAK_LOG);

		StrippableBlockRegistry.register(ModBlocks.PALE_OAK_WOOD, ModBlocks.STRIPPED_PALE_OAK_WOOD);

		FabricDefaultAttributeRegistry.register(ModEntities.CREAKING, CreakingEntity.createCreakingAttributes());

		FabricDefaultAttributeRegistry.register(ModEntities.HAPPY_GHAST, HappyGhastEntity.createGhastAttributes());

		LOGGER.info("Initialized Pale Forest");
	}
}