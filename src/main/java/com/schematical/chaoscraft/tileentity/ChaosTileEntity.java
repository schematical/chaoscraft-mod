package com.schematical.chaoscraft.tileentity;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.blocks.ChaosBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ChaosTileEntity {
    public static final DeferredRegister<TileEntityType<?>> TILES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, ChaosCraft.MODID);

    public static final RegistryObject<TileEntityType<SpawnBlockTileEntity>> SPAWN_TILE = TILES.register("spawn_block_tile_entity", () -> TileEntityType.Builder.create(SpawnBlockTileEntity::new, ChaosBlocks.SPAWN_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<WaypointBlockTileEntity>> WAYPOINT_TILE = TILES.register("waypoint_block_tile_entity", () -> TileEntityType.Builder.create(WaypointBlockTileEntity::new, ChaosBlocks.WAYPOINT_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<FactoryTileEntity>> FACTORY_TILE = TILES.register("factory_tile_entity", () -> TileEntityType.Builder.create(FactoryTileEntity::new, ChaosBlocks.FACTORY_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<BuildAreaMarkerTileEntity>> BUILD_AREA_TILE = TILES.register("build_area_marker_tile_entity", () -> TileEntityType.Builder.create(BuildAreaMarkerTileEntity::new, ChaosBlocks.BUILD_AREA_MARKER.get()).build(null));
}

