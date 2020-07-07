package com.schematical.chaoscraft.blocks;

import com.schematical.chaoscraft.ChaosCraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;

public class ChaosBlocks {


    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, ChaosCraft.MODID);

    public static final RegistryObject<Block> SPAWN_BLOCK = BLOCKS.register("spawn_block", () -> new SpawnBlock(Block.Properties.create(Material.BARRIER).tickRandomly().hardnessAndResistance(-1.0F, 3600000.0F)));
    public static final RegistryObject<Block> WAYPOINT_BLOCK = BLOCKS.register("waypoint", () -> new WaypointBlock(Block.Properties.create(Material.BARRIER).tickRandomly().hardnessAndResistance(-1.0F, 3600000.0F)));
    public static final RegistryObject<Block> EGG_BLOCK = BLOCKS.register("egg", () -> new ChaosEggBlock(Block.Properties.create(Material.BARRIER).tickRandomly().hardnessAndResistance(-1.0F, 3600000.0F)));
    public static final RegistryObject<Block> FACTORY_BLOCK = BLOCKS.register("factory_block", () -> new FactoryBlock(Block.Properties.create(Material.BARRIER).tickRandomly().hardnessAndResistance(-1.0F, 3600000.0F)));
    public static final RegistryObject<Block> MATCH_MANAGER_BLOCK = BLOCKS.register("match_manager_block", () -> new MatchManagerBlock(Block.Properties.create(Material.BARRIER).tickRandomly().hardnessAndResistance(-1.0F, 3600000.0F)));

    public static ArrayList<BlockPos> spawnBlocks = new ArrayList<BlockPos>();
    public static ArrayList<BlockPos> waypointsBlocks = new ArrayList<BlockPos>();
    public static HashMap<String, BlockPos> matchManagerBlocks = new HashMap<>();
}
