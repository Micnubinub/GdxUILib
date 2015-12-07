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
import tbs.uilib.view.ScrollView;
import tbs.uilib.view.TextView;
import tbs.uilib.view.View;

public class UILib extends ApplicationAdapter {
    public static int x, y;
    final Random rand = new Random();
    //Todo test clipping here
    //Todo test frustum in hudcam here
    SpriteBatch batch;
    ShapeRenderer renderer;
    Rectangle scissors = new Rectangle(), clipBounds;
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
        x = rand.nextInt(w);
        y = rand.nextInt(h);
        camera = new OrthographicCamera(w, h);
        camera.position.set(w / 2, h / 2, 0);
        imgH = h / 10;
        imgW = w / 7;

        final TextView textView = new TextView("Mike Check 1,2,1,2 please work...Pls, i prayinh here", w / 4);
        textView.setBackground(new Background(0x4444ff, Background.Type.COLOR));
        textView.setSize(w / 5, h / 5);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view, int x, int y) {
                textView.setWidth(w / (1 + rand.nextInt(10)));
            }
        });

        final Button button = new Button("Button Mike Check 1,2,1,2 please work...Pls, i prayinh here");
        button.setSize(w / 5, w / 9);
        final Button button1 = new Button("Button1");
        button1.setSize(w / 5, w / 9);

        final Button button5 = new Button("Mike5");
        button5.setSize(w / 5, w / 9);

        final Button button4 = new Button("Button4");
        button4.setSize(w / 5, w / 9);

        final Button button3 = new Button("Button3");
        button3.setSize(w / 5, w / 9);

        final Button button2 = new Button("Button2");
        button2.setSize(w / 5, w / 9);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view, int x, int y) {
                button.setWidth(w / (1 + rand.nextInt(5)));
            }
        });

        final ScrollView linearLayout = new ScrollView(true);
        linearLayout.setPosition(w / 6, h / 6);
        linearLayout.setSize((4 * w) / 6, (h) / 3);

        linearLayout.setBackground(new Background(0xffbb00, Background.Type.COLOR));
        button.setBackground(new Background(0xf65b00, Background.Type.COLOR));
        button1.setBackground(new Background(0xcc5500, Background.Type.COLOR));
        button2.setBackground(new Background(0x99bbaa, Background.Type.COLOR));
        button3.setBackground(new Background(0x99bb00, Background.Type.COLOR));
        button4.setBackground(new Background(0x44b560, Background.Type.COLOR));
        button5.setBackground(new Background(0x220045, Background.Type.COLOR));
        linearLayout.addView(button);
        linearLayout.addView(button1);
        linearLayout.addView(button2);
        linearLayout.addView(button3);
        linearLayout.addView(button4);
        linearLayout.addView(button5);
        linearLayout.addView(textView);


        HUDManager.addView(linearLayout);
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
//                HUDManager.getHUDCamera().isInFrustum(x, y, imgW, imgH) ? "is in frus" : "out of frus", w / 2, h / 2, 0.3f);
//        batch.draw(img, x, y, imgW, imgH);

        if (batch.isDrawing())
            batch.end();

        if (!renderer.isDrawing())
            renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.WHITE);
//        renderer.circle(UniversalClickListener.startX, UniversalClickListener.startY, 29);
        if (renderer.isDrawing())
            renderer.end();

    }

    void drawScizzor(SpriteBatch batch) {

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        ScissorStack.calculateScissors(camera, batch.getTransformMatrix(), clipBounds, scissors);
        ScissorStack.pushScissors(scissors);

//        batch.draw(img, rand.nextInt(w), rand.nextInt(h), w, h);
        batch.flush();
        ScissorStack.popScissors();
    }

}
