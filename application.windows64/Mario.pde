static final int MOV_SPEED=50; //Velocità di Mario.
static final float GRAVITY=0.25; //Gravità.
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

void mario() {
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

void checkWin() {
  if (marioY==110) {
    win=true;
    delay(2000);
  }
}


void CollisioniScale() {
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



void collisioneBarili() {
  for (int i=0; i<NUMERO_BARILI; i++) {
    if (dist(marioX+20, marioY+20, barile[i].xPos+15, barile[i].yPos+15)<MARIO_RADIUS+BARREL_RADIUS || marioY>640) {
      gameOver=true;
      playing=false;
      deathTime=millis();
    }
  }
}


void keyPressed() {
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

void keyReleased() {
  if (keyCode==RIGHT) movRight=false;
  if (keyCode==LEFT) movLeft=false;
  if (keyCode==UP) climbing=false;
  if (keyCode==DOWN) climbing2=false;
}
