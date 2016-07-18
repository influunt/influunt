#include <TimedAction.h>
#include <Adafruit_NeoPixel.h>
#ifdef __AVR__
#include <avr/power.h>
#endif

#include "U8glib.h"
#define PIN 7

U8GLIB_SSD1306_128X64 u8g(U8G_I2C_OPT_NO_ACK);  // Display which does not send AC
Adafruit_NeoPixel strip=Adafruit_NeoPixel(24,PIN);

String trap;


uint32_t DESLIGADO = 0;
uint32_t VERDE = 1;
uint32_t AMARELO = 2;
uint32_t VERMELHO = 3;
uint32_t VERMELHO_INTERMITENTE = 4;
uint32_t AMARELO_INTERMITENTE  = 5;
uint32_t AZUL = 6;
uint32_t BRANCO = 7;
uint32_t grupos[24];
bool change = true;
bool first = true;
int quantidadeGrupos = 0;
int count = 0;
void display(){
  count--;
}

TimedAction displayThread = TimedAction(1000,display);

void atualizaLeds(){
    
   for(uint16_t i=0; i< strip.numPixels(); i++) {
    
    if(grupos[i] == DESLIGADO){
        strip.setPixelColor(i,0,0,0);
    }else if(grupos[i] == VERDE){
      strip.setPixelColor(i,0,255,0);
    }else if(grupos[i] == VERMELHO){
      strip.setPixelColor(i,255,0,0);
    }else if(grupos[i] == AMARELO){
      strip.setPixelColor(i,255,255,0);
    }else if(grupos[i] == VERMELHO_INTERMITENTE){
      strip.setPixelColor(i,255,0,0);
    }else if(grupos[i] == AMARELO_INTERMITENTE){
      strip.setPixelColor(i,255,255,0);
    }else if(grupos[i] == AZUL){
      strip.setPixelColor(i,0,0,255);
    }else if(grupos[i] == BRANCO){
      strip.setPixelColor(i,255,255,255);
    }

  }
  strip.show();
  change = false;
}

void draw() 
{
  u8g.setFont(u8g_font_fub30);
  
  enum {BufSize=9}; // If a is short use a smaller number, eg 5 or 6 
  char buf[BufSize];
  if(count >= 0){
    snprintf (buf, BufSize, "%d", count);  
    u8g.drawStr( 10, 57, buf);
  }else{
    u8g.drawStr( 10, 57, "--------");
  }
  

  
  
  //Texto - AM
  u8g.setFont(u8g_font_5x7);
  u8g.drawStr( 115, 33, "s");
  //moldura relogio
  u8g.drawRFrame(0,18, 128, 46, 4);

  //Seleciona a fonte de texto
  u8g.setFont(u8g_font_8x13B);
  //Linha superior - temperatura 
  if(trap.equals("")){
    if(first){
      u8g.drawStr( 5, 15, "Aguardando");
    }else{
      u8g.drawStr( 5, 15, "Conectado!");
    }
  }else{
    char sinal[sizeof(trap)];
    trap.toCharArray(sinal, sizeof(sinal));
    u8g.drawStr( 5, 15, sinal);
  }
}


void setGrupo(int grupo,int cor){
  int led_verde        = (4 * grupo) + 1;
  int led_amarelo      = (4 * grupo) + 2;
  int led_vermelho     = (4 * grupo) + 3;
  grupos[led_verde]    = DESLIGADO;
  grupos[led_amarelo]  = DESLIGADO; 
  grupos[led_vermelho] = DESLIGADO;

  if(cor == VERDE){
     grupos[led_verde] = VERDE;
  }else if(cor == AMARELO){
    grupos[led_amarelo] = AMARELO;
  }else if(cor == VERMELHO){
    grupos[led_vermelho] = VERMELHO;
  }else if(cor == VERMELHO_INTERMITENTE){
     grupos[led_verde]    = VERMELHO_INTERMITENTE;
     grupos[led_amarelo]  = VERMELHO_INTERMITENTE; 
     grupos[led_vermelho] = VERMELHO_INTERMITENTE;
  }else if(cor == AMARELO_INTERMITENTE){
     grupos[led_verde]    = AMARELO_INTERMITENTE;
     grupos[led_amarelo]  = AMARELO_INTERMITENTE; 
     grupos[led_vermelho] = AMARELO_INTERMITENTE;
  }
}

String getValue(String data, char separator, int index)
{
  int found = 0;
  int strIndex[] = {0, -1};
  int maxIndex = data.length()-1;

  for(int i=0; i<=maxIndex && found<=index; i++){
    if(data.charAt(i)==separator || i==maxIndex){
        found++;
        strIndex[0] = strIndex[1]+1;
        strIndex[1] = (i == maxIndex) ? i+1 : i;
    }
  }

  return found>index ? data.substring(strIndex[0], strIndex[1]) : "";
}

void colorWipe(uint32_t c, uint8_t wait) 
{
  for (uint16_t i = 0; i < strip.numPixels(); i++) {
    strip.setPixelColor(i, c);
    strip.show();
    delay(wait);
  }
}

void setup() {

for(int i = 22; i <= 52; i+=2){
  pinMode(i, INPUT);
}
  
  
  Serial.begin(115200);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }
  
  strip.begin();
  strip.setBrightness(50);
  for(int i =0; i < 25; i++){
    grupos[i] = DESLIGADO;
  }
  grupos[0] = BRANCO;
  grupos[4] = BRANCO;
  grupos[8] = BRANCO;
  grupos[12] = BRANCO;
  grupos[16] = BRANCO;
  grupos[20] = BRANCO;  

  atualizaLeds();

}

void loop() {
  if(first){
    colorWipe(strip.Color(0, 255, 0), 10);
    colorWipe(strip.Color(255, 255, 0), 10);
    colorWipe(strip.Color(255, 0, 0), 10);
  }
  trap = "";
  
  for(int i = 22; i <= 52; i+=2){
    if (digitalRead(i) == HIGH) {
      if(i >= 22 && i <= 28){
        trap = trap + "P" + ((i/2) - 11);
      }else if(i >= 30 && i <= 44){
        trap = trap +   "V" +  ((i/2) - 15);
      }else if(i >= 46 && i <= 48){
        trap = trap + "E" +  ((i/2) - 23);
      }else if(i == 50){
        trap = trap + "N0";
      }else if(i == 52){
        trap = trap + "M0";
      }
    }
  }

  if(!trap.equals("")){
    Serial.println(trap + ";");
//    String serialData = Serial.readStringUntil(';');
//    if(!serialData.startsWith("ACK;")){
//      //Não recebeu o evento!!!! O que fazer?
//    }
  }
   
  displayThread.check();
  if (Serial.available() > 0 ) {
    String serialData = Serial.readStringUntil(';');
    if(serialData.startsWith("START")){
      Serial.println("OK,Raro Labs           ,Simulador Arduino   ,1.A.B      ;"); 
      first = false;
      colorWipe(strip.Color(255, 0, 0), 10); 
    }else if(serialData.startsWith("G")){
      quantidadeGrupos = getValue(serialData, ',', 0).substring(1).toInt();
      for(int i = 1; i <= quantidadeGrupos; i++){
        int modo = getValue(serialData, ',', i).toInt();
        setGrupo(i-1,modo);
      }
      count = getValue(serialData, ',', quantidadeGrupos + 1).toInt();
      change = true;
      Serial.println("ACK;"); 
    }else if(serialData.startsWith("AI")){
      String serialData = Serial.readStringUntil(';');
      for(int i = 1; i <= quantidadeGrupos; i++){
        setGrupo(i-1,5);
      }
      change = true;
      Serial.println("ACK;"); 
    }
  }
  if(change){
    atualizaLeds();
  }
   u8g.firstPage();  
   do
   {
    displayThread.check();
    draw();
   } while( u8g.nextPage() );

}
