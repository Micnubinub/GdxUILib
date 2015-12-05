package tbs.uilib;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

public class UILib extends ApplicationAdapter {
    //Todo test clipping here
    //Todo test frustum in hudcam here
    SpriteBatch batch;
    Texture img;
    Rectangle scissors = new Rectangle(), clipBounds;
    OrthographicCamera camera;
    int w, h;


    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(w, h);

        clipBounds = new Rectangle(w / 2, h / 2, w, h);


    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.begin();
        ScissorStack.calculateScissors(camera, batch.getTransformMatrix(), clipBounds, scissors);
        ScissorStack.pushScissors(scissors);


        batch.draw(img, 0, 0);
        batch.flush();
        ScissorStack.popScissors();

        batch.end();
    }
}
