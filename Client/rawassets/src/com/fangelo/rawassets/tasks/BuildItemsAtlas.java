package com.fangelo.rawassets.tasks;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class BuildItemsAtlas extends AssetBuilderTask {

	@Override
	protected void onExecute() {
		buildAtlas();
	}

	private void buildAtlas() {
		TexturePacker.Settings settings = new TexturePacker.Settings();

		settings.fast = true;
		settings.grid = true;
		settings.paddingX = 2;
		settings.paddingY = 2;
		settings.useIndexes = false;
		settings.maxWidth = settings.maxHeight = 2048;
		settings.duplicatePadding = true;
		//settings.filterMag = Texture.TextureFilter.Linear;
		//settings.filterMin = Texture.TextureFilter.Linear;

		TexturePacker.process(settings, "items", REAL_ASSETS_PATH + "items", "items");
	}
}
