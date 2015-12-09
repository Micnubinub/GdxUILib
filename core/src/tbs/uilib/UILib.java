package tbs.uilib;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

import java.util.Random;

import tbs.uilib.view.Button;
import tbs.uilib.view.ListView;
import tbs.uilib.view.ScrollView;
import tbs.uilib.view.TestAdapter;
import tbs.uilib.view.View;

public class UILib extends ApplicationAdapter {
    public static final Random rand = new Random();
    public static int x, y;
    //Todo test clipping here
    //Todo test frustum in hudcam here
    SpriteBatch batch;
    ShapeRenderer renderer;
    Rectangle scissors = new Rectangle(), clipBounds;
    //    Rect container = new Rect(250, 250, 250, 250), mover = new Rect(0, 0, 75, 75);
    OrthographicCamera camera;
    int w, h, imgW, imgH;
    long ticNano;

    @Override
    public void create() {
        batch = new SpriteBatch();
        renderer = new ShapeRenderer();
//        img = new Texture("badlogic.jpg");
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        Utility.print("width > " + w + " , height > " + h);
        x = rand.nextInt(w);
        y = rand.nextInt(h);
        camera = new OrthographicCamera(w, h);
        camera.position.set(w / 2, h / 2, 0);
        imgH = h / 10;
        imgW = w / 7;
        x = 280;
        y = 280;

        final ListView linearLayout = new ListView(new TestAdapter(200));
        linearLayout.setSize(w, h);
        linearLayout.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                String text = String.valueOf(pos);
                if (v instanceof Button)
                    text = ((Button) v).getText();
                Utility.print("clickedItem > " + text);
            }
        });

        linearLayout.setBackground(new Background(0xffbb00, Background.Type.COLOR));
        HUDManager.addView(linearLayout);
    }

    public void addButton(ScrollView sv, String text) {
        final Button button5 = new Button(text);
        button5.setSize(w, w / 10);
        button5.setBackground(new Background(rand.nextInt(), Background.Type.COLOR));
        sv.addView(button5);
    }


    @Override
    public void dispose() {
//        img.dispose();
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
        HUDManager.getHUDManager().draw(batch, renderer);

//        Utility.initSpriteBatch(batch, renderer);
//        Utility.drawCenteredText(batch, Color.WHITE,
//                HUDManager.getHUDCamera().isInFrustum(x, y, imgW, imgH) ? "is in frus" : "out of frus", width / 2, height / 2, 0.3f);
//        batch.draw(img, x, y, imgW, imgH);
        drawFPS();
        if (batch.isDrawing())
            batch.end();

        if (!renderer.isDrawing())
            renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.WHITE);
        renderer.rect(0, (h / 2) - 2, w, 4);
//        mover.x = x;
//        mover.y = y;
//
//        renderer.setColor(Color.NAVY);
//        renderer.rect(container.x, container.y, container.w, container.h);
//
//        renderer.setColor(container.contains(mover) ? Color.GREEN : Color.RED);
//        renderer.rect(mover.x, mover.y, mover.w, mover.h);

//        renderer.circle(UniversalClickListener.getInitialTouchDownX(), UniversalClickListener.getInitialTouchDownY(), 29);
        if (renderer.isDrawing())
            renderer.end();

    }

    public void drawFPS() {
        if (renderer.isDrawing())
            renderer.end();
        if (!batch.isDrawing())
            batch.begin();

        Utility.drawCenteredText(batch, Color.BLACK, "fps: " + Gdx.graphics.getFramesPerSecond(), w / 9, w / 9, 0.4f);

    }

    void drawScizzor(SpriteBatch batch) {

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        ScissorStack.calculateScissors(camera, batch.getTransformMatrix(), clipBounds, scissors);
        ScissorStack.pushScissors(scissors);

//        batch.draw(img, rand.nextInt(width), rand.nextInt(height), width, height);
        batch.flush();
        ScissorStack.popScissors();
    }

}
