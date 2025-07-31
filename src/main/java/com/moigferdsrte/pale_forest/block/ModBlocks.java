package com.moigferdsrte.pale_forest.block;

import com.moigferdsrte.pale_forest.ThePaleForest;
import com.moigferdsrte.pale_forest.block.custom.*;
import com.moigferdsrte.pale_forest.sound.ModSoundEvents;
import com.moigferdsrte.pale_forest.world.tree.ModTreeGenerator;
import com.terraformersmc.terraform.sign.api.block.TerraformHangingSignBlock;
import com.terraformersmc.terraform.sign.api.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.api.block.TerraformWallHangingSignBlock;
import com.terraformersmc.terraform.sign.api.block.TerraformWallSignBlock;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.PathUtil;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Direction;

import java.util.function.ToIntFunction;

public class ModBlocks {

    public static final Block PALE_OAK_LOG = register("pale_oak_log", new PillarBlock(
            AbstractBlock.Settings.create()
                    .mapColor(state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? MapColor.RED : MapColor.WHITE_GRAY)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.2F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()
    ));

    public static final Block STRIPPED_PALE_OAK_LOG = register("stripped_pale_oak_log", new PillarBlock(
            AbstractBlock.Settings.create()
                    .mapColor(state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? MapColor.RED : MapColor.WHITE_GRAY)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.2F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()
    ));

    public static final Block PALE_OAK_WOOD = register("pale_oak_wood",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.DARK_OAK_WOOD)));

    public static final Block STRIPPED_PALE_OAK_WOOD = register("stripped_pale_oak_wood",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_DARK_OAK_WOOD)));

    public static final Block PALE_OAK_PLANKS = register("pale_oak_planks",
            new Block(AbstractBlock.Settings.copy(Blocks.DARK_OAK_PLANKS)));

    public static final Block PALE_OAK_LEAVES = register("pale_oak_leaves",
            new PaleLeavesBlock(AbstractBlock.Settings.copy(Blocks.DARK_OAK_LEAVES)));

    public static final Block PALE_OAK_STAIRS=register("pale_oak_stairs",new StairsBlock(Blocks.DARK_OAK_STAIRS.getDefaultState(),AbstractBlock.Settings.copy(Blocks.DARK_OAK_PLANKS)));

    public static final Block PALE_OAK_SLAB=register("pale_oak_slab",new SlabBlock(AbstractBlock.Settings.copy(Blocks.DARK_OAK_PLANKS)));

    public static final Block PALE_OAK_BUTTON=register("pale_oak_button",new ButtonBlock(BlockSetType.DARK_OAK,26,AbstractBlock.Settings.copy(Blocks.DARK_OAK_PLANKS)));

    public static final Block PALE_OAK_PRESSURE_PLATE=register("pale_oak_pressure_plate",new PressurePlateBlock(BlockSetType.DARK_OAK,AbstractBlock.Settings.copy(Blocks.DARK_OAK_PLANKS)));

    public static final Block PALE_OAK_FENCE = register(
            "pale_oak_fence",
            new FenceBlock(
                    AbstractBlock.Settings.create()
                            .mapColor(PALE_OAK_PLANKS.getDefaultMapColor())
                            .solid()
                            .instrument(NoteBlockInstrument.BASS)
                            .strength(2.0F, 3.0F)
                            .sounds(BlockSoundGroup.WOOD)
                            .burnable()
            )
    );

    public static final Block PALE_OAK_FENCE_GATE = register(
            "pale_oak_fence_gate",
            new FenceGateBlock(
                    WoodType.DARK_OAK,
                    AbstractBlock.Settings.create().mapColor(PALE_OAK_PLANKS.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).burnable()
            )
    );

    public static final Block PALE_OAK_DOOR = register(
            "pale_oak_door",
            new DoorBlock(
                    BlockSetType.DARK_OAK,
                    AbstractBlock.Settings.create()
                            .mapColor(PALE_OAK_PLANKS.getDefaultMapColor())
                            .instrument(NoteBlockInstrument.BASS)
                            .strength(3.0F)
                            .nonOpaque()
                            .burnable()
                            .pistonBehavior(PistonBehavior.DESTROY)
            )
    );

    public static final Block PALE_OAK_TRAPDOOR = register(
            "pale_oak_trapdoor",
            new TrapdoorBlock(
                    BlockSetType.DARK_OAK,
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.WHITE_GRAY)
                            .instrument(NoteBlockInstrument.BASS)
                            .strength(3.0F)
                            .nonOpaque()
                            .allowsSpawning(Blocks::never)
                            .burnable()
            )
    );

    public static final Block PALE_OAK_BEEHIVE = register("pale_oak_beehive", new MoreBeehiveVariantBlock(MapColor.WHITE_GRAY, "pale_oak"));

    public static final Block PALE_OAK_CAMPFIRE = register("pale_oak_campfire", new MoreCampfireVariantBlock(MapColor.WHITE_GRAY, "pale_oak"));

    public static final Block PALE_OAK_SOUL_CAMPFIRE = register("pale_oak_soul_campfire", new MoreCampfireVariantBlock(MapColor.WHITE_GRAY, "pale_oak"));

    public static final Identifier PALE_OAK_SIGN_TEXTURE = Identifier.of(ThePaleForest.MOD_ID, "entity/signs/pale_oak");

    public static final Identifier PALE_OAK_HANGING_SIGN_TEXTURE = Identifier.of(ThePaleForest.MOD_ID, "entity/signs/hanging/pale_oak");

    public static final Identifier PALE_OAK_HANGING_SIGN_GUI = Identifier.of(ThePaleForest.MOD_ID, "textures/gui/hanging_signs/pale_oak");

    public static final Block PALE_OAK_SIGN = Registry.register(Registries.BLOCK, Identifier.of(ThePaleForest.MOD_ID, "pale_oak_sign"),
            new TerraformSignBlock(PALE_OAK_SIGN_TEXTURE, AbstractBlock.Settings.copy(Blocks.DARK_OAK_SIGN))
            );

    public static final Block PALE_OAK_WALL_SIGN = Registry.register(Registries.BLOCK, Identifier.of(ThePaleForest.MOD_ID, "pale_oak_wall_sign"),
            new TerraformWallSignBlock(PALE_OAK_SIGN_TEXTURE, AbstractBlock.Settings.copy(Blocks.DARK_OAK_WALL_SIGN))
    );

    public static final Block PALE_OAK_HANGING_SIGN = Registry.register(Registries.BLOCK, Identifier.of(ThePaleForest.MOD_ID, "pale_oak_hanging_sign"),
            new TerraformHangingSignBlock(PALE_OAK_HANGING_SIGN_TEXTURE, PALE_OAK_HANGING_SIGN_GUI, AbstractBlock.Settings.copy(Blocks.DARK_OAK_HANGING_SIGN))
    );

    public static final Block PALE_OAK_WALL_HANGING_SIGN = Registry.register(Registries.BLOCK, Identifier.of(ThePaleForest.MOD_ID, "pale_oak_wall_hanging_sign"),
            new TerraformWallHangingSignBlock(PALE_OAK_HANGING_SIGN_TEXTURE, PALE_OAK_HANGING_SIGN_GUI, AbstractBlock.Settings.copy(Blocks.DARK_OAK_WALL_HANGING_SIGN))
    );

    public static final Block PALE_OAK_SAPLING = register("pale_oak_sapling", new SaplingBlock(ModTreeGenerator.PALE_OAK_TREE,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.WHITE_GRAY)
                    .noCollision()
                    .ticksRandomly()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.CHERRY_SAPLING)
                    .pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block PALE_IVY = register(
            "pale_ivy",
            new PaleIvyBlock(
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.WHITE_GRAY)
                            .ticksRandomly()
                            .noCollision()
                            .breakInstantly()
                            .sounds(BlockSoundGroup.GRASS)
                            .pistonBehavior(PistonBehavior.DESTROY)
            )
    );
    public static final Block PALE_IVY_PLANT = register(
            "pale_ivy_plant",
            new PaleIvyPlantBlock(
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.WHITE_GRAY)
                            .noCollision()
                            .breakInstantly()
                            .sounds(BlockSoundGroup.GRASS)
                            .pistonBehavior(PistonBehavior.DESTROY)
            )
    );

    public static final Block PALE_GRASS = register(
            "pale_grass",
            new PaleGrassBlock(
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.WHITE_GRAY)
                            .replaceable()
                            .noCollision()
                            .breakInstantly()
                            .sounds(BlockSoundGroup.GRASS)
                            .burnable()
                            .pistonBehavior(PistonBehavior.DESTROY)
            )
    );

    public static final Block PALE_TWIST_FERN = register(
            "pale_twist_fern",
            new FlowerBlock(
                    StatusEffects.BLINDNESS,
                    11.0F,
                    AbstractBlock.Settings.create()
                                .mapColor(MapColor.WHITE_GRAY)
                            .noCollision()
                            .breakInstantly()
                            .sounds(BlockSoundGroup.GRASS)
                            .offset(AbstractBlock.OffsetType.XZ)
                            .pistonBehavior(PistonBehavior.DESTROY)
            )
    );

    public static final Block PALE_TALL_GRASS = register(
            "pale_tall_grass",
            new TallFlowerBlock(
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.WHITE_GRAY)
                            .noCollision()
                            .breakInstantly()
                            .sounds(BlockSoundGroup.GRASS)
                            .offset(AbstractBlock.OffsetType.XZ)
                            .burnable()
                            .pistonBehavior(PistonBehavior.DESTROY)
            )
    );

    public static final Block PALE_MOSS_CARPET = register(
            "pale_moss_carpet",
            new MossyCarpetBlock(
                    AbstractBlock.Settings.create().mapColor(MapColor.WHITE_GRAY).strength(0.1F).sounds(BlockSoundGroup.MOSS_CARPET).pistonBehavior(PistonBehavior.DESTROY)
            )
    );

    public static final Block PALE_MOSS_BLOCK = register(
            "pale_moss_block",
            new MossBlock(
                    AbstractBlock.Settings.create().mapColor(MapColor.WHITE_GRAY).strength(0.1F).sounds(BlockSoundGroup.MOSS_BLOCK).pistonBehavior(PistonBehavior.DESTROY)
            )
    );

    public static final Block CREAKING_HEART=register("creaking_heart",new CreakingHeartBlock(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(3.5f,4.0f).luminance(state -> 3).sounds(ModSoundEvents.CREAKING)));

    public static final Block POTTED_PALE_OAK_SAPLING = Registry.register(Registries.BLOCK, Identifier.of(ThePaleForest.MOD_ID, "potted_pale_oak_sapling"), Blocks.createFlowerPotBlock(PALE_OAK_SAPLING));

    public static final Block POTTED_PALE_TWIST_FERN = Registry.register(Registries.BLOCK, Identifier.of(ThePaleForest.MOD_ID, "potted_pale_twist_fern"), Blocks.createFlowerPotBlock(PALE_TWIST_FERN));

    public static final Block OPEN_EYEBLOSSOM = register(
            "open_eyeblossom",
            new EyeBlossomFlowerBlock(
                    StatusEffects.BLINDNESS,
                    0.85F,
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.WHITE_GRAY)
                            .noCollision()
                            .breakInstantly()
                            .sounds(BlockSoundGroup.GRASS)
                            .offset(AbstractBlock.OffsetType.XZ)
                            .pistonBehavior(PistonBehavior.DESTROY)
                            .luminance(createLightLevelFromLitBlockState(2))
            )
    );

    public static final Block CLOSED_EYEBLOSSOM = register(
            "closed_eyeblossom",
            new ClosedEyeBlossomFlowerBlock(
                    StatusEffects.NAUSEA,
                    0.35F,
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.WHITE_GRAY)
                            .noCollision()
                            .breakInstantly()
                            .sounds(BlockSoundGroup.GRASS)
                            .offset(AbstractBlock.OffsetType.XZ)
                            .pistonBehavior(PistonBehavior.DESTROY)
            )
    );

    public static final Block POTTED_OPEN_EYEBLOSSOM = Registry.register(Registries.BLOCK, Identifier.of(ThePaleForest.MOD_ID, "potted_open_eyeblossom"), Blocks.createFlowerPotBlock(OPEN_EYEBLOSSOM));

    public static final Block POTTED_CLOSED_EYEBLOSSOM = Registry.register(Registries.BLOCK, Identifier.of(ThePaleForest.MOD_ID, "potted_closed_eyeblossom"), Blocks.createFlowerPotBlock(CLOSED_EYEBLOSSOM));






    public static final Block RESIN_CLUMP = Registry.register(Registries.BLOCK, Identifier.of(ThePaleForest.MOD_ID, "resin_clump"),
            new GlowLichenBlock(
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.ORANGE)
                            .replaceable()
                            .noCollision()
                            .nonOpaque()
                            .strength(0.8F)
                            .sounds(BlockSoundGroup.STONE)
                            .burnable()
                            .pistonBehavior(PistonBehavior.DESTROY)
            )
    );

    public static final Block RESIN_BLOCK = register("resin_block",
            new Block(AbstractBlock.Settings.copy(Blocks.BRICKS)));

    public static final Block RESIN_BRICKS = register("resin_bricks",
            new Block(AbstractBlock.Settings.copy(Blocks.BRICKS)));

    public static final Block RESIN_BRICKS_STAIRS=register("resin_bricks_stairs",new StairsBlock(Blocks.BRICK_STAIRS.getDefaultState(),AbstractBlock.Settings.copy(Blocks.BRICKS)));

    public static final Block RESIN_BRICKS_SLAB=register("resin_bricks_slab",new SlabBlock(AbstractBlock.Settings.copy(Blocks.BRICKS)));

    public static final Block RESIN_BRICKS_WALL = register("resin_bricks_wall", new WallBlock(AbstractBlock.Settings.copyShallow(RESIN_BRICKS).solid()));

    public static final Block CHISELED_RESIN_BRICKS = register("chiseled_resin_bricks",
            new Block(AbstractBlock.Settings.copy(Blocks.BRICKS)));

    public static final Block WILDFLOWER = register("wildflower", new ModdedFlowerbedBlock(AbstractBlock.Settings.create().mapColor(MapColor.WHITE).noCollision().sounds(BlockSoundGroup.PINK_PETALS).pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block LEAF_LITTER = register("leaf_litter", new ModdedFlowerbedBlock(AbstractBlock.Settings.create().mapColor(MapColor.BROWN).noCollision().sounds(BlockSoundGroup.PINK_PETALS).pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block TEST_ACCEPT = register("test_block_accept", new Block(AbstractBlock.Settings.copy(Blocks.JIGSAW)), Rarity.EPIC);

    public static final Block TEST_FAIL = register("test_block_fail", new Block(AbstractBlock.Settings.copy(Blocks.JIGSAW)), Rarity.EPIC);

    public static final Block TEST_LOG = register("test_block_log", new Block(AbstractBlock.Settings.copy(Blocks.JIGSAW)), Rarity.EPIC);

    public static final Block TEST_START = register("test_block_start", new Block(AbstractBlock.Settings.copy(Blocks.JIGSAW)), Rarity.EPIC);

    public static final Block TEST_INSTANCE = register("test_instance_block", new Block(AbstractBlock.Settings.copy(Blocks.JIGSAW)), Rarity.EPIC);

    public static final Block FIREFLY_BUSH = register(
            "firefly_bush",
            new FireflyBushBlock(
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.BROWN)
                            .replaceable()
                            .noCollision()
                            .breakInstantly()
                            .sounds(BlockSoundGroup.SMALL_DRIPLEAF)
                            .offset(AbstractBlock.OffsetType.XYZ)
                            .burnable()
                            .pistonBehavior(PistonBehavior.DESTROY)
                            .luminance(state -> 15)
            )
    );

    public static final Block BUSH = Registry.register(
            Registries.BLOCK,
            Identifier.of(ThePaleForest.MOD_ID, "bush"),
            new ShortPlantBlock(
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.DARK_GREEN)
                            .replaceable()
                            .noCollision()
                            .breakInstantly()
                            .sounds(BlockSoundGroup.SMALL_DRIPLEAF)
                            .offset(AbstractBlock.OffsetType.XYZ)
                            .burnable()
                            .pistonBehavior(PistonBehavior.DESTROY)
            )
    );

    public static final Block SHORT_DRY_GRASS = register(
            "short_dry_grass",
            new ShortDryGrassBlock(
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.PALE_YELLOW)
                            .replaceable()
                            .noCollision()
                            .breakInstantly()
                            .sounds(BlockSoundGroup.SMALL_DRIPLEAF)
                            .offset(AbstractBlock.OffsetType.XYZ)
                            .burnable()
                            .pistonBehavior(PistonBehavior.DESTROY)
            )
    );

    public static final Block TALL_DRY_GRASS = register(
            "tall_dry_grass",
            new TallDryGrassBlock(
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.PALE_YELLOW)
                            .replaceable()
                            .noCollision()
                            .breakInstantly()
                            .sounds(BlockSoundGroup.SWEET_BERRY_BUSH)
                            .offset(AbstractBlock.OffsetType.XYZ)
                            .burnable()
                            .pistonBehavior(PistonBehavior.DESTROY)
            )
    );

    public static final Block CACTUS_FLOWER = register(
            "cactus_flower",
            new CactusFlowerBlock(
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.PINK)
                            .replaceable()
                            .noCollision()
                            .breakInstantly()
                            .sounds(BlockSoundGroup.PINK_PETALS)
                            .burnable()
                            .pistonBehavior(PistonBehavior.DESTROY)
            )
    );

    public static final Block DRIED_GHAST = register(
            "dried_ghast",
            new DriedGhastBlock(
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.WHITE_GRAY)
                            .breakInstantly()
                            .nonOpaque()
                            .sounds(BlockSoundGroup.BONE)
                            .pistonBehavior(PistonBehavior.DESTROY)
            ), Rarity.RARE
    );

    public static final Block POTTED_BUSH = Registry.register(Registries.BLOCK, Identifier.of(ThePaleForest.MOD_ID, "potted_bush"), Blocks.createFlowerPotBlock(BUSH));

    public static final Block POTTED_FIREFLY_BUSH = Registry.register(Registries.BLOCK, Identifier.of(ThePaleForest.MOD_ID, "potted_firefly_bush"), Blocks.createFlowerPotBlock(FIREFLY_BUSH));

    public static final Block POTTED_CACTUS_FLOWER = Registry.register(Registries.BLOCK, Identifier.of(ThePaleForest.MOD_ID, "potted_cactus_flower"), Blocks.createFlowerPotBlock(CACTUS_FLOWER));

    public static final Block POTTED_SHORT_DRY_GRASS = Registry.register(Registries.BLOCK, Identifier.of(ThePaleForest.MOD_ID, "potted_short_dry_grass"), Blocks.createFlowerPotBlock(SHORT_DRY_GRASS));


    //compact
    public static final Block MOSSY_PALE_OAK_LOG = register("mossy_pale_oak_log", new PillarBlock(
            AbstractBlock.Settings.create()
                    .mapColor(state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? MapColor.RED : MapColor.WHITE_GRAY)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.2F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()
    ));

    public static final Block MOSSY_PALE_OAK_WOOD = register("mossy_pale_oak_wood",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.DARK_OAK_WOOD)));

    public static final Block MOSSY_PALE_OAK_PLANKS = register("mossy_pale_oak_planks",
            new Block(AbstractBlock.Settings.copy(Blocks.DARK_OAK_PLANKS)));

    public static final Block MOSSY_PALE_OAK_STAIRS=register("mossy_pale_oak_stairs",new StairsBlock(Blocks.DARK_OAK_STAIRS.getDefaultState(),AbstractBlock.Settings.copy(Blocks.DARK_OAK_PLANKS)));

    public static final Block MOSSY_PALE_OAK_SLAB=register("mossy_pale_oak_slab",new SlabBlock(AbstractBlock.Settings.copy(Blocks.DARK_OAK_PLANKS)));

    public static final Block MOSSY_PALE_OAK_BUTTON=register("mossy_pale_oak_button",new ButtonBlock(BlockSetType.DARK_OAK,26,AbstractBlock.Settings.copy(Blocks.DARK_OAK_PLANKS)));

    public static final Block MOSSY_PALE_OAK_PRESSURE_PLATE=register("mossy_pale_oak_pressure_plate",new PressurePlateBlock(BlockSetType.DARK_OAK,AbstractBlock.Settings.copy(Blocks.DARK_OAK_PLANKS)));

    public static final Block MOSSY_PALE_OAK_FENCE = register(
            "mossy_pale_oak_fence",
            new FenceBlock(
                    AbstractBlock.Settings.create()
                            .mapColor(PALE_OAK_PLANKS.getDefaultMapColor())
                            .solid()
                            .instrument(NoteBlockInstrument.BASS)
                            .strength(2.0F, 3.0F)
                            .sounds(BlockSoundGroup.WOOD)
                            .burnable()
            )
    );

    public static final Block MOSSY_PALE_OAK_FENCE_GATE = register(
            "mossy_pale_oak_fence_gate",
            new FenceGateBlock(
                    WoodType.DARK_OAK,
                    AbstractBlock.Settings.create().mapColor(PALE_OAK_PLANKS.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).burnable()
            )
    );

    public static final Block MOSSY_PALE_OAK_DOOR = register(
            "mossy_pale_oak_door",
            new DoorBlock(
                    BlockSetType.DARK_OAK,
                    AbstractBlock.Settings.create()
                            .mapColor(PALE_OAK_PLANKS.getDefaultMapColor())
                            .instrument(NoteBlockInstrument.BASS)
                            .strength(3.0F)
                            .nonOpaque()
                            .burnable()
                            .pistonBehavior(PistonBehavior.DESTROY)
            )
    );

    public static final Block MOSSY_PALE_OAK_TRAPDOOR = register(
            "mossy_pale_oak_trapdoor",
            new TrapdoorBlock(
                    BlockSetType.DARK_OAK,
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.WHITE_GRAY)
                            .instrument(NoteBlockInstrument.BASS)
                            .strength(3.0F)
                            .nonOpaque()
                            .allowsSpawning(Blocks::never)
                            .burnable()
            )
    );

    public static final Block MUSIC_DISC_LAVA_CHICKEN = Registry.register(
            Registries.BLOCK, Identifier.of(ThePaleForest.MOD_ID, "music_disc_lava_chicken"),
            new MusicDiskBlock(AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.BAMBOO_WOOD)
                    .nonOpaque()
                    .mapColor(MapColor.BLACK)
                    .breakInstantly()
                    .noBlockBreakParticles()
                    .pistonBehavior(PistonBehavior.DESTROY))
    );

    public static final Block MUSIC_DISC_TEARS = Registry.register(
            Registries.BLOCK, Identifier.of(ThePaleForest.MOD_ID, "music_disc_tears"),
            new MusicDiskBlock(AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.BAMBOO_WOOD)
                    .nonOpaque()
                    .mapColor(MapColor.BLACK)
                    .breakInstantly()
                    .noBlockBreakParticles()
                    .pistonBehavior(PistonBehavior.DESTROY))
    );

    public static final Block ACACIA_SHELF = register("acacia_shelf", new ShelfBlock(
            AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.WOOD)
                    .strength(2.0F, 3.0F)
                    .burnable()
                    .nonOpaque()
                    .notSolid()
                    .mapColor(Blocks.ACACIA_PLANKS.getDefaultMapColor())
                    .pistonBehavior(PistonBehavior.DESTROY))
    );

    public static final Block BIRCH_SHELF = register("birch_shelf", new ShelfBlock(
            AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.WOOD)
                    .strength(2.0F, 3.0F)
                    .burnable()
                    .nonOpaque()
                    .notSolid()
                    .mapColor(Blocks.BIRCH_PLANKS.getDefaultMapColor())
                    .pistonBehavior(PistonBehavior.DESTROY))
    );

    public static final Block BAMBOO_SHELF = register("bamboo_shelf", new ShelfBlock(
            AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.BAMBOO_WOOD)
                    .strength(2.0F, 3.0F)
                    .burnable()
                    .nonOpaque()
                    .notSolid()
                    .mapColor(Blocks.BAMBOO_PLANKS.getDefaultMapColor())
                    .pistonBehavior(PistonBehavior.DESTROY))
    );

    public static final Block JUNGLE_SHELF = register("jungle_shelf", new ShelfBlock(
            AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.WOOD)
                    .strength(2.0F, 3.0F)
                    .burnable()
                    .nonOpaque()
                    .notSolid()
                    .mapColor(Blocks.JUNGLE_PLANKS.getDefaultMapColor())
                    .pistonBehavior(PistonBehavior.DESTROY))
    );

    public static final Block MANGROVE_SHELF = register("mangrove_shelf", new ShelfBlock(
            AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.WOOD)
                    .strength(2.0F, 3.0F)
                    .burnable()
                    .nonOpaque()
                    .notSolid()
                    .mapColor(Blocks.MANGROVE_PLANKS.getDefaultMapColor())
                    .pistonBehavior(PistonBehavior.DESTROY))
    );

    public static final Block CHERRY_SHELF = register("cherry_shelf", new ShelfBlock(
            AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.CHERRY_WOOD)
                    .strength(2.0F, 3.0F)
                    .burnable()
                    .nonOpaque()
                    .notSolid()
                    .mapColor(Blocks.CHERRY_PLANKS.getDefaultMapColor())
                    .pistonBehavior(PistonBehavior.DESTROY))
    );

    public static final Block DARK_OAK_SHELF = register("dark_oak_shelf", new ShelfBlock(
            AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.WOOD)
                    .strength(2.0F, 3.0F)
                    .burnable()
                    .nonOpaque()
                    .notSolid()
                    .mapColor(Blocks.DARK_OAK_PLANKS.getDefaultMapColor())
                    .pistonBehavior(PistonBehavior.DESTROY))
    );

    public static final Block OAK_SHELF = register("oak_shelf", new ShelfBlock(
            AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.WOOD)
                    .strength(2.0F, 3.0F)
                    .burnable()
                    .nonOpaque()
                    .notSolid()
                    .mapColor(Blocks.OAK_PLANKS.getDefaultMapColor())
                    .pistonBehavior(PistonBehavior.DESTROY))
    );

    public static final Block CRIMSON_SHELF = register("crimson_shelf", new ShelfBlock(
            AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.NETHER_STEM)
                    .strength(2.0F, 3.0F)
                    .nonOpaque()
                    .notSolid()
                    .mapColor(Blocks.CRIMSON_PLANKS.getDefaultMapColor())
                    .pistonBehavior(PistonBehavior.DESTROY))
    );

    public static final Block WARPED_SHELF = register("warped_shelf", new ShelfBlock(
            AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.NETHER_STEM)
                    .strength(2.0F, 3.0F)
                    .nonOpaque()
                    .notSolid()
                    .mapColor(Blocks.WARPED_PLANKS.getDefaultMapColor())
                    .pistonBehavior(PistonBehavior.DESTROY))
    );

    public static final Block SPRUCE_SHELF = register("spruce_shelf", new ShelfBlock(
            AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.WOOD)
                    .strength(2.0F, 3.0F)
                    .burnable()
                    .nonOpaque()
                    .notSolid()
                    .mapColor(Blocks.SPRUCE_PLANKS.getDefaultMapColor())
                    .pistonBehavior(PistonBehavior.DESTROY))
    );

    public static final Block PALE_OAK_SHELF = register("pale_oak_shelf", new ShelfBlock(
            AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.WOOD)
                    .strength(2.0F, 3.0F)
                    .burnable()
                    .nonOpaque()
                    .notSolid()
                    .mapColor(PALE_OAK_PLANKS.getDefaultMapColor())
                    .pistonBehavior(PistonBehavior.DESTROY))
    );

    public static final Block COPPER_GOLEM_STATUE = register("copper_golem_statue", new CopperGolemStatueBlock(
            Oxidizable.OxidationLevel.UNAFFECTED,
            AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.COPPER)
                    .strength(3.0F, 3.3F)
                    .nonOpaque()
                    .notSolid()
                    .mapColor(Blocks.COPPER_BLOCK.getDefaultMapColor())
                    .pistonBehavior(PistonBehavior.BLOCK))
    );

    public static final Block WEATHERED_COPPER_GOLEM_STATUE = register("weathered_copper_golem_statue", new CopperGolemStatueBlock(
            Oxidizable.OxidationLevel.WEATHERED,
            AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.COPPER)
                    .strength(3.0F, 3.3F)
                    .nonOpaque()
                    .notSolid()
                    .mapColor(Blocks.COPPER_BLOCK.getDefaultMapColor())
                    .pistonBehavior(PistonBehavior.BLOCK))
    );

    public static final Block EXPOSED_COPPER_GOLEM_STATUE = register("exposed_copper_golem_statue", new CopperGolemStatueBlock(
            Oxidizable.OxidationLevel.EXPOSED,
            AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.COPPER)
                    .strength(3.0F, 3.3F)
                    .nonOpaque()
                    .notSolid()
                    .mapColor(Blocks.COPPER_BLOCK.getDefaultMapColor())
                    .pistonBehavior(PistonBehavior.BLOCK))
    );

    public static final Block OXIDIZED_COPPER_GOLEM_STATUE = register("oxidized_copper_golem_statue", new CopperGolemStatueBlock(
            Oxidizable.OxidationLevel.OXIDIZED,
            AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.COPPER)
                    .strength(3.0F, 3.3F)
                    .nonOpaque()
                    .notSolid()
                    .mapColor(Blocks.COPPER_BLOCK.getDefaultMapColor())
                    .pistonBehavior(PistonBehavior.BLOCK))
    );

    public static final Block WAXED_COPPER_GOLEM_STATUE = register("waxed_copper_golem_statue", new CopperGolemStatueBlock(
            Oxidizable.OxidationLevel.UNAFFECTED,
            AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.COPPER)
                    .strength(3.0F, 3.3F)
                    .nonOpaque()
                    .notSolid()
                    .mapColor(Blocks.COPPER_BLOCK.getDefaultMapColor())
                    .pistonBehavior(PistonBehavior.BLOCK))
    );

    public static final Block WAXED_WEATHERED_COPPER_GOLEM_STATUE = register("waxed_weathered_copper_golem_statue", new CopperGolemStatueBlock(
            Oxidizable.OxidationLevel.WEATHERED,
            AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.COPPER)
                    .strength(3.0F, 3.3F)
                    .nonOpaque()
                    .notSolid()
                    .mapColor(Blocks.COPPER_BLOCK.getDefaultMapColor())
                    .pistonBehavior(PistonBehavior.BLOCK))
    );

    public static final Block WAXED_EXPOSED_COPPER_GOLEM_STATUE = register("waxed_exposed_copper_golem_statue", new CopperGolemStatueBlock(
            Oxidizable.OxidationLevel.EXPOSED,
            AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.COPPER)
                    .strength(3.0F, 3.3F)
                    .nonOpaque()
                    .notSolid()
                    .mapColor(Blocks.COPPER_BLOCK.getDefaultMapColor())
                    .pistonBehavior(PistonBehavior.BLOCK))
    );

    public static final Block WAXED_OXIDIZED_COPPER_GOLEM_STATUE = register("waxed_oxidized_copper_golem_statue", new CopperGolemStatueBlock(
            Oxidizable.OxidationLevel.OXIDIZED,
            AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.COPPER)
                    .strength(3.0F, 3.3F)
                    .nonOpaque()
                    .notSolid()
                    .mapColor(Blocks.COPPER_BLOCK.getDefaultMapColor())
                    .pistonBehavior(PistonBehavior.BLOCK))
    );

    //registry

    public static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return state -> state.get(Properties.LIT) ? litLevel : 0;
    }

    public static Block register(String id, Block block) {
        registerBlockItems(id, block);
        return Registry.register(Registries.BLOCK, Identifier.of(ThePaleForest.MOD_ID, id), block);
    }

    public static Block register(String id, Block block, Rarity rarity) {
        registerBlockItems(id, block, rarity);
        return Registry.register(Registries.BLOCK, Identifier.of(ThePaleForest.MOD_ID, id), block);
    }

    public static void registerBlockItems(String id, Block block, Rarity rarity) {
        BlockItem item = Registry.register(Registries.ITEM, Identifier.of(ThePaleForest.MOD_ID, id), new BlockItem(block , new Item.Settings().rarity(rarity)));
        if (item instanceof BlockItem) {
            item.appendBlocks(Item.BLOCK_ITEMS, item);
        }
    }

    public static void registerBlockItems(String id, Block block) {
        BlockItem item = Registry.register(Registries.ITEM, Identifier.of(ThePaleForest.MOD_ID, id), new BlockItem(block , new Item.Settings()));
        if (item instanceof BlockItem) {
            item.appendBlocks(Item.BLOCK_ITEMS, item);
        }
    }

    public static void registerModBlocks(){
        ThePaleForest.LOGGER.info("Registering Blocks");
    }
}
