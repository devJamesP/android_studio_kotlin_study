package javacontents;

public class MusicBoxExam1 {
    public static void main(String[] args) {
        MusicBox box = new MusicBox();

        MusicPlayer player1 = new MusicPlayer(1, box);
        MusicPlayer player2 = new MusicPlayer(2, box);
        MusicPlayer player3 = new MusicPlayer(3, box);

        player1.start();
        player2.start();
        player3.start();
    }
}
