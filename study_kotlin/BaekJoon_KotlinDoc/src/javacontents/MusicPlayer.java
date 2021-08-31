package javacontents;

public class MusicPlayer extends Thread {
    int type;
    MusicBox musicBox = null;

    public MusicPlayer(int type, MusicBox musicBox) {
        this.type = type;
        this.musicBox = musicBox;
    }

    @Override
    public void run() {
        switch (type) {
            case 1:
                musicBox.playMusic1();
                break;
            case 2:
                musicBox.playMusic2();
                break;
            default:
                musicBox.playMusic3();
                break;
        }
    }
}

