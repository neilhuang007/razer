package me.neilhuang007.razer.script.api.wrapper.impl;

import me.neilhuang007.razer.script.api.RenderAPI;
import me.neilhuang007.razer.util.font.impl.rise.FontRenderer;

public class ScriptRiseFontRenderer extends ScriptFontRenderer<FontRenderer>
{
	public ScriptRiseFontRenderer(FontRenderer wrapped)
	{
		super(wrapped);
	}

	@Override
	public double width(String string)
	{
		return this.wrapped.width(string);
	}

	@Override
	public double height()
	{
		return this.wrapped.height();
	}

	@Override
	public void draw(String string, double x, double y, int[] color)
	{
		this.wrapped.drawString(string, x, y, RenderAPI.intArrayToColor(color).getRGB());
	}

	@Override
	public void drawCentered(String string, double x, double y, int[] color)
	{
		this.wrapped.drawCenteredString(string, x, y, RenderAPI.intArrayToColor(color).getRGB());
	}

	@Override
	public void drawWithShadow(String string, double x, double y, int[] color)
	{
		this.wrapped.drawStringWithShadow(string, x, y, RenderAPI.intArrayToColor(color).getRGB());
	}

	@Override
	public void drawCenteredWithShadow(String string, float x, float y, int[] color)
	{
		this.wrapped.drawCenteredStringWithShadow(string, x, y, RenderAPI.intArrayToColor(color).getRGB());
	}

}
