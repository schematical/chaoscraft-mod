package com.schematical.chaoscraft.ai;

import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaosnet.model.ChaosNetException;
import com.schematical.chaosnet.model.ObservedAttributesElement;
import com.schematical.chaosnet.model.Organism;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 2/25/19.
 */
public class CCObservableAttributeManager {

  public List<ObservedAttributesElement> newAttributes = new ArrayList<ObservedAttributesElement>();
  public HashMap<String, List<String>> attributeMap = new HashMap<String, List<String>>();
  protected Organism organism;

  public CCObservableAttributeManager(Organism _organism) {
    this.organism = _organism;
  }

  public boolean TestUnique(String attributeId, String attributeValue) {
    List<String> valueMap = null;
    if (!attributeMap.containsKey(attributeId)) {
      valueMap = new ArrayList<String>();
      attributeMap.put(attributeId, valueMap);
    } else {
      valueMap = attributeMap.get(attributeId);
    }
    if (!valueMap.contains(attributeValue)) {
      valueMap.add(attributeValue);
      ObservedAttributesElement newAttribute = new ObservedAttributesElement();
      newAttribute.setAttributeId(attributeId);
      newAttribute.setAttributeValue(attributeValue);
      newAttribute.setSpecies(organism.getSpeciesNamespace());
      newAttributes.add(newAttribute);
      return true;
    }
    return false;
  }

  public CCObserviableAttributeCollection Observe(IBlockState blockState) {
    CCObserviableAttributeCollection atts = Observe(blockState.getBlock());

    return atts;

  }

  public CCObserviableAttributeCollection Observe(Block block) {
    CCObserviableAttributeCollection atts = new CCObserviableAttributeCollection();
    atts.resourceType = CCResourceType.BLOCK;
    atts.resourceId = block.getRegistryName().toString();
    //atts.position = block.blockP

    TestUnique(CCAttributeId.BLOCK_ID, atts.resourceId);
    return atts;

  }

  public CCObserviableAttributeCollection Observe(Item item) {
    CCObserviableAttributeCollection atts = new CCObserviableAttributeCollection();
    atts.resourceType = CCResourceType.ITEM;
    atts.resourceId = item.getRegistryName().toString();
    TestUnique(CCAttributeId.ITEM_ID, atts.resourceId);
    return atts;
  }

  public CCObserviableAttributeCollection Observe(Entity entity) {
    CCObserviableAttributeCollection atts = new CCObserviableAttributeCollection();
    if (entity == null) {
      throw new ChaosNetException("Entity is null");
    }
    EntityEntry entityEntry = EntityRegistry.getEntry(entity.getClass());
    if (entityEntry == null) {
      return null;
      //throw new ChaosNetException("EntityEntry is null");
    }
    ResourceLocation resourceLocation = entityEntry.getRegistryName();
    if (resourceLocation == null) {
      throw new ChaosNetException("ResourceLocation is null");
    }
    atts.resourceId = resourceLocation.toString();
    atts.resourceType = CCResourceType.ENTITY;
    atts.position = entity.getPositionVector();
    atts._entity = entity;
    TestUnique(CCAttributeId.ENTITY_ID, atts.resourceId);
    return atts;
  }

  public CCObserviableAttributeCollection Observe(IRecipe recipe) {
    CCObserviableAttributeCollection atts = new CCObserviableAttributeCollection();
    atts.resourceType = CCResourceType.RECIPE;
    atts.resourceId = recipe.getRegistryName().toString();
    TestUnique(CCAttributeId.RECIPE_ID, atts.resourceId);
    return atts;
  }

  public void ObserveCraftableRecipes(EntityOrganism entityOrganism) {
    //TODO: Go through each craftable recipe and figure out what it can craft
    //List<IRecipe> craftableRecipes = new ArrayList<IRecipe>();
    for (IRecipe irecipe : CraftingManager.REGISTRY) {
      if (entityOrganism.canCraft(irecipe)) {
        Observe(irecipe);
      }
    }
  }


  public void parseData(JSONObject jsonObject) {
    Set<String> attributeIds = jsonObject.keySet();
    for (String attributeId : attributeIds) {
      JSONArray jsonArray = (JSONArray) jsonObject.get(attributeId);
      ArrayList<String> attributeValues = new ArrayList<String>();
      for (String attributeValue : attributeValues) {
        attributeValues.add(attributeValue);
      }
      attributeMap.put(attributeId, attributeValues);
    }

  }

}
