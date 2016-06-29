#include <Utility.h>
#include <TimedAction.h>
uint8_t const BLINK = 22;

uint8_t g1_verde = LOW, g1_vermelho = LOW, g1_amarelo = LOW,
        g2_verde = LOW, g2_vermelho = LOW, g2_amarelo = LOW,
        g3_verde = LOW, g3_vermelho = LOW;

uint8_t g1_verde_pin = 13, g1_amarelo_pin = 12, g1_vermelho_pin = 11,
        g2_verde_pin = 10, g2_amarelo_pin = 9, g2_vermelho_pin = 8,
        g3_verde_pin = 7 , g3_vermelho_pin = 6, detector1 = 3;

int buttonState = 0;

void checkSerial(){
  if (Serial.available() > 0 ) {
    String serialData = Serial.readString();
    int g3 = getValue(serialData, ',', 2).toInt();
    int g1 = getValue(serialData, ',', 0).toInt();
    int g2 = getValue(serialData, ',', 1).toInt();
    
    grupo(g1_verde,g1_amarelo,g1_vermelho,g1);
    grupo(g2_verde,g2_amarelo,g2_vermelho,g2);
    grupoPedestre(g3_verde,g3_vermelho,g3);
  }
}
void updateLeds(){
  digitalWrite(g1_verde_pin,g1_verde);
  digitalWrite(g1_amarelo_pin,g1_amarelo);
  digitalWrite(g1_vermelho_pin,g1_vermelho);
  digitalWrite(g2_verde_pin,g2_verde);
  digitalWrite(g2_amarelo_pin,g2_amarelo);
  digitalWrite(g2_vermelho_pin,g2_vermelho);
  digitalWrite(g3_verde_pin,g3_verde);   
  if(g3_vermelho == BLINK){
    delay(150);
    digitalWrite(g3_vermelho_pin,LOW);   
    delay(100);
    digitalWrite(g3_vermelho_pin,HIGH);   
  }else{
    digitalWrite(g3_vermelho_pin,g3_vermelho);   
  }
}

TimedAction serialThread = TimedAction(100,checkSerial);
TimedAction updateLedsThread = TimedAction(50,updateLeds);




int dado; //variável que receberá os dados da porta serial
int i = 0;
bool blinkG3 = false;

void setup(){
  Serial.begin(115200);
  pinMode(g1_verde_pin,OUTPUT);
  pinMode(g1_amarelo_pin,OUTPUT);
  pinMode(g1_vermelho_pin,OUTPUT);
  pinMode(g2_verde_pin,OUTPUT);
  pinMode(g2_amarelo_pin,OUTPUT);
  pinMode(g2_vermelho_pin,OUTPUT);
  pinMode(g3_verde_pin,OUTPUT);  
  pinMode(g3_vermelho_pin,OUTPUT); 
  pinMode(detector1, INPUT); 
   while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }
}
 
void loop(){
   buttonState = digitalRead(detector1);
  if(buttonState == HIGH){
    Serial.println("BOTAO1");
  }
  updateLedsThread.check();
  serialThread.check();
  updateLedsThread.check();
  
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
 void grupo(uint8_t &verde, uint8_t &amarelo, uint8_t &vermelho, int state){
    
    switch(state){
        case 1:
           verde = HIGH;
           amarelo = LOW;
           vermelho = LOW;           
           break;
        case 2:
           verde = LOW;
           amarelo = HIGH;
           vermelho = LOW;           
           break;
        case 3:
           verde = LOW;
           amarelo = LOW;
           vermelho = HIGH;           
           break;
      }
}

 void grupoPedestre(uint8_t &verde, uint8_t &vermelho, int state){
    switch(state){
        case 1:
           verde = HIGH;
           vermelho = LOW;
           break;
        case 3:
           verde = LOW;
           vermelho = HIGH;
           break;
        case 4:
           verde = LOW;
           vermelho = BLINK;
           break;
      }
}
