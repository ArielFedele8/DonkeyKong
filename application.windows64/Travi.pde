//Funzione per il disegno delle travi in base alla situazione del gioco
void travi() {
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
