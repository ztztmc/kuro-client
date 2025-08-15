package website.kuro.ui.modmenu;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;
import website.kuro.KuroClient;
import website.kuro.mod.Mod;
import website.kuro.ui.mainmenu.KuroImageButton;
import website.kuro.ui.mainmenu.KuroMainMenu;
import website.kuro.ui.modmenu.components.KuroCategoryButton;
import website.kuro.ui.modmenu.components.KuroModButton;
import website.kuro.ui.modmenu.components.TextBox;
import website.kuro.util.MouseUtils;
import website.kuro.util.SearchUtils;
import website.kuro.util.render.GuiImage;
import website.kuro.util.render.RoundedUtils;

import java.awt.*;
import java.util.ArrayList;

public class KuroModMenu extends GuiScreen {

    public static int panoramaTimer;
    private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[] {new ResourceLocation("kuro/images/panorama/panorama_0.png"),
            new ResourceLocation("kuro/images/panorama/panorama_1.png"),
            new ResourceLocation("kuro/images/panorama/panorama_2.png"),
            new ResourceLocation("kuro/images/panorama/panorama_3.png"),
            new ResourceLocation("kuro/images/panorama/panorama_4.png"),
            new ResourceLocation("kuro/images/panorama/panorama_5.png")};
    private ResourceLocation backgroundTexture;
    private DynamicTexture viewportTexture;

    boolean isInGame;

    public ArrayList<KuroCategoryButton> categoryButtons = new ArrayList<>();
    public ArrayList<KuroModButton> modButtons = new ArrayList<>();

    public TextBox searchField;
    String search;

    public KuroModMenu(boolean isInGame, int panoramaTimer) {
        if(panoramaTimer != -1)
            this.panoramaTimer = panoramaTimer;
        this.isInGame = isInGame;
    }

    @Override
    public void onGuiClosed() {
        KuroMainMenu.panoramaTimer = panoramaTimer;
        KuroSettingsMenu.panoramaTimer = panoramaTimer;
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void initGui() {
        super.initGui();
        modButtons.clear();
        categoryButtons.clear();

        this.viewportTexture = new DynamicTexture(256, 256);
        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);

        categoryButtons.add(new KuroCategoryButton(width / 2 - 140, height / 2 - 110, 41, 16, "mods", "Mods"));
        categoryButtons.add(new KuroCategoryButton(width / 2 - 96, height / 2 - 110, 55, 16, "settings2", "Settings", new KuroSettingsMenu(isInGame, panoramaTimer)));

        (searchField = new TextBox(100, "Search mods", this.fontRendererObj, 1, 1, 98, 15)).setFocused(true);
        Keyboard.enableRepeatEvents(true);
        if(searchField.getText() != search) search = searchField.getText();

        int addX = 0;
        int addY = 0;
        int rowAddedMods = 0;
        for(Mod m : KuroClient.modManager.mods) {
            if(search == "" || SearchUtils.isSimilar(m.getName(), search.toLowerCase().trim())) {
                modButtons.add(new KuroModButton(width / 2 - 203 + addX, height / 2 - 88 + addY, m));
                addX += 103;
                rowAddedMods++;
                if (rowAddedMods == 4) {
                    addX = 0;
                    addY += 70;
                    rowAddedMods = 0;
                }
            }
        }
    }

    @Override
    public void drawScreen(int p_73863_0_, int mouseX, float mouseY) {
        super.drawScreen(p_73863_0_, mouseX, mouseY);
        if(!isInGame) {
            GlStateManager.disableAlpha();
            this.renderSkybox(p_73863_0_, mouseX, mouseY);
            GlStateManager.enableAlpha();
        }

        RoundedUtils.drawRound(width / 2 - 209, height / 2 - 115, 419, 230, 4, new Color(5,5,5));

        GlStateManager.enableBlend();
        GuiImage.drawImage(width / 2 - 204, height / 2 - 110, 59, 15, "kuro/images/kuro-logo.png");

        for(KuroCategoryButton c : categoryButtons) {
            c.drawButton(p_73863_0_, mouseX);
        }

        //edit hud button
        if(MouseUtils.isInside(p_73863_0_, mouseX, width / 2 + 88, height / 2 - 108, 12, 12)) {
            RoundedUtils.drawRound(width / 2 + 86, height / 2 - 110, 16, 16, 4, new Color(19, 19, 19));
        }
        GlStateManager.enableBlend();
        GuiImage.drawImage(width / 2 + 88, height / 2 - 108, 12, 12, "kuro/icons/edit.png");

        searchField.drawTextBox(width / 2 + 117, height / 2 - 109);

        for(KuroModButton m : modButtons) {
            m.drawButton(p_73863_0_, mouseX);
        }

    }

    @Override
    protected void mouseClicked(int p_73864_0_, int mouseX, int mouseY) {
        super.mouseClicked(p_73864_0_, mouseX, mouseY);
        if(MouseUtils.isInside(p_73864_0_, mouseX, width / 2 + 88, height / 2 - 108, 12, 12)) {
            mc.displayGuiScreen(new HUDPositioningScreen());
        }
        for(KuroCategoryButton c : categoryButtons) {
            c.mouseClicked(p_73864_0_, mouseX);
        }
        searchField.mouseClicked(p_73864_0_, mouseX, mouseY);
        for(KuroModButton m : modButtons) {
            m.onClick(p_73864_0_, mouseX);
        }
    }

    @Override
    protected void keyTyped(char p_73869_0_, int typedChar) {
        super.keyTyped(p_73869_0_, typedChar);

        this.searchField.textboxKeyTyped(p_73869_0_, typedChar);

        this.modButtons.clear();
        search = searchField.getText();
        int addX = 0;
        int addY = 0;
        int rowAddedMods = 0;
        for(Mod m : KuroClient.modManager.mods) {
            if(search == "" || SearchUtils.isSimilar(m.getName(), search.toLowerCase().trim())) {
                modButtons.add(new KuroModButton(width / 2 - 204 + addX, height / 2 - 88 + addY, m));
                addX += 103;
                rowAddedMods++;
                if (rowAddedMods == 4) {
                    addX = 0;
                    addY += 70;
                    rowAddedMods = 0;
                }
            }
        }
    }

    //panorama from GuiMainMenu
    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.panoramaTimer;
    }

    private void drawPanorama(int p_73970_1_, int p_73970_2_, float p_73970_3_)
    {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        int i = 8;
        int j = 64;

        for (int k = 0; k < j; ++k)
        {
            GlStateManager.pushMatrix();
            float f = ((float)(k % i) / (float)i - 0.5F) / 64.0F;
            float f1 = ((float)(k / i) / (float)i - 0.5F) / 64.0F;
            float f2 = 0.0F;
            GlStateManager.translate(f, f1, f2);
            GlStateManager.rotate(MathHelper.sin(((float)this.panoramaTimer + p_73970_3_) / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-((float)this.panoramaTimer + p_73970_3_) * 0.1F, 0.0F, 1.0F, 0.0F);

            for (int l = 0; l < 6; ++l)
            {
                GlStateManager.pushMatrix();

                if (l == 1)
                {
                    GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                }

                if (l == 2)
                {
                    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                }

                if (l == 3)
                {
                    GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
                }

                if (l == 4)
                {
                    GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                }

                if (l == 5)
                {
                    GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
                }

                ResourceLocation[] aresourcelocation = titlePanoramaPaths;

                this.mc.getTextureManager().bindTexture(aresourcelocation[l]);
                worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                int i1 = 255 / (k + 1);
                float f3 = 0.0F;
                worldrenderer.pos(-1.0D, -1.0D, 1.0D).tex(0.0D, 0.0D).color(255, 255, 255, i1).endVertex();
                worldrenderer.pos(1.0D, -1.0D, 1.0D).tex(1.0D, 0.0D).color(255, 255, 255, i1).endVertex();
                worldrenderer.pos(1.0D, 1.0D, 1.0D).tex(1.0D, 1.0D).color(255, 255, 255, i1).endVertex();
                worldrenderer.pos(-1.0D, 1.0D, 1.0D).tex(0.0D, 1.0D).color(255, 255, 255, i1).endVertex();
                tessellator.draw();
                GlStateManager.popMatrix();
            }

            GlStateManager.popMatrix();
            GlStateManager.colorMask(true, true, true, false);
        }

        worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
    }

    private void rotateAndBlurSkybox(float p_73968_1_)
    {
        this.mc.getTextureManager().bindTexture(this.backgroundTexture);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, 256, 256);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.colorMask(true, true, true, false);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        GlStateManager.disableAlpha();
        int i = 3;
        int j = 3;

        for (int k = 0; k < j; ++k)
        {
            float f = 1.0F / (float)(k + 1);
            int l = this.width;
            int i1 = this.height;
            float f1 = (float)(k - i / 2) / 256.0F;
            worldrenderer.pos((double)l, (double)i1, (double)this.zLevel).tex((double)(0.0F + f1), 1.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
            worldrenderer.pos((double)l, 0.0D, (double)this.zLevel).tex((double)(1.0F + f1), 1.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
            worldrenderer.pos(0.0D, 0.0D, (double)this.zLevel).tex((double)(1.0F + f1), 0.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
            worldrenderer.pos(0.0D, (double)i1, (double)this.zLevel).tex((double)(0.0F + f1), 0.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
        }

        tessellator.draw();
        GlStateManager.enableAlpha();
        GlStateManager.colorMask(true, true, true, true);
    }

    private void renderSkybox(int p_73971_1_, int p_73971_2_, float p_73971_3_)
    {
        this.mc.getFramebuffer().unbindFramebuffer();
        GlStateManager.viewport(0, 0, 256, 256);
        this.drawPanorama(p_73971_1_, p_73971_2_, p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        int i = 3;

        for (int j = 0; j < i; ++j)
        {
            this.rotateAndBlurSkybox(p_73971_3_);
            this.rotateAndBlurSkybox(p_73971_3_);
        }

        this.mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        float f2 = this.width > this.height ? 120.0F / (float)this.width : 120.0F / (float)this.height;
        float f = (float)this.height * f2 / 256.0F;
        float f1 = (float)this.width * f2 / 256.0F;
        int k = this.width;
        int l = this.height;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos(0.0D, (double)l, (double)this.zLevel).tex((double)(0.5F - f), (double)(0.5F + f1)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        worldrenderer.pos((double)k, (double)l, (double)this.zLevel).tex((double)(0.5F - f), (double)(0.5F - f1)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        worldrenderer.pos((double)k, 0.0D, (double)this.zLevel).tex((double)(0.5F + f), (double)(0.5F - f1)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        worldrenderer.pos(0.0D, 0.0D, (double)this.zLevel).tex((double)(0.5F + f), (double)(0.5F + f1)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        tessellator.draw();
    }

}