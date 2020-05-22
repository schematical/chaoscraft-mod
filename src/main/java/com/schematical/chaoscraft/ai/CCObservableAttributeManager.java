package com.schematical.chaoscraft.ai;

import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaosnet.model.ChaosNetException;
import com.schematical.chaosnet.model.ObservedAttributesElement;
import com.schematical.chaosnet.model.Organism;
import net.minecraft.block.Block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by user1a on 2/25/19.
 */
public class CCObservableAttributeManager {
    protected Organism organism;

    public List<ObservedAttributesElement> newAttributes = new ArrayList<ObservedAttributesElement>();
    public HashMap<String, List<String>> attributeMap = new HashMap<String, List<String>>();
    public CCObservableAttributeManager(Organism _organism){
        this.organism = _organism;
    }
    public boolean TestUnique(String attributeId, String attributeValue){
        List<String> valueMap = null;
        if(!attributeMap.containsKey(attributeId)){
            valueMap = new ArrayList<String>();
            attributeMap.put(attributeId, valueMap);
        }else{
            valueMap = attributeMap.get(attributeId);
        }
        if(!valueMap.contains(attributeValue)){
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
    public CCObserviableAttributeCollection Observe(BlockPos blockPos, World world){

        BlockState blockState = world.getBlockState(blockPos);
        CCObserviableAttributeCollection atts = Observe(blockState);
        atts._blockPos = blockPos;
        return atts;

    }
    public CCObserviableAttributeCollection Observe(BlockState blockState){
        CCObserviableAttributeCollection atts = Observe(blockState.getBlock());

        return atts;

    }
    public CCObserviableAttributeCollection Observe(Block block){
        CCObserviableAttributeCollection atts = new CCObserviableAttributeCollection();
        atts.resourceType = CCResourceType.BLOCK;
        atts.resourceId = block.getRegistryName().toString();
        //atts.position = block.blockP

        TestUnique(CCAttributeId.BLOCK_ID, atts.resourceId);
        return atts;

    }
    public CCObserviableAttributeCollection Observe(Item item){
        CCObserviableAttributeCollection atts = new CCObserviableAttributeCollection();
        atts.resourceType = CCResourceType.ITEM;
        atts.resourceId = item.getRegistryName().toString();
        TestUnique(CCAttributeId.ITEM_ID, atts.resourceId);
        return atts;
    }
    public CCObserviableAttributeCollection Observe(Entity entity){
        CCObserviableAttributeCollection atts = new CCObserviableAttributeCollection();
        if(entity == null){
            throw new ChaosNetException("Entity is null");
        }

        if(entity.getType() == null){
            return null;
            //throw new ChaosNetException("EntityEntry is null");
        }
        ResourceLocation resourceLocation = entity.getType().getRegistryName();
        if(resourceLocation == null){
            throw new ChaosNetException("ResourceLocation is null");
        }
        atts.resourceId = resourceLocation.toString();
        atts.resourceType = CCResourceType.ENTITY;
        atts.position = entity.getPositionVector();
        atts.team = entity.getTeam();
        atts._entity = entity;
        TestUnique(CCAttributeId.ENTITY_ID, atts.resourceId);
        return atts;
    }
    public CCObserviableAttributeCollection Observe(IRecipe recipe){
        CCObserviableAttributeCollection atts = new CCObserviableAttributeCollection();
        atts.resourceType = CCResourceType.RECIPE;
        atts.resourceId = recipe.getType().toString();
        TestUnique(CCAttributeId.RECIPE_ID, atts.resourceId);
        return atts;
    }

    public void ObserveCraftableRecipes(OrgEntity entityOrganism) {
        //TODO: Go through each craftable recipe and figure out what it can craft
        //List<IRecipe> craftableRecipes = new ArrayList<IRecipe>();
        RecipeManager recipeManager = null;
        if(entityOrganism.getServer() != null){
            recipeManager = entityOrganism.getServer().getRecipeManager();
        }else{
            recipeManager = entityOrganism.world.getRecipeManager();
        }
        for (IRecipe irecipe : recipeManager.getRecipes())
        {
            if(entityOrganism.canCraft(irecipe)){
                Observe(irecipe);
            }
        }
    }



    public void parseData(JSONObject jsonObject){
        Set<String> attributeIds = jsonObject.keySet();
        for(String attributeId : attributeIds){
            JSONArray jsonArray = (JSONArray) jsonObject.get(attributeId);
            ArrayList<String> attributeValues = new ArrayList<String>();
            for(String attributeValue : attributeValues){
                attributeValues.add(attributeValue);
            }
            attributeMap.put(attributeId, attributeValues);
        }

    }

}
