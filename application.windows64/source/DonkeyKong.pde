static final int MAP_WIDTH=14; //Larghezza mappa
static final int MAP_HEIGHT=32; //Altezza mappa
static final int NUMERO_BARILI=15; //Numero dei barili
static final float JUMP_ACCEL_DONKEY=0.215; //Accelerazione durante il salto di Donkey
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


void setup() {
  frameRate(60);
  size(560, 640);
  
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


void draw() {
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
void DonkeyBarili() {
  
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
void Scale() {
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
void scalePercorso() {
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
void DonkeyKongSalita() {
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
void DonkeyKongSalto() {
  if (DonkeyY<131 && stopSalti==false && DonkeyX>60) {
    DonkeyY-=jSpeed;  
    DonkeyX-=Vx;
    if (j==0.25 && DonkeyY>64)
      DonkeyX-=2;
    if (j==0.25 && DonkeyY<=64) {
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
    if (j==0.50)
      j=0;
    if (j!=0.25 && j!=0.375)
      j+=0.25;
    else
      j+=0.125;
  }
}

//Disegno delle travi su cui sta in piedi Peach
void BasePeach() {
  image(tileset[1], 5*40+20, 8*20-10, 40, 20);
  image(tileset[1], 6*40+20, 8*20-10, 40, 20);
  image(tileset[1], 7*40+20, 8*20-10, 40, 20);
}

//Aggiornamento delle variabili
void updateState() {
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
void BarilePetrolio() {
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
void peach(){
  image(peach[p],220,95,37,55);
  if(millis()-peachLast>peachAnim){
    peachLast=millis();
    if(p==0) p++;
    else p--;
  }
}
