package com.schematical.chaoscraft.items;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.blocks.ChaosBlocks;
import com.schematical.chaoscraft.blocks.SpawnBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ChaosItems {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<Item>(ForgeRegistries.ITEMS, ChaosCraft.MODID);
    public static final RegistryObject<Item> SPAWN_BLOCK_ITEM = ITEMS.register("spawn_block_item", () ->
            new BlockItem(ChaosBlocks.SPAWN_BLOCK.get(),  new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))
    );
    public static final RegistryObject<Item> FACTORY_BLOCK_ITEM = ITEMS.register("factory_block_item", () ->
            new BlockItem(ChaosBlocks.FACTORY_BLOCK.get(),  new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))
    );
    public static final RegistryObject<Item> WAYPOINT_BLOCK_ITEM = ITEMS.register("waypoint_block_item", () ->
            new BlockItem(ChaosBlocks.WAYPOINT_BLOCK.get(),  new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))
    );

}
