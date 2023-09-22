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
            fallV=-fallV*0.20;
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
