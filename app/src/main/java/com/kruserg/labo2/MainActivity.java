package com.kruserg.labo2;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    DatabaseHelper dbHelper;
    List<AstreModel> astreList;

    Spaceship spaceship = new Spaceship(490,1600,100,100,R.drawable.spaceship,5);

    //layout
    FrameLayout fl_AlienSolarSystem;
    ImageView iv_spaceship;
    ImageView iv_astre;

    //Sensor
    private SensorManager sensorManager;
    Sensor accelerometer;

    //spaceship speed
    private int speed = spaceship.getSpeed();
    //space limit
    private int spaceWidth = 900;
    private int spaceHeight = 1600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MainActivity.this, accelerometer, SensorManager.SENSOR_DELAY_GAME);

        //Instance of the controller
        dbHelper = new DatabaseHelper(this);

        //view finder
        fl_AlienSolarSystem = findViewById(R.id.fl_AlienSolarSystem);

        //adding data to database
/*
        dbHelper.addOne(new AstreModel(-1, "Mercury", 10, "Red", false, "mercury"));
        dbHelper.addOne(new AstreModel(-1, "Venus", 10, "Red", false, "venus"));
        dbHelper.addOne(new AstreModel(-1, "Earth", 10, "Red", true, "earth"));
        dbHelper.addOne(new AstreModel(-1, "Mars", 10, "Red", true, "mars"));
        dbHelper.addOne(new AstreModel(-1, "Jupiter", 10, "Red", false, "jupiter"));
        dbHelper.addOne(new AstreModel(-1, "Saturn", 10, "Red", false, "saturn"));
        dbHelper.addOne(new AstreModel(-1, "Uranus", 10, "Red", false, "uranus"));
        dbHelper.addOne(new AstreModel(-1, "Neptune", 10, "Red", false, "neptune"));
*/

        //for each object execute the method on the object
         astreList = dbHelper.listAstre();
        for (AstreModel astre : astreList)
        {
           displayAstre(astre);


        }

        //deploying spaceship...
        deploySpaceship(spaceship);


    }



    //spaceship tracker and planet proximity listener
    public void onReached(AstreModel astreModel, Spaceship spaceship){
        //Here's how I visualize the rectangle ABCD
        // A              B
        // ----------------
        // |              |
        // |      .       |  <-- The point is the coordinates (X and Y) of our AstreModel object
        // |              |
        // ----------------
        // D              C

        //if x of spaceship is between D and C
        //if y of spaceship is between A and D
        boolean betweenDC = spaceship.getX()>=astreModel.getD()[0]&&spaceship.getX()<=astreModel.getC()[0];
        boolean betweenAD = spaceship.getY()>=astreModel.getA()[1]&&spaceship.getY()<=astreModel.getD()[1];

        if(betweenDC && betweenAD ){
            //and finally if it's between (A and D) and (D and C) do the following :
           final Toast toast = Toast.makeText(this, "You've reached " + astreModel.getNom() + " !",Toast.LENGTH_SHORT);
           toast.show();
           //using a handler to make to force hiding the toast after the user leaves the planet
            Handler handler = new Handler();
            handler.postDelayed(toast::cancel, 1000);

            //if the planet is inhabitable or not
            if(astreModel.isStatus()){
                astreModel.getView().setBackgroundColor(Color.GREEN);
            }else{
                astreModel.getView().setBackgroundColor(Color.RED);
            }
        }else{
            astreModel.getView().setBackgroundColor(0x00000000);
        }
    }



    public void deploySpaceship(Spaceship spaceship){
        iv_spaceship = new ImageView(MainActivity.this);
        iv_spaceship.setImageResource(spaceship.getDrawableId());
        //setting the size
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(spaceship.getWidth(), spaceship.getHeight());
        params.leftMargin = (int) spaceship.getX(); //x
        params.topMargin  = (int) spaceship.getY(); //-y
        fl_AlienSolarSystem.addView(iv_spaceship, params);

        iv_spaceship.setOnClickListener(v-> Toast.makeText(this, "("+spaceship.getX()+","+spaceship.getY()+")", Toast.LENGTH_SHORT).show());
    }


    public void displayAstre(AstreModel astre){
        //creating the img view
         iv_astre = new ImageView(MainActivity.this);
         //setting the view to the actual object
         astre.setView(iv_astre);
        //setting the image
        String uri = "@drawable/"+astre.getImage();
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        iv_astre.setImageResource(imageResource);
        //how big the planets should appear
        int scale = 20;
        //setting the size
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(astre.getTaille()*scale,astre.getTaille()*scale,11);// 11 means center
        //size of screen (x:0-900) (-y:0-1600)
        //setting the coordinates randomly, and making sure the x and y are within the screen
        astre.setX((int) getRng(0,spaceWidth)); //x
        astre.setY((int) getRng(0,spaceHeight)); //-y
        //four points of our rectangle to define the area of our planet
        astre.setA(new float[]{astre.getX() - 100, astre.getY() - 100});
        astre.setB(new float[]{astre.getX() + 100, astre.getY() - 100});
        astre.setC(new float[]{astre.getX() + 100, astre.getY() + 100});
        astre.setD(new float[]{astre.getX() - 100, astre.getY() + 100});

        params.leftMargin = (int) astre.getX();
        params.topMargin  = (int) astre.getY();
        fl_AlienSolarSystem.addView(iv_astre, params);

       // Toast.makeText(this, astre.getNom() + " is now in (" + astre.getX() + "," + astre.getY() + ")", Toast.LENGTH_LONG).show();

        iv_astre.setOnClickListener(v-> {
            Toast.makeText(this, astre.getNom() + " (" + astre.getX() + "," + astre.getY() + ")\n" +
                    "A("+astre.getA()[0]+","+astre.getA()[1]+")" + "\nB("+astre.getB()[0]+","+astre.getB()[1]+")" +
                            "\nC("+astre.getC()[0]+","+astre.getC()[1]+")"+"\nD("+astre.getD()[0]+","+astre.getD()[1]+")"
                    , Toast.LENGTH_SHORT).show();
        });
    }

    //method to get a random position using a min and max value
    public double getRng(double min, double max){
        return (double) (Math.round((Math.random()*((max-min)+1)+min)/10)*10);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int X = (int)event.values[0];
        int Y = (int)event.values[1];
        int Z = (int)event.values[2];
        if(Y == 9) spaceship.stopMoving(iv_spaceship);
        if(X == 0 && Y < 9 && Z > 0) spaceship.move(iv_spaceship,0,-speed); //forward
        if(X < 0 && Y < 9 && Z > 0) spaceship.move(iv_spaceship,speed,-speed); //forward left
        if(X > 0 && Y < 9 && Z > 0) spaceship.move(iv_spaceship,-speed,-speed); //forward right
        if(X == 0 && Y < 9 && Z < 0)spaceship.move(iv_spaceship,0,speed); //backward
        if(X > 0 && Y < 9 && Z < 0) spaceship.move(iv_spaceship,speed,speed); //backward left
        if(X < 0 && Y < 9 && Z < 0) spaceship.move(iv_spaceship,-speed,speed); //backward right


                for (AstreModel astre : astreList) {
                    onReached(astre, spaceship);
                }

    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }
}