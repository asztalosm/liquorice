import java.util.List;
import java.util.Random;

public class CreateEncounters {
    //


    /*
    room example: (╔ ═ ╗ ║ ╚ ╝ ╣ ╦ ╩ ╬ ╠   ╍ ┋)

         ╔══════════════════════╗
         ║                      ╠══════════════════╗
         ║          3.          |                  ║
         ║                      ╠══════╗           ║
         ╚══════╗---════════════║      ║    5.     ║
    ╔═══════════╝       ║       ║      ║           ║
    ║      ║            |       ║      ║           ║
    ║  2.  |     1.     ║   4.  ║      ╚═══════════╝
    ║      ║            ║       ║
    ║      ║            ║       ║
    ╚══════╩---═════════╣       ║
                        ║       ║
                        ╚═══════╝
    data about floor / encounter place:
    room count: 4
    1 opens 2,3,4
    3 opens 5
     */

    public static void createRooms(int roomCount) {
        long seed = System.currentTimeMillis();
        Random rng = new Random(seed);
        System.out.println("room created with " + roomCount + " rooms, seed: " + rng.nextInt());
        //this seed will be used for spawning enemies and the room layout
    }
    public static void createRooms(long seed, int roomCount) {
        Random rng = new Random(seed);
        System.out.println("room created with " + roomCount + " rooms, seed: " + rng.nextInt());

    }
}
