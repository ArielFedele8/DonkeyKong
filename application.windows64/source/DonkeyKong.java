import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class DonkeyKong extends PApplet {

static final int MAP_WIDTH=14; //Larghezza mappa
static final int MAP_HEIGHT=32; //Altezza mappa
static final int NUMERO_BARILI=15; //Numero dei barili
static final float JUMP_ACCEL_DONKEY=0.215f; //Accelerazione durante il salto di Donkey
static final int CLIMBING_SPEED=100; //Velocità di salita delle scale
static final int BARREL_THROW_ANIM_TIME=800; //Tempo in millisecondi fra il lancio dei barili
int spriteX=14, DonkeyY=513, SpriteDonkey=625, SpriteDonkeyX=45, DonkeyWidth=110, ScaleY=580, DonkeyX=260; //Variabili Donkey
int j1=0, x=0, y=0, i=0; //j1: Verifica salti Donkey.    x,y,i: Variabili contatore
int riga=0, mapState=0, traveInc=0, Salti=0;
int p=0, peachLast,peachAnim=300; //p: Frame animazione peach.   peachAnim: Tempo in millisecondi fra le animazioni di Peach.
int spritePetrolioX, TimingPetrolio, TimingLancioBarili; //Variabili dedicate al barile di petrolio.
int rigaTrave; //Riga della tile trave.
int m=0, n=0; //variabili per generazione travi
int lastTime; //momento dell'ultima animazione
float jSpeed=6, Vx=1, j=0; //variabili salti Donkey.   jSpeed:velocità verticale.   Vx: velocità orizzontale. j: contatore.
boolean stop=false, stopSalti=false, stopScalePercorso=true, delayed=false; //stop:fine salita scale di Donkey.   stopSalti:fine salti di Donkey.    delayed:delay a fine salita delle scale di Donkey.

float tempoBarile; //Tempo fra gli spawn dei barili
int ultimoBarile; //Momento del lancio dell'ultimo barile
int ultimaAnimazione; //Momento dell'ultima animazione del lancio dei barili di Donkey
int animazione=0; //frame animazione lancio dei barili di Donkey
boolean throwing=false; //Variabile booleana per verificare se Donkey sta lanciando un barile
int b=0; //numero barili attivi.
int bariliLanciati=0; //numero di barili lanciati
boolean firstBarrel=false; //Variabile per verificare se il primo barile è stato lanciato

PImage [] ScaleSalita; //Array di immagini contenente le immagini delle scale nel livello
PImage [] scalePercorso; //Array di immagini contenente le immagini delle scale nella cinematica di Donkey
PImage [] tileset; //Array contenente le tile della mappa
PImage DonkeyKong, PrelievoBarili, ScalePeach, ScalePeachPiccole, barilePetrolio; //DonkeyKong: sprite di Donkey.   PrevievoBarili: sprite pila di barili accanto a Donkey.   ScalePeach:sprite scale lunghe accanto a peach.   ScalePeachPiccole:sprite scale sotto la piattaforma di Peach.   barilePetrolio: Sprite barile blu di petrolio
PImage victoryScreen; //Schermata di vittoria
PFont font;
Barili[] barile; //array ci oggetti barile

PImage marioScale []; //array sprite animazione di Mario sulle scale
PImage marioMov []; //array sprite animazioni movimento mario a terra
PImage marioDeath []; //array sprite animazione morte Mario
PImage peach[]; //array sprite animazione Peach


int [][] maze = {
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
  {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, 
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
  {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, 
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
  {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, 
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
  {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, 
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
  {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, 
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
  {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, 
  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
};


public void setup() {
  frameRate(60);
  
  
  //Inserimento dei barili nel vettore
  barile= new Barili [NUMERO_BARILI];
  for (int b=0; b<NUMERO_BARILI; b++) {
    Barili nuovo= new Barili();
    barile[b] = nuovo;
  }
  
  //Caricamento e ridimensionamento schermata di vittoria
  victoryScreen=loadImage("arcadeScreen.png");
  victoryScreen.resize(560,640);
  
  //Caricamento font
  font = createFont("ARCADECLASSIC.TTF",40);
  
  //Inserimento degli sprite di Peach nel vettore
  peach= new PImage[2];
  peach[0] = loadImage("peach1.png");
  peach[1] = loadImage("peach2.png");

  //Inserimento degli sprite della morte di Mario nel vettore
  marioDeath = new PImage[5];
  marioDeath[0] = loadImage("Mario/marioDeathU.png");
  marioDeath[1] = loadImage("Mario/marioDeathR.png");
  marioDeath[2] = loadImage("Mario/marioDeathD.png");
  marioDeath[3] = loadImage("Mario/marioDeathL.png");
  marioDeath[4] = loadImage("Mario/F.png");
  
  //Inserimento degli sprite del movimento a terra di Mario nel vettore
  marioMov = new PImage[6];
  marioMov [0] = loadImage("Mario/mov1r.png");
  marioMov [1] = loadImage("Mario/mov2r.png");
  marioMov [2] = loadImage("Mario/jumpr.png");
  marioMov [3] = loadImage("Mario/mov1l.png");
  marioMov [4] = loadImage("Mario/mov2l.png");
  marioMov [5] = loadImage("Mario/jumpl.png");
  
  //Inseimento degli sprite del movimento sulle scale di Mario nel vettore
  marioScale = new PImage[7];
  marioScale [0] = loadImage("Mario/marioScaleStanding.png");
  marioScale [1] = loadImage("Mario/marioScaleRight.png");
  marioScale [2] = loadImage("Mario/marioScaleLeft.png");
  marioScale [3] = loadImage("Mario/marioScaleAlmostRight.png");
  marioScale [4] = loadImage("Mario/marioScaleAlmostLeft.png");
  marioScale [5] = loadImage("Mario/marioScaleTopRight.png");
  marioScale [6] = loadImage("Mario/marioScaleTopLeft.png");
  
  //Inserimento degli sprite delle scale della cinematica nel vettore
  ScaleSalita = new PImage[23];
  for (i=0; i<23; i++) {
    ScaleSalita[i]=loadImage("ScaleSalita.png");
  }
  
  //Inserimento degli sprite della mappa nel vettore
  tileset = new PImage[2];
  tileset [1] = loadImage("wall.png");
  tileset [0] = loadImage("black.png");
  
  //Inserimento degli sprite delle scale del livello nel vettore
  scalePercorso = new PImage[16];
  scalePercorso [0] = loadImage("ScalePercorso/ScalePercorso1.png");
  scalePercorso [1] = loadImage("ScalePercorso/ScalePercorso2.png");
  scalePercorso [2] = loadImage("ScalePercorso/ScalePercorso3.png");
  scalePercorso [3] = loadImage("ScalePercorso/ScalePercorso4.png");
  scalePercorso [4] = loadImage("ScalePercorso/ScalePercorso5.png");
  scalePercorso [5] = loadImage("ScalePercorso/ScalePercorso6.png");
  scalePercorso [6] = loadImage("ScalePercorso/ScalePercorso7.png");
  scalePercorso [7] = loadImage("ScalePercorso/ScalePercorso8.png");
  scalePercorso [8] = loadImage("ScalePercorso/ScalePercorso9.png");
  scalePercorso [9] = loadImage("ScalePercorso/ScalePercorso10.png");
  scalePercorso [10] = loadImage("ScalePercorso/ScalePercorso10.png");
  scalePercorso [11] = loadImage("ScalePercorso/ScalePercorso11.png");
  scalePercorso [12] = loadImage("ScalePercorso/ScalePercorso12.png");
  scalePercorso [13] = loadImage("ScalePercorso/ScalePercorso13.png");
  scalePercorso [14] = loadImage("ScalePercorso/ScalePercorso14.png");
  scalePercorso [15] = loadImage("ScalePercorso/ScalePercorso15.png");

  //Caricamento sprite
  DonkeyKong= loadImage("DonkeyKong.png");
  ScalePeach= loadImage("ScalePeach.png");
  ScalePeachPiccole= loadImage("ScalePeachPiccole.png"); 
  PrelievoBarili= loadImage("PrelievoBarili.png");
  barilePetrolio= loadImage("BariliPetrolio.png");
}


public void draw() {
  if (win==false) { //Controllo stato del gioco
    background(0);
    
    //Disegno delle scale nella mappa e delle piattaforme
    scalePercorso();
    travi();

    //Disegno base di Peach
    BasePeach();
    
    //Piazzamento immagini statiche nella mappa
    image(PrelievoBarili, 0, 140, 50, 80);
    image(ScalePeach, 159, 90, 60, 130);
    image(ScalePeachPiccole, 320, 170, 19, 50);
    
    //Disegno delle scale nella mappa
    Scale();
    
    //Disegno dinamico di Donkey
    copy(DonkeyKong, SpriteDonkey, 0, SpriteDonkeyX, 40, DonkeyX, DonkeyY, DonkeyWidth, 100);
    
    //Movimenti cinematica di Donkey
    if (j1<1)
      DonkeyKongSalita();
    if (j1==2) {
      DonkeyKongSalto();
    }
    if (mapState>=6) { //Verifica dello stato della mappa (fine cinematica)
      peach(); //Disegno di Peach
      
      //Modifica stato di gioco
      if (playing==false && gameOver==false)
        playing=true;
        
      //Disegno dei barili
      DonkeyBarili();
      for (int i=0; i<NUMERO_BARILI; i++)
        barile[i].move();
      mario();
    }
    updateState();
  } else {
    //Schermata di vittoria
    background(0);
    background(victoryScreen);
    textAlign(CENTER);
    textFont(font);
    text("YOU WON",width/2+25,height/2-90);
  }
}

//Animazione lancio barili e disegno dei barili
public void DonkeyBarili() {
  
  //Verifica lancio primo barile
  if (firstBarrel==false) {
    tempoBarile=1;
    firstBarrel=true;
    throwing=true;
  }
  if (firstBarrel && throwing==false && bariliLanciati<NUMERO_BARILI) {
    tempoBarile=random(1000, 5000);
    throwing=true;
    ultimoBarile=millis();
    
    //Inizio animazioni lancio barili
  } else if (throwing) {
    if (millis()-ultimoBarile>tempoBarile && animazione==0) {
      DonkeyY=130;
      SpriteDonkey=47;
      DonkeyX=50;
      animazione++;
      ultimaAnimazione=millis();
    }
    if (millis()-ultimaAnimazione>BARREL_THROW_ANIM_TIME && animazione==1) {
      SpriteDonkey=150;
      DonkeyX=80;
      barile[b].active=true;
      bariliLanciati++;
      if (b<NUMERO_BARILI-1) b++;
      else b=0;
      animazione++;
      ultimaAnimazione=millis();
    }
    if (millis()-ultimaAnimazione>BARREL_THROW_ANIM_TIME && animazione==2) {
      animazione=0;
      SpriteDonkey=0;
      DonkeyX=60;
      throwing=false;
    }
  }
  //Fine animazioni lancio barili
}

//Disegno delle scale
public void Scale() {
  if (!stop) {
    ScaleY=580-x*20;
    for (i=x; i<14; i++) {
      image(ScaleSalita[i], 320, ScaleY, 20, 20);
      ScaleY-=20;
      if (DonkeyY+110 <= 580-i*20) {
        x++;
      }
    }
    ScaleY=580-y*20;
    for (i=y; i<14; i++) {
      image(ScaleSalita[i], 280, ScaleY, 20, 20);
      ScaleY-=20;
      if (DonkeyY+110 <= 580-i*20)
        y++;
    }
    for (i=0; i<4; i++) {
      image(ScaleSalita[i], 320, 300-(i*20), 20, 20);
      image(ScaleSalita[i], 280, 300-(i*20), 20, 20);
    }
  }
}

//Disegno delle scale presenti durante il percorso
public void scalePercorso() {
  if (stopScalePercorso==false) {
    image(scalePercorso[0], 200, 547, 20, 10);
    image(scalePercorso[1], 200, 600, 20, 20);
    image(scalePercorso[2], 459, 562, 20, 44);
    image(scalePercorso[3], 240, 472, 20, 60);
    image(scalePercorso[4], 80, 482, 20, 42);
    image(scalePercorso[5], 160, 438, 20, 20);
    image(scalePercorso[6], 160, 388, 20, 15);
    image(scalePercorso[7], 280, 394, 20, 60);
    image(scalePercorso[8], 459, 402, 20, 44);
    image(scalePercorso[9], 419, 360, 20, 20);
    image(scalePercorso[10], 419, 306, 20, 20);
    image(scalePercorso[11], 180, 318, 20, 50);
    image(scalePercorso[12], 80, 322, 20, 42);
    image(scalePercorso[13], 220, 263, 20, 33);
    image(scalePercorso[14], 220, 240, 20, 10);
    image(scalePercorso[15], 459, 244, 20, 40);
  }
}

//Cinematica salita scale di Donkey
public void DonkeyKongSalita() {
  while (millis()-lastTime>CLIMBING_SPEED) {
    lastTime=millis();
    if ((SpriteDonkey==625 || SpriteDonkey==575) && DonkeyY > 220) {
      DonkeyY-=10;
      if (DonkeyY > 230) {
        if (SpriteDonkey==625)
          SpriteDonkey=575;
        else 
        SpriteDonkey=625;
      }
    }
    if (DonkeyY<=220 && delayed==false) { 
      delay(300); 
      delayed=true;
    }
  }

  if (DonkeyY<220 && !stop) {
    DonkeyY-=jSpeed;
    jSpeed-=JUMP_ACCEL_DONKEY;
    if (jSpeed<0 && DonkeyY>=130) {
      stop=true;
      j1=1;
      mapState++;
    }
  }

  if (j1==1) {
    SpriteDonkey=0;
    SpriteDonkeyX=40;
    DonkeyWidth=100;
    DonkeyY=130;
    jSpeed=2;
    j1=2;
    x=0;
  }
}

//Cinematica salti di Donkey
public void DonkeyKongSalto() {
  if (DonkeyY<131 && stopSalti==false && DonkeyX>60) {
    DonkeyY-=jSpeed;  
    DonkeyX-=Vx;
    if (j==0.25f && DonkeyY>64)
      DonkeyX-=2;
    if (j==0.25f && DonkeyY<=64) {
      DonkeyX-=1;
    }
    jSpeed-=JUMP_ACCEL_DONKEY;
    if (DonkeyY>=130 && DonkeyX>90) {
      jSpeed=2;
      delay(50);
    }
    if (DonkeyX==64) {
      stopSalti=true;
      stopScalePercorso=false;
      DonkeyX=62;
      DonkeyY+=4;
    }
    if (j==0.50f)
      j=0;
    if (j!=0.25f && j!=0.375f)
      j+=0.25f;
    else
      j+=0.125f;
  }
}

//Disegno delle travi su cui sta in piedi Peach
public void BasePeach() {
  image(tileset[1], 5*40+20, 8*20-10, 40, 20);
  image(tileset[1], 6*40+20, 8*20-10, 40, 20);
  image(tileset[1], 7*40+20, 8*20-10, 40, 20);
}

//Aggiornamento delle variabili
public void updateState() {
  if (DonkeyX==220)
    mapState++;
  if (DonkeyX==180)
    mapState++;
  if (DonkeyX==140)
    mapState++;
  if (DonkeyX==100)
    mapState++;
  if (DonkeyX==62)
    mapState++;
  if (mapState>=6) {
    BarilePetrolio();
    TimingPetrolio++;
  }
  //Reset delle variabili in caso di game over
  if (reset) {
    spriteX=14; 
    DonkeyY=513; 
    SpriteDonkey=625; 
    SpriteDonkeyX=45; 
    DonkeyWidth=110; 
    ScaleY=580; 
    DonkeyX=260;
    j1=0; 
    x=0; 
    y=0; 
    i=0; 
    riga=0; 
    mapState=0; 
    traveInc=0; 
    Salti=0; 
    m=0; 
    n=0;
    lastTime=0;
    jSpeed=6; 
    Vx=1; 
    j=0;
    b=0;
    bariliLanciati=0;
    stop=false; 
    stopSalti=false; 
    stopScalePercorso=true;
    delayed=false;
    throwing=false;
    throwing=false;
    firstBarrel=false;
    reset=false;
    dir=0; 
    anim=0;
    marioX=100; 
    marioY=580; 
    animScale=1;
    jump=false; 
    scala=false;
    playing=false; 
    gameOver=false;
    reset=false;
    climbing=false;
    morte=0;
    giri=0;
    for (int i=0; i<NUMERO_BARILI; i++) {
      barile[i].inAir=false;
      barile[i].bounce=false;
      barile[i].active=false;
      barile[i].set=false;
      barile[i].xPos=-30;
      barile[i].yPos=580;
    }
  }
}

//Disegno barile di petrolio
public void BarilePetrolio() {
  copy(barilePetrolio, spritePetrolioX, 0, 23, 32, 40, 540, 57, 80);
  if (TimingPetrolio==6) {
    if (spritePetrolioX==0)
      spritePetrolioX=24;
    else
      spritePetrolioX=0;
    TimingPetrolio=0;
  }
}

//Disegno e animazione di Peach
public void peach(){
  image(peach[p],220,95,37,55);
  if(millis()-peachLast>peachAnim){
    peachLast=millis();
    if(p==0) p++;
    else p--;
  }
}
//"Classe Barili"
class Barili {
  int SpriteBarile, TimingBarile; //SpriteBarile: sprite attuale del barile.   TimingBarile: tempo in frame tra le animazioni dei barili.
  int xPos; //Posizione y del barile.
  int yPos; //Posizione x del barile.
  int speed; //velocità del barile.
  int Rimbalzi; //Direzione di movimento del barile.
  float fallV; //Velocità caduta del barile.
  boolean inAir=false, bounce=false, active=false, set=false; //Stati del barile.
  PImage Barili; //Immagine del barile.
  float fallChance; //Possibilità dei barili di scendere su una scala.
  boolean cadutaBarile=false; //Discesa sulla scala dei barili.
  boolean changeDir=true; //Cambio di direzione dei barili.

  //Setup della classe "Barili".
  public Barili() {
    Barili= loadImage("Barili.png");
    xPos = 160;
    yPos = 195;
    speed = 3;
    SpriteBarile=0;
    TimingBarile=0;
    Rimbalzi=0;
    active=false;
  }

  //Draw della classe "Barili".
  public void move() {
    if (active) {
      //Creazione del barile.
      if (set==false) {
        xPos=160;
        yPos=195;
        Rimbalzi=0;
        TimingBarile=0;
        SpriteBarile=0;
        set=true;
      }
      copy(Barili, SpriteBarile, 0, 12, 10, xPos, yPos, 30, 25);
      //Passaggio allo stato di caduta di un barile da una trave.
      if (((xPos<5 && Rimbalzi==1) || (xPos>521 && Rimbalzi==0)) && get(xPos+15, yPos+25)!=color(255, 35, 91) && inAir==false && yPos<590) {
        speed=0;
        inAir=true;
        fallV=0;
      } 
      else speed=3;
      //Inizio stato di caduta di un barile da una trave.
      if (inAir) {
        for (int pixel=0; pixel<=fallV; pixel++) {
          if (get(xPos+15, yPos+25+pixel)==color(255, 35, 91) && bounce==false) {
            fallV=-fallV*0.20f;
            bounce=true;
            yPos-=pixel;
          } else if (get(xPos+15, yPos+25+pixel)==color(255, 35, 91)) {
            bounce=false;
            inAir=false;
            yPos--;
            if (Rimbalzi==0) Rimbalzi++;
            else if (Rimbalzi==1) Rimbalzi--;
          }
        }
        yPos+=fallV;
        fallV+=GRAVITY;
      } 
      
      else if (inAir==false) {
        //Generazione di un numero tra 0 e 100 in caso un barile sia sopra una scala.
        if ((xPos>=215 && xPos<=220 && yPos>240-50 && yPos<270) ||
          (xPos>=454 && xPos<=459 && yPos>244-50 && yPos<254) ||
          (xPos>=175 && xPos<=180 && yPos>318-50 && yPos<338) ||
          (xPos>=414 && xPos<=419 && yPos>306-50 && yPos<355) ||
          (xPos>=454 && xPos<=459 && yPos>402-50 && yPos<415) ||
          (xPos>=275 && xPos<=280 && yPos>394-50 && yPos<425) ||
          (xPos>=155 && xPos<=160 && yPos>388-50 && yPos<430) ||
          (xPos>=235 && xPos<=240 && yPos>472-50 && yPos<500) ||
          (xPos>=454 && xPos<=459 && yPos>562-50 && yPos<580)) {
          if (cadutaBarile==false) {
            fallChance=random(0, 100);
          }
          //Settaggio dei parametri che permettono la discesa del barile sula scala in caso il numero generato sia maggiore o uguale a 90.
          if (fallChance>=90) {
            cadutaBarile=true;
            speed=0;
          }
        }

        //Determinazione del senso di marcia di un barile.
        if ((yPos>220 && yPos<300) || (yPos>400 && yPos<480) || yPos>560) Rimbalzi=1;
        else Rimbalzi=0;
        
        
        //Movimento dei barili.
        if (Rimbalzi==0)
          xPos = xPos + speed;
        else if (Rimbalzi==1)
          xPos = xPos - speed;
        //Animazione dei barili.
        TimingBarile++;
        if (TimingBarile==4) {
          TimingBarile=0;
          if (SpriteBarile==0)
            SpriteBarile=24;
          else if (SpriteBarile==24)
            SpriteBarile=48;
          else if (SpriteBarile==48)
            SpriteBarile=72;
          else 
          SpriteBarile=0;
        }
        //Discesa del barile sula scala in caso il numero generato sia maggiore o uguale a 90.
        if (get(xPos+15, yPos+25)!=color(255, 35, 91) || cadutaBarile)
          yPos++;
        //Arresto della discesa del barile.
        if (get(xPos+15, yPos+25)==color(255, 35, 91) && cadutaBarile) {

          cadutaBarile=false;
          speed=3;
        }
      }
      //Rinascita dei barili.
      if (xPos<-30 && yPos>580) {
        active=false;
        set=false;
        bariliLanciati--;
      }
    }
  }
}
static final int MOV_SPEED=50; //Velocità di Mario.
static final float GRAVITY=0.25f; //Gravità.
static final int BARREL_RADIUS=15; //Raggio dell'area di collisione dei barili.
static final int MARIO_RADIUS=20; //Raggio dell'area di collisione di Mario.
int dir=0, anim=0, animScale=1; //Animazioni Mario.
int marioX=100, marioY=580, lastMov; //Coordinate Mario.
boolean movRight, movLeft, jump=false; //Movimenti di Mario.
boolean scala=false; //Presenza di una scala.
float jumpV; //Velocità di mario durante un salto.
boolean playing=false, gameOver=false, reset=false, win=false; //Stati del gioco.
boolean climbing=false, climbing2=false; //Movimento in corso di Mario verso l'alto o verso il basso.
int deathTime; //Tempo dell'animazione della morte di Mario.
int morte=0, giri=0; //Animazioni della morte di Mario.

public void mario() {
  if (playing) {
    //Verifica delle collisioni di Mario.
    CollisioniScale();
    collisioneBarili();
    //Verifica della vincita.
    checkWin();
    //
    if (climbing==false && climbing2==false) {
      if (scala==true){
        //Animazioni di Mario sulla scala.
        if(get(marioX+20, marioY+39)!=color(255, 35, 91) || get(marioX+20, marioY+40)!=color(255, 35, 91) || get(marioX+20, marioY+41)!=color(255, 35, 91))
        image(marioScale[animScale], marioX, marioY, 40, 40);
        else
        image(marioMov[anim], marioX, marioY, 40, 40);
      }else
        image(marioMov[anim], marioX, marioY, 40, 40);
      //Movimenti e animazioni di Mario.
      if (jump==false && climbing==false && climbing2==false) {
        if (keyPressed) {         
          if (movRight && marioX<520 && climbing==false && climbing2==false)
            while (millis()-lastMov>MOV_SPEED) {
              lastMov=millis();
              if (anim==0) anim=1;
              else anim=0;
              marioX+=5;
            } else if (dir==0) anim=0;
          else if (movLeft && marioX>0 && climbing==false && climbing2==false) 
            while (millis()-lastMov>MOV_SPEED) {
              lastMov=millis();
              if (anim==3) anim=4;
              else anim=3;
              marioX-=5;
            } else if (dir==1) anim=3;
        } else if (dir==0) anim=0;
        else if (dir==1) anim=3;
        
        //Salita e discesa delle travi in presenza di dislivelli.
        if ((get(marioX+32, marioY+40)==color(0, 0, 0) && get(marioX+10, marioY+40)==color(0, 0, 0)) && scala==false) marioY++;
        else if ((marioX+32>=477 && marioX+32<=483) && (marioY>564)) marioY-=2;
        else if ((((get(marioX+32, marioY+40)!=color(255, 35, 91) && get(marioX+32, marioY+42)!=color(255, 35, 91)) || ((get(marioX+10, marioY+40)!=color(255, 35, 91) && get(marioX+10, marioY+42)!=color(255, 35, 91)))) && scala==false) && ((marioX+10<445 || marioX+32>452) && marioY!=566)) marioY--;
        else if ((((get(marioX+32, marioY+40)==color(0, 0, 0) && get(marioX+10, marioY+40)==color(0, 0, 0)) || (get(marioX+32, marioY+40)==color(0, 255, 255) && get(marioX+10, marioY+40)==color(0, 255, 255)) || (get(marioX+32, marioY+40)==color(0, 255, 255) && get(marioX+10, marioY+40)==color(0, 0, 0)) || (get(marioX+32, marioY+40)==color(0, 0, 0) && get(marioX+10, marioY+40)==color(0, 255, 255))) && (get(marioX+32, marioY+42)==color(255, 35, 91) && get(marioX+10, marioY+42)==color(255, 35, 91))) && scala==true && climbing==false && climbing2==false) marioY++;
      } else if (climbing==false) {
        //Collisioni all'atteraggio di Mario dopo un salto.
        for (int pixel=0; pixel<=abs(jumpV); pixel++) {
          if (jumpV<0)
            if ((get(marioX+32, marioY+40+pixel)==color(255, 35, 91)) || (get(marioX+10, marioY+40+pixel)==color(255, 35, 91))) {
              marioY--;
              jump=false;
            }
        }
        //Movimento verso destra di Mario con corrispettiva animazione.
        if (movRight) marioX+=1;
        //Movimento verso destra di Mario con corrispettiva animazione.
        else if (movLeft) marioX-=1;
        if (dir==0)
          anim=2;
        else if (dir==1)
          anim=5;
        //Salto di Mario.
        marioY-=jumpV;
        jumpV-=GRAVITY;
      }
    } else {
      //Movimento e animazione di Mario quando sale una scala.
      if (climbing) {
        image(marioScale[animScale], marioX, marioY, 40, 40);
        if (millis()-lastMov>CLIMBING_SPEED) {
          lastMov=millis();
          if (animScale==1) animScale++;
          else animScale=1;
          marioY-=6;
        }
      }
    }
    //Movimento e animazione di Mario quando scende una scala.
    if (climbing2) {
      image(marioScale[animScale], marioX, marioY, 40, 40);
      if (millis()-lastMov>CLIMBING_SPEED) {
        lastMov=millis();
        if (animScale==1) animScale++;
        else animScale=1;
        marioY+=6;
      }
    }
  } else if (gameOver) {
    if (millis()-deathTime>CLIMBING_SPEED) {
      deathTime=millis();
      if (giri<2) {
        if (morte<4) morte++;
        if (morte==3) {
          morte=0;
          giri++;
        }
      } else {
        morte=4;
      }
    }
    image(marioDeath[morte], marioX, marioY, 40, 40);
    if (morte==4) {
      delay(1000);
      reset=true;
    }
  }
}

public void checkWin() {
  if (marioY==110) {
    win=true;
    delay(2000);
  }
}


public void CollisioniScale() {
  //Collisioni di Mario quando sta scendendo dalla scala
  if (marioX+20>200 && marioX+20<220 && marioY>600-40 && marioY>620-40+6) marioY=580;
  else if (marioX+20>459 && marioX+20 <479 && marioY>610-40 && marioY<610-40+6) marioY=610-40;
  else if (marioX+20>240 && marioX+20 <260 && marioY>534-40 && marioY<534-40+6) marioY=534-40;
  else if (marioX+20>80 && marioX+20 <100 && marioY>526-40 && marioY<526-40+6) marioY=526-40;
  else if (marioX+20>160 && marioX+20 <180 && marioY>472-40 && marioY<472-40+6) marioY=472-40;
  else if (marioX+20>280 && marioX+20 <300 && marioY>466-40 && marioY<466-40+6) marioY=466-40;
  else if (marioX+20>459 && marioX+20 <479 && marioY>458-40 && marioY<458-40+6) marioY=458-40;
  else if (marioX+20>419 && marioX+20 <439 && marioY>388-40 && marioY<388-40+6) marioY=388-40;
  else if (marioX+20>180 && marioX+20 <200 && marioY>384-40 && marioY<384-40+6) marioY=384-40;
  else if (marioX+20>80 && marioX+20 <100 && marioY>316-40 && marioY<316-40+6) marioY=316-40;
  else if (marioX+20>220 && marioX+20 <240 && marioY>304-40 && marioY<304-40+6) marioY=304-40;
  else if (marioX+20>459 && marioX+20 <479 && marioY>240-40 && marioY<240-40+6) marioY=240-40;
  else if (marioX+20>419 && marioX+20 <439 && marioY>400-40 && marioY<400-40+6) marioY=400-40;

  //Collisioni di Mario quando è sulla scala
  if (marioX+20>200 && marioX+20<220 && marioY>600-61 && marioY<621-40) scala=true;    
  else if (marioX+20>459 && marioX+20 <479 && marioY>562-61 && marioY<610-40) scala=true;
  else if (marioX+20>240 && marioX+20 <260 && marioY>474-61 && marioY<534-40) scala=true;
  else if (marioX+20>80 && marioX+20 <100 && marioY>482-61 && marioY<526-40)  scala=true;
  else if (marioX+20>160 && marioX+20 <180 && marioY>438-61 && marioY<472-40) scala=true;
  else if (marioX+20>280 && marioX+20 <300 && marioY>394-61 && marioY<466-40) scala=true;
  else if (marioX+20>459 && marioX+20 <479 && marioY>402-61 && marioY<458-40) scala=true;
  else if (marioX+20>419 && marioX+20 <439 && marioY>360-61 && marioY<400-40) scala=true;
  else if (marioX+20>180 && marioX+20 <200 && marioY>318-61 && marioY<388-40) scala=true;
  else if (marioX+20>80 && marioX+20 <100 && marioY>322-61 && marioY<384-40)  scala=true;
  else if (marioX+20>220 && marioX+20 <240 && marioY>263-61 && marioY<316-40) scala=true;
  else if (marioX+20>459 && marioX+20 <479 && marioY>244-61 && marioY<304-40) scala=true;
  else if (marioX+20>320 && marioX+20 <340 && marioY>170-61 && marioY<240-40) scala=true;
  else {
    scala=false; 
    climbing=false; 
    climbing2=false;
  }
}



public void collisioneBarili() {
  for (int i=0; i<NUMERO_BARILI; i++) {
    if (dist(marioX+20, marioY+20, barile[i].xPos+15, barile[i].yPos+15)<MARIO_RADIUS+BARREL_RADIUS || marioY>640) {
      gameOver=true;
      playing=false;
      deathTime=millis();
    }
  }
}


public void keyPressed() {
  if (keyCode==RIGHT) {
    dir=0;
    movRight=true;
  }
  if (keyCode==LEFT) {
    dir=1;
    movLeft=true;
  }
  if (key==32 && jump==false && scala==false) {
    jump=true;
    jumpV=4;
  }
  if (keyCode==UP && scala==true && jump==false) {
    if (climbing==false)
      lastMov=millis();
    climbing=true;
  }
  if (keyCode==DOWN && scala==true && jump==false) {
    if (climbing2==false)
      lastMov=millis();
    climbing2=true;
  }
}

public void keyReleased() {
  if (keyCode==RIGHT) movRight=false;
  if (keyCode==LEFT) movLeft=false;
  if (keyCode==UP) climbing=false;
  if (keyCode==DOWN) climbing2=false;
}
//Funzione per il disegno delle travi in base alla situazione del gioco
public void travi() {
  //Disegno delle travi nello stato iniziale del gioco fino alla loro inclinazione
  if (6>mapState) {
    for ( riga=0; riga<MAP_HEIGHT; riga++)
    {
      if (mapState >= 1 && riga==11)
        riga++;
      if (mapState >= 2 && riga==14)
        riga++;  
      if (mapState >= 3 && riga==18)
        riga++;
      if (mapState >= 4 && riga==22)
        riga++;
      if (mapState >= 5 && riga==26)
        riga++;


      if (riga==11 || riga==18 || riga==26) {
        n=1;
        m=0;
      } else if (riga==14 || riga==22) {
        n=1;
        m=40;
      } else {
        n=0;
        m=0;
      }

      for (int colonna=0; colonna<MAP_WIDTH-n; colonna++)
      {
        image(tileset[maze[riga][colonna]], colonna*40+m, riga*20, 40, 20);
      }
    }
  }
  
  //Piegamento della prima trave
  if (mapState>=1) {
    traveInc=-18;
    riga=11;
    for (int colonna=0; colonna<MAP_WIDTH-1; colonna++)
    {
      if (colonna<9)
        image(tileset[maze[riga][colonna]], colonna*40, riga*20, 40, 20);
      else
        image(tileset[maze[riga][colonna]], colonna*40, riga*20+traveInc, 40, 20);
      traveInc+=2;
    }
  }
  
  //Piegamento della seconda trave
  if (mapState>=2) {
    traveInc=0;
    riga=14;
    for (int colonna=MAP_WIDTH-1; colonna>0; colonna--)
    {
      image(tileset[maze[riga][colonna]], colonna*40, riga*20+traveInc, 40, 20);
      traveInc+=2;
    }
  }

  //Piegamento della terza trave
  if (mapState>=3) {
    traveInc=0;
    riga=18;
    for (int colonna=0; colonna<MAP_WIDTH-1; colonna++)
    {
      image(tileset[maze[riga][colonna]], colonna*40, riga*20+traveInc, 40, 20);
      traveInc+=2;
    }
  }

  //Piegamento della quarta trave
  if (mapState>=4) {
    traveInc=0;
    riga=22;
    for (int colonna=MAP_WIDTH-1; colonna>0; colonna--)
    {
      image(tileset[maze[riga][colonna]], colonna*40, riga*20+traveInc, 40, 20);
      traveInc+=2;
    }
  }
  
  //Piegamento della quinta trave
  if (mapState>=5) {
    traveInc=0;
    riga=26;
    for (int colonna=0; colonna<MAP_WIDTH-1; colonna++)
    {
      image(tileset[maze[riga][colonna]], colonna*40, riga*20+traveInc, 40, 20);
      traveInc+=2;
    }
  }
  
  //Piegamento della sesta trave
  if (mapState>=6) {
    traveInc=0;
    riga=30;
    for (int colonna=MAP_WIDTH-1; colonna>-1; colonna--)
    {
      if (colonna<7)
        image(tileset[maze[riga][colonna]], colonna*40, riga*20+20, 40, 20);
      else 
      image(tileset[maze[riga][colonna]], colonna*40, riga*20+traveInc, 40, 20);
      traveInc+=3;
    }
  }
}
  public void settings() {  size(560, 640); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "DonkeyKong" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
