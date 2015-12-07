package tbs.uilib;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

import java.util.Random;

import tbs.uilib.view.Button;
import tbs.uilib.view.TextView;
import tbs.uilib.view.View;

public class UILib extends ApplicationAdapter {
    final Random rand = new Random();
    //Todo test clipping here
    //Todo test frustum in hudcam here
    SpriteBatch batch;
    ShapeRenderer renderer;
    Texture img;
    Rectangle scissors = new Rectangle(), clipBounds;
    OrthographicCamera camera;
    int w, h, imgW, imgH;
    long ticNano;
    public static int x, y;


    @Override
    public void create() {
        batch = new SpriteBatch();
        renderer = new ShapeRenderer();
        img = new Texture("badlogic.jpg");
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        x = rand.nextInt(w);
        y = rand.nextInt(h);
        camera = new OrthographicCamera(w, h);
        camera.position.set(w / 2, h / 2, 0);
        imgH = h / 10;
        imgW = w / 7;

        final TextView textView = new TextView("Mike Check 1,2,1,2 please work...Pls, i prayinh here", w / 9);
        textView.setPosition(w / 5, h / 5);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view, int x, int y) {
                Utility.print("w > " + textView.getW());
                textView.setW(w / (1 + rand.nextInt(10)));
            }
        });

        final Button button = new Button("Button Mike Check 1,2,1,2 please work...Pls, i prayinh here");
        button.setPosition(w / 5, h / 2);
        button.setSize(w / 9, w / 9);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view, int x, int y) {
                Utility.print("w > " + button.w);
                button.setW(w / (1 + rand.nextInt(10)));
            }
        });

        HUDManager.addView(button);
        HUDManager.addView(textView);
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

        Utility.initBatch(batch, renderer);
//        Utility.drawCenteredText(batch, Color.WHITE,
//                HUDManager.getHUDCamera().isInFrustum(x, y, imgW, imgH) ? "is in frus" : "out of frus", w / 2, h / 2, 0.3f);
        batch.draw(img, x, y, imgW, imgH);
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
