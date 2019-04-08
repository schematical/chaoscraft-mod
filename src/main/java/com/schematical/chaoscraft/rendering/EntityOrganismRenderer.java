package com.schematical.chaoscraft.rendering;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.biology.ObservableTraitsCollection;
import com.schematical.chaoscraft.entities.EntityOrganism;
import io.mikael.urlbuilder.util.Decoder;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;

/**
 * Created by user1a on 3/22/19.
 */
public class EntityOrganismRenderer extends RenderLiving<EntityOrganism> {

  private static final Decoder UTF8_URL_ENCODER = new Decoder(StandardCharsets.UTF_8);

  public EntityOrganismRenderer(RenderManager rendermanagerIn) {
    super(rendermanagerIn, new ModelPlayer(.5f, false), 0.5f);
    this.addLayer(new LayerHeldItem(this));
  }

  @Override
  protected ResourceLocation getEntityTexture(EntityOrganism entity) {

    EntityOrganism realOrg = ChaosCraft.getEntityOrganismByName(entity.getName());
    if (realOrg == null) {
      return null;
    }
    ObservableTraitsCollection observableTraitsCollection = (ObservableTraitsCollection) realOrg
        .getNNet().getBiology("ObservableTraitsCollection_0");
    if (observableTraitsCollection == null) {
      return new ResourceLocation("chaoscraft:morty.png");
    }
    String rgbString = observableTraitsCollection.getValueAsInt("color1R") + "_" +
        observableTraitsCollection.getValueAsInt("color1G") + "_" +
        observableTraitsCollection.getValueAsInt("color1B");
    BufferedImage image = null;
    ResourceLocation resourceLocation = new ResourceLocation("chaoscraft:" + rgbString + ".png");

    String chaosResourceDir = getClass().getResource("/assets/chaoscraft").getFile();
    URL skinFileURL = getClass().getResource("/assets/chaoscraft/" + rgbString + ".png");
    String skinFileName = UTF8_URL_ENCODER.decodePath(chaosResourceDir + "/" + rgbString + ".png");
    if (skinFileURL != null) {

      File file = new File(skinFileName);
      if (file.exists()) {
        return resourceLocation;
      }
    }
    try {
      URL blankResourceUrl = getClass().getResource("/assets/chaoscraft/blank.png");
      image = ImageIO.read(blankResourceUrl);
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      Color color1 = new Color(
          observableTraitsCollection.getValueAsColorInt("color1R"),
          observableTraitsCollection.getValueAsColorInt("color1G"),
          observableTraitsCollection.getValueAsColorInt("color1B"),
          255
      );
      Color color2 = new Color(
          observableTraitsCollection.getValueAsColorInt("color2R"),
          observableTraitsCollection.getValueAsColorInt("color2G"),
          observableTraitsCollection.getValueAsColorInt("color2B"),
          255
      );
      Color color3 = new Color(
          observableTraitsCollection.getValueAsColorInt("color3R"),
          observableTraitsCollection.getValueAsColorInt("color3G"),
          observableTraitsCollection.getValueAsColorInt("color3B"),
          255
      );

      for (int x = 0; x < image.getWidth(); x++) {
        for (int y = 0; y < image.getHeight(); y++) {
          int intPixelColor = image.getRGB(x, y);
          Color pixelColor = new Color(intPixelColor);
          int r = pixelColor.getRed();
          int g = pixelColor.getGreen();
          int b = pixelColor.getBlue();
          Color newPixelColor = pixelColor;
          if (pixelColor.equals(Color.WHITE)) {
            newPixelColor = pixelColor;
          } else if (pixelColor.equals(Color.RED)) {
            newPixelColor = color1;
          } else if (
              pixelColor.equals(Color.GREEN) ||
                  (
                      pixelColor.getGreen() > 200 &&
                          pixelColor.getRed() < 100 &&
                          pixelColor.getBlue() < 100
                  )
          ) {
            newPixelColor = color2;
          } else if (pixelColor.equals(Color.BLUE)) {
            newPixelColor = color3;
          } else {
            //ChaosCraft.logger.info(pixelColor.getRed() + " " + pixelColor.getBlue() + " " + pixelColor.getGreen());
          }
          image.setRGB(x, y, newPixelColor.getRGB());
        }
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    File outputfile = new File(skinFileName);
    try {
      ImageIO.write(image, "png", outputfile);
    } catch (IOException e) {
      e.printStackTrace();
    }

    entity.refreshRender = false;

    return resourceLocation;
  }


}
