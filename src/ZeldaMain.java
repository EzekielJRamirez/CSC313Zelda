import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.time.LocalTime;
import java.util.Random;
import java.util.Vector;

public class ZeldaMain {
    public static void main(String[] args) {
        System.out.println("hi");
    }

    //pages 109 - 150
    // we are responsible for these areas
    // dungeon:
    // due by
    /* zeek --> pages 120 to 130*/
    private static class PlayerMover implements Runnable {
        public PlayerMover() {
            velocitystep = 3;
        }

        public void run() {
            while (endgame == false) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {

                }
                if (upPressed || downPressed || leftPressed || rightPressed) {
                    p1velocity = velocitystep;
                    if ( upPressed) {
                        p1.setInternalAngle(fivequartersPi);
                    } else if (rightPressed) {
                        p1.setInternalAngle(5.49779);
                    } else {
                        p1.setInternalAngle(threehalvesPi);
                    }
                }
//                if (upPressed == true) {
//                    p1velocity = p1velocity + velocitystep;
//                }
//                if (downPressed == true) {
//                    p1velocity = p1velocity - velocitystep;
//                }
//                if (leftPressed == true) {
//                    if (p1velocity < 0) {
//                        p1.rotate(-rotatestep);
//                    } else {
//                        p1.rotate(rotatestep);
//                    }
//                }
//                if (rightPressed == true) {
//                    if (p1velocity < 0) {
//                        p1.rotate(rotatestep);
//                    } else {
//                        p1.rotate(-rotatestep);
//                    }
//                }
//                if (firePressed == true) {
//                    try {
//                        if (playerBullets.size() == 0) {
//                            insertPlayerBullet();
//                        } else if (System.currentTimeMillis() - playerBulletsTimes.elementAt(playerBulletsTimes.size() - 1) > playerbulletlifetime / 4.0) {
//                            insertPlayerBullet();
//                        }
//                    } catch (java.lang.ArrayIndexOutOfBoundsException aioobe) {
//                        aioobe.printStackTrace();
//                    }
                if ( downPressed) {
                    if (leftPressed) {
                        p1.setInternalAngle(2.35619);
                    } else if (rightPressed) {
                        p1.setInternalAngle(quarterPi);
                    } else {
                        p1.setInternalAngle(halfPi);
                    }
                }

                if (leftPressed) {
                    if (upPressed) {
                        p1.setInternalAngle(fivequartersPi);
                    } else if (downPressed) {
                        p1.setInternalAngle(threequartersPi);
                    } else {
                        p1.setInternalAngle(pi);
                    }
                }

                if (rightPressed) {
                    if (upPressed) {
                        p1.setInternalAngle(5.49779);
                    } else if (downPressed) {
                        p1.setInternalAngle(quarterPi);
                    } else {
                        p1.setInternalAngle(0);
                    }
                }
                else {
                    p1velocity = 0;
                    p1.setInternalAngle(threehalvesPi);
                }

                p1.updateBounce();
                p1.move(p1velocity * Math.cos(p1.getInternalAngle()), p1velocity * Math.sin(p1.getInternalAngle()));
                int wrap = p1.screenWrap(XOFFSET, XOFFSET + WINWIDTH, YOFFSET, YOFFSET + WINHEIGHT);
                backgroundState = bgWrap( backgroundState, wrap);
                if( wrap != 0) {
                    clearEnemies();
                    generateEnemies(backgroundState);
                }
            }
        }

        private double velocitystep;
    }

    private static void clearEnemies() {
        bluepigEnemies.clear();
        bubblebossEnemies.clear();
    }

    private static void generateEnemies( String backgroundState) {
        if (backgroundState.substring(0,6).equals("KI0809")) {
            bluepigEnemies.addElement( new ImageObject(20, 90, 33, 33, 0));
            bluepigEnemies.addElement( new ImageObject(250, 230, 33, 33, 0));
        }

        for (int i = 0; i < bluepigEnemies.size(); i++) {
            bluepigEnemies.elementAt(i).setMaxFrames(25);
        }
    }

    private static class EnemyMover implements Runnable {
        public EnemyMover() {
            bluepigvelocitystep = 2;
        }

        public void run() {
            Random randomNumbers = new Random(LocalTime.now().getNano());
            while (endgame == false && enemyAlive == true) {
                try {
                    Thread.sleep(10);

                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }

                try {
                    for (int i = 0; i < bluepigEnemies.size(); i++) {
                        int state = randomNumbers.nextInt(1000);
                        if (state < 5) {
                            bluepigvelocity = bluepigvelocitystep;
                            bluepigEnemies.elementAt(i).setInternalAngle(0);
                        } else if (state < 10) {
                            bluepigvelocity = bluepigvelocitystep;
                            bluepigEnemies.elementAt(i).setInternalAngle(halfPi);
                        } else if ( state < 15) {
                            bluepigvelocity = bluepigvelocitystep;
                            bluepigEnemies.elementAt(i).setInternalAngle(pi);
                        } else if (state < 20) {
                            bluepigvelocity = bluepigvelocitystep;
                            bluepigEnemies.elementAt(i).setInternalAngle(threehalvesPi);
                        } else if (state < 250) {
                            bluepigvelocity = bluepigvelocity;
                        } else {
                            bluepigvelocity = 0;
                        }
                        bluepigEnemies.elementAt(i).updateBounce();
                        bluepigEnemies.elementAt(i).move(bluepigvelocity * Math.cos(bluepigEnemies.elementAt(i).getInternalAngle()), bluepigVelocity * Math.sin(bluepigEnemies.elementAt(i).getInternalAngle()));
                    }
                    for (int i = 0; i < bubblebossEnemies.size(); i++) {
                        // nothing mentioned in book
                    }
//                    catch (java.lang.NullPointerException jlnpe) {
                        // nop --> note sure what sander's note means
//                    }
                } catch (java.lang.NullPointerException jlnpe) {

                }
//                } catch (NullPointerException jlnpe) {
//                    jlnpe.printStackTrace();
//                }
//                try {
//                    if (enemyAlive == true) {
//                        if (enemyBullets.size() == 0) {
//                            insertEnemyBullet();
//                        } else if (System.currentTimeMillis() - enemyBulletsTimes.elementAt(enemyBulletsTimes.size() - 1) > enemybulletlifetime / 4.0) {
//                            insertEnemyBullet();
//                        }
//                    }
//                } catch (java.lang.ArrayIndexOutOfBoundsException aioobe) {
//                    aioobe.printStackTrace();
//                }
            }
        }

        private double bluepigvelocitystep;
        private double bluepigvelocity;
    }

    private static class HealthTracker implements Runnable {
        public void run() {
            while ( endgame == false) {
                Long curTime = new Long ( System.currentTimeMillis());
                if (availableToDropLife && p1.getDropLife() > 0) {
                    int newLife = p1.getLife() - p1.getDropLife();
                    p1.setDropLife(0);
                    availableToDropLife = false;

                    lastDropLife = System.currentTimeMillis();
                    p1.setLife(newLife);

                    try {
                        AudioInputStream ais = AudioSystem.getAudioInputStream(new File("hurt.wav").getAbsoluteFile());
                        hurtclip.open(ais);
                        hurtclip.start();
                    } catch (Exception e) {
                        // left empty in book
                    }
                } else {
                    if (curTime - lastDropLife > dropLifeLifeTime) {
                        // wierd spelling is in book page 124
                        availableToDropLife = true;
                    }
                }
            }
        }
    }

    private static class CollisionChecker implements Runnable {

        public void run() {
            // line commented out by sanders
            //Random randomNumbers = new Random(LocalTime.now().getNano());
            while (endgame == false) {
                // check player against doors in given scenes
                if (backgroundState.substring(0,6).equals("KI0511")) {
                    if ( collisionOccurs(p1, doorKItoTC)) {
                        // maybe a type here
                        p1.moveto(p1originalX, p1originalY);
                        backgroundState = "TC0305";
                        clip.stop();
                        playAudio(backgroundState);
                    }
                } else if (backgroundState.substring(0, 6).equals("TC0305")){
                    if (collisionOccurs(p1, doorTCtoKI)) {
                        p1.moveto(p1originalX, p1originalY);
                        backgroundState = "KI0511";
                        clip.stop();
                        playAudio(backgroundState);
                    }
                }

                // check player and enemies against walls
                if (backgroundState.substring(0,6).equals("KI0510")) {
                    checkMoversAgainstWalls(wallsKI.elementAt(5).elementAt(10));
                }
                if (background.substring(0,6).equals("KI0809")) {
                    checkMoversAgainstWalls(wallsKI.elementAt(8).elementAt(9));
                }

                // check player against enemies
                for (int i = 0; i < bluepigEnemies.size(); i++) {
                    if (collisionOccurs(p1, bluepigEnemies.elementAt(i))) {
                        //System.out.println("Still Colliding: " + i + ", " + System.currentTimeMillis() );
                        p1.setBounce(true);
                        bluepigEnemies.elementAt(i).setBounce(true);
                        if (availableToDropLife) {
                            p1.setDropLife(1);
                        }
                    }
                }

                //todo: check enemies against walls

                //todo: check player against deep water or pits


                //todo: check player against enemy arrows

                //todo: check enemies against player weapons
            }
        }

        private static void checkMoversAgainstWalls (Vector<ImageObject> wallsInput) {
            // page 126
            for (int i = 0; i < wallsInput.size(); i++) {
                if (collisionOccurs( p1, wallsInput.elementAt(i))) {
                    p1.setBounce(true);
                }
                for (int j = 0; j < bluepigEnemies.size(); j++) {
                    if (collisionOccurs(bluepigEnemies.elementAt(j), wallsInput.elementAt(i))) {
                        bluepigEnemies.elementAt(j).setBounce(true);
                    }
                }
            }
        }
    }

    //todo make one lockrotate function which takes as input objInner,
    // objOuter, and point relative to objInner's x, y that objOuter must
    // rotate around.
    // dist is a distance between the two objects at the bottom of objInner
    private static void lockrotateObjAroundObjbottom(ImageObject objOuter, ImageObject objInner, double dist) {
        objOuter.moveto(objInner.getX() + (dist + objInner.getWidth() / 2.00) * Math.cos(-objInner.getAngle() + pi / 2.0) + objOuter.getWidth() / 2.0, objInner.getY() + (dist + objInner.getHeight() /2.0)*Math.sin(-objInner.getAngle() + pi/2.0) + objOuter.getHeight() / 2.0);
        objOuter.setAngle(objInner.getAngle());
    }
    //looks good
    private static void lockrotateObjAroundObjtop(ImageObject objOuter, .ImageObject objInner, double dist) {
        objOuter.moveto(objInner.getX() + objOuter.getWidth() + (objInner.getWidth() / 2.0) + (dist + objInner.getWidth() / 2.0) * Math.cos((objInner.getAngle() + pi/2.0)) / 2.0 , objInner.getY() - objOuter.getHeight() + (dist + objInner.getHeight() / 2.0) * Math.sin(objInner.getAngle()/ 2.0));
        objOuter.setAngle(objInner.getAngle());
    }

    private static AffineTransformOp rotateImageObject(ImageObject obj) {
        AffineTransform at = AffineTransform.getRotateInstance(-obj.getAngle() , obj.getWidth()/2.0, obj.getHeight()/2.0) ;
        AffineTransformOp atop = new AffineTransformOp(at,AffineTransformOp.TYPE_BILINEAR);
        return atop;
    }
    //looks good
    private static AffineTransformOp spinImageObject(ImageObject obj) {
        AffineTransform at = AffineTransform.getRotateInstance(-obj.getInternalAngle(), obj.getWidth()/2.0, obj.getHeight()/2.0);
        AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        return atop;
    }

    private static void backgroundDraw() {
        Graphics g = appFrame.getGraphics();
        Graphics2D g2D = (Graphics2D) g;

        if ( backgroundState.substring(0,2).equals("KI")) {
            int i = Integer.parseInt( backgroundState.substring(4,6) );
            int j = Integer.parseInt( backgroundState.substring(2,4));
            if (i < backgroundKI.size() ) {
                if (j < backgroundKI.elementAt(i).size()) {
                    g2D.drawImage(backgroundKI.elementAt(i).elementAt(j), XOFFSET, YOFFSET, null);
                }
            }
        }

        if (backgroundState.substring(0,2).equals("TC")) {
            int i = Integer.parseInt( backgroundState.substring(4,6) );
            int j = Integer.parseInt( backgroundState.substring(2,4));
            if (i < backgroundTC.size() ) {
                if (j < backgroundTC.elementAt(i).size()) {
                    g2D.drawImage(backgroundTC.elementAt(i).elementAt(j), XOFFSET, YOFFSET, null);
                }
            }
        }
    }

    private static void playerDraw() {
        // page 128
        Graphics g = appFrame.getGraphics();
        Graphics2D g2D = (Graphics2D) g;
//        g2D.drawImage(rotateImageObject(p1).filter(player, null), (int)(p1.getX() + 0.5), (int)(p1.getY()+ 0.5), null);
        if (upPressed || downPressed || leftPressed || rightPressed) {
            if (upPressed == true) {
                if (p1.getCurrentFrame() == 0) {
                    g2D.drawImage(rotateImageObject(p1).filter(link.elementAt(4), null), (int) (p1.getX() + 0.5), (int) (p1.getY() + 0.5), null);
                } else if (p1.geCurrentFrame() == 1) {
                    g2D.drawImage(rotateImageObject(p1).filter(link.elementAt(5), null), (int) (p1.getX() + 5), (int) (p1.getY() + 0.5), null);
                }
                p1.updateCurrentFrame();
            }
            if (downPressed == true) {
                if (p1.getCurrentFrame() == 0) {
                    g2D.drawImage(rotateImageObject(p1).filter(link.elementAt(2), null), (int) (p1.getX() + 0.5), (int) (p1.getY() + 0.5), null);
                } else if (p1.geCurrentFrame() == 1) {
                    g2D.drawImage(rotateImageObject(p1).filter(link.elementAt(3), null), (int) (p1.getX() + 5), (int) (p1.getY() + 0.5), null);
                }
                p1.updateCurrentFrame();
            }
            if (leftPressed == true) {
                if (p1.getCurrentFrame() == 0) {
                    g2D.drawImage(rotateImageObject(p1).filter(link.elementAt(0), null), (int) (p1.getX() + 0.5), (int) (p1.getY() + 0.5), null);
                } else if (p1.geCurrentFrame() == 1) {
                    g2D.drawImage(rotateImageObject(p1).filter(link.elementAt(1), null), (int) (p1.getX() + 5), (int) (p1.getY() + 0.5), null);
                }
                p1.updateCurrentFrame();
            }
            if (rightPressed == true) {
                if (p1.getCurrentFrame() == 0) {
                    g2D.drawImage(rotateImageObject(p1).filter(link.elementAt(6), null), (int) (p1.getX() + 0.5), (int) (p1.getY() + 0.5), null);
                } else if (p1.geCurrentFrame() == 1) {
                    g2D.drawImage(rotateImageObject(p1).filter(link.elementAt(7), null), (int) (p1.getX() + 5), (int) (p1.getY() + 0.5), null);
                }
                p1.updateCurrentFrame();
            }
        } else {
            if (Math.abs(lastPresed - 90) < 1) {
                g2D.drawImage(rotateImageObject(p1).filter(link.elementAt(4), null), (int) (p1.getX() + 0.5), (int) (p1.getY() + 0.5), null);
            }
            if (Math.abs(lastPressed - 270) < 1) {
                g2D.drawImage(rotateImageObject(p1).filter(link.elementAt(2), null), (int) (p1.getX() + 0.5), (int) (p1.getY() + 0.5), null);
            }
            if (Math.abs(lastPressed - 0) < 1) {
                g2D.drawImage(rotateImageObject(p1).filter(link.elementAt(6), null), (int) (p1.getX() + 0.5), (int) (p1.getY() + 0.5), null);
            }
            if (Math.abs(lastPressed - 180) < 1) {
                g2D.drawImage(rotateImageObject(p1).filter(link.elementAt(0), null), (int) (p1.getX() + 0.5), (int) (p1.getY() + 0.5), null);
            }
        }

        //d2D.drawImage(rotateImageObject(p1).filter(player, null), (int)(p1.getX() + 0.5), (int)(p1.getY() + 0.5), null);
    }

    private static void healthDraw(){
        Graphics g=appFrame.getGraphics();
        Graphics2D g2D=(Graphics2D)g;
        int leftscale=10;
        int leftoffset=10;
        int rightoffset=9;
        int interioroffset=2;
        int halfinterioroffset=1;
        for(int i=0;i<p1.getMaxLife();i++){
            if(i%2==0){
                g2D.drawImage(rotateImageObject(p1).filter(leftHeartOutline,null),leftscale*i+leftoffset+XOFFSET,YOFFSET,
                        null);
            }else{
                g2D.drawImage(rotateImageObject(p1).filter(
                        rightHeartOutline,null),leftscale*i+rightoffset+
                        XOFFSET,YOFFSET,null);
            }
        }
        for(int i=0;i<p1.getLife();i++){
            if(i%2==0){
                g2D.drawImage(rotateImageObject(p1).filter(leftHeart,null
                        ),leftscale*i+leftoffset+interioroffset+XOFFSET,
                        interioroffset+YOFFSET,null);
            }else{
                g2D.drawImage(rotateImageObject(p1).filter(rightHeart,null
                ),leftscale*i+leftoffset-halfinterioroffset+
                        XOFFSET,interioroffset+YOFFSET,null);
            }
        }
        for(int i=0;i<p1.getLife();i++){
            if(i%2==0){
                g2D.drawImage(rotateImageObject(p1).filter(leftHeart,null
                        ),leftscale*i+leftoffset+interioroffset+XOFFSET,
                        interioroffset+YOFFSET,null);
            }else{
                g2D.drawImage(rotateImageObject(p1).filter(rightHeart,null
                ),leftscale*i+leftoffset-halfinterioroffset+
                        XOFFSET,interioroffset+YOFFSET,null);
            }
        }
    }

    private static void enemiesDraw(){
        Graphics g=appFrame.getGraphics();
        Graphics2D g2D=(Graphics2D)g;
        for(int i=0;i<bluepigEnemies.size();i++){
            if(Math.abs(bluepigEnemies.elementAt(i).getInternalAngle()-0.0
            )< 1.0){
                if(bluepigEnemies.elementAt(i).getCurrentFrame()<
                        bluepigEnemies.elementAt(i).getMaxFrames()/2){
                    g2D.drawImage(rotateImageObject(bluepigEnemies.elementAt(i)).filter(bluepigEnemy.elementAt(6),null),(int)(
                            bluepigEnemies.elementAt(i).getX()+0.5),(int)(
                            bluepigEnemies.elementAt(i).getY()+0.5),null);
                }else{
                    g2D.drawImage(rotateImageObject(bluepigEnemies.elementAt(i)).filter(bluepigEnemy.elementAt(7),null),(int)(
                            bluepigEnemies.elementAt(i).getX()+0.5),(int)(
                            bluepigEnemies.elementAt(i).getY()+0.5),null);
                }
                bluepigEnemies.elementAt(i).updateCurrentFrame();
            }
            if(Math.abs(bluepigEnemies.elementAt(i).getInternalAngle()-pi
            )< 1.0){
                if(bluepigEnemies.elementAt(i).getCurrentFrame()<
                        bluepigEnemies.elementAt(i).getMaxFrames()/2){
                    g2D.drawImage(rotateImageObject(bluepigEnemies.elementAt(i)).filter(bluepigEnemy.elementAt(4),null),(int)(
                            bluepigEnemies.elementAt(i).getX()+0.5),(int)(
                            bluepigEnemies.elementAt(i).getY()+0.5),null);
                }else{
                    g2D.drawImage(rotateImageObject(bluepigEnemies.elementAt(i)).filter(bluepigEnemy.elementAt(5),null),(int)(
                            bluepigEnemies.elementAt(i).getX()+0.5),(int)(
                            bluepigEnemies.elementAt(i).getY()+0.5),null);
                }
                bluepigEnemies.elementAt(i).updateCurrentFrame();
            }
            if(Math.abs(bluepigEnemies.elementAt(i).getInternalAngle()-halfPi)< 1.0){
                if(bluepigEnemies.elementAt(i).getCurrentFrame()<
                        bluepigEnemies.elementAt(i).getMaxFrames()/2){
                    g2D.drawImage(rotateImageObject(bluepigEnemies.elementAt(i
                    )).filter(bluepigEnemy.elementAt(2),null),(int)(
                            bluepigEnemies.elementAt(i).getX()+0.5),(int)(
                            bluepigEnemies.elementAt(i).getY()+0.5),null);
                }else{
                    g2D.drawImage(rotateImageObject(bluepigEnemies.elementAt(i
                    )).filter(bluepigEnemy.elementAt(3),null),(int)(
                            bluepigEnemies.elementAt(i).getX()+0.5),(int)(
                            bluepigEnemies.elementAt(i).getY()+0.5),null);
                }
                bluepigEnemies.elementAt(i).updateCurrentFrame();
            }
            if(Math.abs(bluepigEnemies.elementAt(i).getInternalAngle()-threehalvesPi)< 1.0){
                if(bluepigEnemies.elementAt(i).getCurrentFrame()<
                        bluepigEnemies.elementAt(i).getMaxFrames()/2){
                    g2D.drawImage(rotateImageObject(bluepigEnemies.elementAt(i
                    )).filter(bluepigEnemy.elementAt(0),null),(int)(
                            bluepigEnemies.elementAt(i).getX()+0.5),(int)(
                            bluepigEnemies.elementAt(i).getY()+0.5),null);

                }else{
                    g2D.drawImage(rotateImageObject(bluepigEnemies.elementAt(i
                    )).filter(bluepigEnemy.elementAt(1),null),(int)(
                            bluepigEnemies.elementAt(i).getX()+0.5),(int)(
                            bluepigEnemies.elementAt(i).getY()+0.5),null);
                }
                bluepigEnemies.elementAt(i).updateCurrentFrame();
            }
        }
    }

    private static class KeyPressed extends AbstractAction {

        public KeyPressed() {
            action = ””;
        }

        public KeyPressed(String input) {

            action = input;
        }

        public void actionPerformed(ActionEvent e) {

            if (action.equals( ”UP”)) {
                upPressed = true;
                lastPressed = 90.0;
            }

            if (action.equals( ”DOWN”)) {
                downPressed = true;
                lastPressed = 270.0;
            }

            if (action.equals( ”LEFT”)) {
                leftPressed = true;
                lastPressed = 180.0;
            }

            if (action.equals( ”RIGHT”)) {
                rightPressed = true;
                lastPressed = 0.0;
            }

            if (action.equals( ”A”)) {
                aPressed = true;
            }
            if (action.equals( ”X” )) {
                xPressed = true;
            }
        }

        private String action;
    }

    private static class KeyReleased extends AbstractAction {
        public KeyReleased() {
            action = ” ”;
        }

        public KeyReleased(String input) {
            action = input;
        }

        public void actionPerformed(ActionEvent e) {
            if (action.equals( ”UP” )) {
                upPressed = false;
            }
            if (action.equals( ”DOWN” )) {
                downPressed = false;
            }
            if (action.equals( ”LEFT” )) {
                leftPressed = false;
            }
            if (action.equals( ”RIGHT” )) {
                rightPressed = false;
            }
            if (action.equals( ”A” )) {
                aPressed = false;
            }
            if (action.equals( ”X” )) {
                xPressed = false;
            }
        }

        private String action;
    }

    private static class QuitGame implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            endgame = true;
        }
    }

    private static class StartGame implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            endgame = true;
            upPressed = false;
            downPressed = false;
            leftPressed = false;
            rightPressed = false;
            aPressed = false;
            xPressed = false;
            lastPressed = 90.0;
            backgroundState = ”KI0809”;
            availableToDropLife = true;
            try {
                clearEnemies();
                generateEnemies(backgroundState);
            } catch (java.lang.NullPointerException jlnpe) {
            }
            p1 = new ImageObject(p1originalX, p1originalY, p1width, p1height, 0.0);
            p1velocity = 0.0;
            p1.setInternalAngle(threehalvesPi); // 270 degrees , in radians
            p1.setMaxFrames(2);
            p1.setlastposx(p1originalX);
            p1.setlastposy(p1originalY);
            p1.setLife(6);
            p1.setMaxLife(6);
            doorKItoTC = new ImageObject(200, 55, 35, 35, 0.0);
            doorTCtoKI = new ImageObject(150, 270, 35, 35, 0.0);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ie) {
            }
            lastAudioStart = System.currentTimeMillis();
            playAudio(backgroundState);
            endgame = false;
            lastDropLife = System.currentTimeMillis();
            Thread t1 = new Thread(new Animate());
            Thread t2 = new Thread(new PlayerMover());
            Thread t3 = new Thread(new CollisionChecker());
            Thread t4 = new Thread(new AudioLooper());
            Thread t5 = new Thread(new EnemyMover());
            Thread t6 = new Thread(new HealthTracker());
            t1.start();
            t2.start();
            t3.start();
            t4.start();
            t5.start();
            t6.start();
        }
    }

    private static class GameLevel implements ActionListener {
        public int decodeLevel(String input) {
            int ret = 3;
            if (input.equals( ”One” )) {
                ret = 1;
            } else if (input.equals( ”Two” )) {
                ret = 2;
            } else if (input.equals( ”Three ” )) {
                ret = 3;
            } else if (input.equals( ”Four ” )) {
                ret = 4;
            } else if (input.equals( ” Five ” )) {
                ret = 5;
            } else if (input.equals( ” Six ” )) {
                ret = 6;
            } else if (input.equals( ”Seven” )) {
                ret = 7;
            } else if (input.equals( ”Eight ” )) {
                ret = 8;
            } else if (input.equals( ”Nine ” )) {
                ret = 9;
            } else if (input.equals( ”Ten” )) {
                ret = 10;
            }
            return ret;
        }

        public void actionPerformed(ActionEvent e) {
            JComboBox cb = (JComboBox) e.getSource();
            String textLevel = (String) cb.getSelectedItem();
            level = decodeLevel(textLevel);
        }
    }

    private static Boolean isInside(double p1x, double p1y, double p2x1, double p2y1, double p2x2, double p2y2) {
        Boolean ret = false;
        if (p1x > p2x1 && p1x < p2x2) {
            if (p1y > p2y1 && p1y < p2y2) {
                ret = true;
            }
            if (p1y > p2y2 && p1y < p2y1) {
                ret = true;
            }
        }
        if (p1x > p2x2 && p1x < p2x1) {
            if (p1y > p2y1 && p1y < p2y2) {
                ret = true;
            }
            if (p1y > p2y2 && p1y < p2y1) {
                ret = true;
            }
        }
        return ret;
    }

    private static Boolean collisionOccursCoordinates(double p1x1, double
            p1y1, double p1x2, double p1y2, double p2x1, double p2y1, double
                                                              p2x2, double p2y2) {
        Boolean ret = false;
        if (isInside(p1x1, p1y1, p2x1, p2y1, p2x2, p2y2) == true) {
            ret = true;
        }
        if (isInside(p1x1, p1y2, p2x1, p2y1, p2x2, p2y2) == true) {
            ret = true;
        }
        if (isInside(p1x2, p1y1, p2x1, p2y1, p2x2, p2y2) == true) {
            ret = true;
        }
        if (isInside(p1x2, p1y2, p2x1, p2y1, p2x2, p2y2) == true) {
            ret = true;
        }
        if (isInside(p2x1, p2y1, p1x1, p1y1, p1x2, p1y2) == true) {
            ret = true;
        }
        if (isInside(p2x1, p2y2, p1x1, p1y1, p1x2, p1y2) == true) {
            ret = true;
        }
        if (isInside(p2x2, p2y1, p1x1, p1y1, p1x2, p1y2) == true) {
            ret = true;
        }
        if (isInside(p2x2, p2y2, p1x1, p1y1, p1x2, p1y2) == true) {
            ret = true;
        }
        return ret;
    }

    private static Boolean collisionOccurs(ImageObject obj1, ImageObject
            obj2) {
        Boolean ret = false;
        if (collisionOccursCoordinates(obj1.getX(), obj1.getY(), obj1.getX()
                        + obj1.getWidth(), obj1.getY() + obj1.getHeight(), obj2.getX(),
                obj2.getY(), obj2.getX() + obj2.getWidth(), obj2.getY() + obj2.
                        getHeight()) == true) {
            ret = true;
        }
        return ret;
    }

    private static class ImageObject {
        public ImageObject() {
            maxFrames = 1;
            currentFrame = 0;
            bounce = false;
            life = 1;
            maxLife = 1;
            dropLife = 0;
        }

        public ImageObject(double xinput, double yinput, double xwidthinput,
                           double yheightinput, double angleinput) {
            this();
            x = xinput;
            y = yinput;
            lastposx = x;
            lastposy = y;
            xwidth = xwidthinput;
            yheight = yheightinput;
            angle = angleinput;
            internalangle = 0.0;
            coords = new Vector<Double>();
        }
        //missing a closing curly brace but it is in collin's section we are fine
}
