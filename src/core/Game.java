package core;

public final class Game {
    private boolean running_ = false;
    private GameTimer gameTimer_ = null;

    public Game() {
    }

    public void initialise() {
        if (Context.params.opengl) {
            System.setProperty("sun.java2d.opengl", "True");
        }
        gameTimer_ = new GameTimer();
        Context.window.create();
        Context.renderer.setTarget(Context.window);
        running_ = true;
    }

    public void run() {
        gameTimer_.init();
        try {
            while (running_) {
                gameTimer_.beginFrame();
                Context.sceneMgr.update(gameTimer_.getDeltaTime());
                Context.renderer.render();
                gameTimer_.endFrame();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Context.window.close();
        System.out.println("Game exit.");
    }

    public void stop() {
        running_ = false;
    }

    public long getFrames() {
        return gameTimer_.getFrames();
    }

    public float getRealFps() {
        return gameTimer_.getRealFps();
    }
}

final class GameTimer {
    private long begin_;
    private long last_;
    private long frames_;
    private float deltaTime_;
    private final float fpslimit = Context.params.fpslimit;

    private final int recordcount = 30;
    private float[] record_ = new float[recordcount];
    private float realfps_ = fpslimit;

    void init() {
        begin_ = System.nanoTime();
        last_ = begin_;
        frames_ = 0;
        for (int i = 0; i < 30; i++) {
            record_[i] = 1 / fpslimit;
        }
    }

    void beginFrame() {
        if (frames_ == 0) {
            deltaTime_ = 1 / fpslimit;
        } else {
            long cur = System.nanoTime();
            deltaTime_ = (cur - last_) / 1E9f;
            last_ = cur;
        }
        float total = 0;
        for (int i = 0; i < recordcount - 1; i++) {
            total += record_[i] = record_[i + 1];
        }
        record_[recordcount - 1] = deltaTime_;
        total += deltaTime_;
        realfps_ = recordcount / total;
    }

    void endFrame() throws InterruptedException {
        frames_++;
        long margin = (long)(frames_ * 1000 / fpslimit - (System.nanoTime() - begin_) / 1E6f);
        if (margin > 0) {
            Thread.sleep(margin);
        }
    }

    float getDeltaTime() {
        return deltaTime_;
    }

    long getFrames() {
        return frames_;
    }

    float getRealFps() {
        return realfps_;
    }
}