package tbs.uilib;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import tbs.uilib.view.ProgressBar;
import tbs.uilib.view.View;

import java.util.Random;

public class UILib extends ApplicationAdapter {
    final Random rand = new Random();
    //Todo test clipping here
    //Todo test frustum in hudcam here
    SpriteBatch batch;
    ShapeRenderer renderer;
    Texture img;
    Rectangle scissors = new Rectangle(), clipBounds;
    OrthographicCamera camera;
    int w, h;
    long ticNano;


    @Override
    public void create() {
        batch = new SpriteBatch();
        renderer = new ShapeRenderer();
        img = new Texture("badlogic.jpg");
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(w, h);
        camera.position.set(w / 2, h / 2, 0);
        clipBounds = new Rectangle(w / 2, h / 2, w, h);

        final ProgressBar button = new ProgressBar(100, 0xffbb00, 0xbbff00, w / 2, h / 14);
        button.setPosition((w / 2) - (int) (button.w / 2), (h / 2) - (int) (button.h / 2));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view, int x, int y) {
                button.setProgress(rand.nextInt((int) button.getMax()));
                Utility.print("click > " + button.getMax());
            }
        });
        HUDManager.addView(button);
    }

    @Override
    public void dispose() {
        img.dispose();
        renderer.dispose();
        batch.dispose();
        super.dispose();
    }

    @Override
    public void render() {
        Utility.clearScreen();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        renderer.setProjectionMatrix(camera.combined);
        batch.begin();
        HUDManager.getHUDManager().draw(batch, renderer, 0, 0);

        if (batch.isDrawing())
            batch.end();

        if (renderer.isDrawing())
            renderer.end();

    }

    void drawScizzor(SpriteBatch batch) {

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        ScissorStack.calculateScissors(camera, batch.getTransformMatrix(), clipBounds, scissors);
        ScissorStack.pushScissors(scissors);

        batch.draw(img, rand.nextInt(w), rand.nextInt(h), w, h);
        batch.flush();
        ScissorStack.popScissors();
    }

}
