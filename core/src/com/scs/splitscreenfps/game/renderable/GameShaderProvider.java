package com.scs.splitscreenfps.game.renderable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;

public class GameShaderProvider extends DefaultShaderProvider {

	private DefaultShader.Config fog_texture;
	private DefaultShader.Config fog_diffuse;

	public GameShaderProvider() {
		fog_texture = new DefaultShader.Config(
				Gdx.files.internal("shaders/default_vertex.glsl").readString(),
				Gdx.files.internal("shaders/fog_texture.glsl").readString()
				);

		fog_diffuse = new DefaultShader.Config(
				Gdx.files.internal("shaders/default_vertex.glsl").readString(),
				Gdx.files.internal("shaders/fog_diffuse.glsl").readString()
				);
	}


	@Override
	public Shader createShader(Renderable renderable) {
		/*if(renderable.userData instanceof RenderData) {
			RenderData data = (RenderData) renderable.userData;
			return new CustomShader(renderable, getConfig(data.shaderType));
		} else {*/
			return super.createShader(renderable);
		//}
	}
/*
	private DefaultShader.Config getConfig(RenderData.ShaderType type){
		switch(type){
		case FOG_TEXTURE:
			return fog_texture;
		case FOG_COLOR:
			return fog_diffuse;
		default:
			return fog_diffuse;
		}
	}

*/
	private class CustomShader extends DefaultShader {

		public final int u_tilemapOffset = register("u_tilemapOffset");
		public final int u_tilemapSize = register("u_tilemapSize");
		public final int u_textureRepeat = register("u_textureRepeat");

		public CustomShader(Renderable renderable, Config config) {
			super(renderable, config);
		}


		@Override
		public void render(Renderable renderable, Attributes combinedAttributes) {
			/*RenderData renderData = (RenderData)renderable.userData;
			if (renderData != null) {
				set(u_tilemapOffset, renderData.tilemapOffset);
				set(u_tilemapSize, renderData.tilemapSize);
				set(u_textureRepeat, renderData.textureRepeat);
				super.render(renderable, combinedAttributes);
			}*/
		}
	}


}
