#include <stdlib.h>
//#include <Base64.h>

#include <QueueList.h>

#include <Arduino.h>
#include "ApplicationMonitor.h"

Watchdog::CApplicationMonitor ApplicationMonitor;




//#include <U8glib.h>

#include <TimedAction.h>

#include <Adafruit_NeoPixel.h>
#ifdef __AVR__
#include <avr/power.h>
#endif

//Pino de dados da matriz de led
#define PIN 7

//Parametros: numero de leds, pino de ligacao
Adafruit_NeoPixel strip = Adafruit_NeoPixel(64, PIN, NEO_GRB + NEO_KHZ800);

//U8GLIB_SSD1306_128X64 u8g(U8G_I2C_OPT_NO_ACK);

int tempo = 0;

String status;
String welcome = "Raro Labs;Controlador Arduino;1.0.4";


unsigned char const TM_RETORNO = 0x0;
unsigned char const TM_INICIO  = 0x1;
unsigned char const TM_ESTAGIO = 0x2;
unsigned char const TM_DETECTOR = 0x3;
unsigned char const TM_FALHA_ANEL = 0x4;
unsigned char const TM_FALHA_DETECTOR = 0x5;
unsigned char const TM_FALHA_GRUPO_SEMAFORICO = 0x6;
unsigned char const TM_FALHA_GENERICA = 0x7;
unsigned char const TM_ALARME = 0x8;
unsigned char const TM_REMOCAO_FALHA = 0x9;
unsigned char const TM_INSERCAO_PLUG = 0xA;
unsigned char const TM_REMOCAO_PLUG = 0xB;
unsigned char const TM_TROCA_ESTAGIO_MANUAL = 0xC;
unsigned char const TM_MODO_MANUAL_ATIVADO = 0xD;
unsigned char const TM_MODO_MANUAL_DESATIVADO = 0xE;
unsigned char const TM_INFO = 0xF; 

unsigned char const RET_OK = 0x0;
unsigned char const RET_INVALID_CHECKSUM = 0x1;
unsigned char const RET_INVALID_MSG_TYPE = 0x2;
unsigned char const RET_INVALID_SIZE = 0x3;

unsigned char const DESLIGADO = 0;
unsigned char const VERDE = 1;
unsigned char const VERMELHO = 2;
unsigned char const AMARELO = 3;
unsigned char const VERMELHO_INTERMITENTE = 4;
unsigned char const AMARELO_INTERMITENTE = 5;
unsigned char const VERMELHO_LIMPEZA = 6;

bool sendState = false;

unsigned int const CHAVE_MANUAL = 5;
unsigned int const LED_MANUAL = 4;
QueueList <String> queue;

// countdown until the program locks up. 
int g_nEndOfTheWorld = 15; 

// number of iterations completed. 
int g_nIterations = 0; 

int chaveManual = -1;
bool estadoManual = false;

String inputString;

typedef union status {
  struct {
    unsigned int state: 3;
    unsigned int pedestrian : 1;
  };

  int intRepresentation;
} bitset;

int colors[16];


long int times[16][5] = {-1}; //Flag in the first byte

int whereIsTime(int line) {
  if (times[line][1] >= 100) {
    return 1;
  } else if (times[line][2] >= 100) {
    return 2;
  } else if (times[line][3] >= 100) {
    return 3;
  } else if (times[line][4] >= 100) {
    return 4;
  }
  return -1;
}



void dropTime(int line,int wit) {
  times[line][wit] -= 100;
}

int getColorAndDecrement(int line) {
  if (times[line][0] == -1) {
    return DESLIGADO;
  }
  int wit = whereIsTime(line);
  int ret = DESLIGADO;
  bitset status;
  status.intRepresentation = times[line][0];
  switch (wit) {
    case -1:
      return status.pedestrian ? DESLIGADO : AMARELO_INTERMITENTE;
    case 1: //Atraso de grupo ou vermelho
      ret = times[line][2] > 0 ? VERDE : VERMELHO;
      break;
    case 2:
      if (status.state == 4) {
        ret = status.pedestrian ? DESLIGADO : AMARELO_INTERMITENTE;
      } else {
        ret = status.pedestrian ? VERMELHO_INTERMITENTE : AMARELO;
      }
      break;
    case 3:
      if (status.state == 4) {
        ret = VERMELHO;
      } else {
        ret = VERMELHO_LIMPEZA;  
      }
      break;
    case 4:
      switch (status.state) {
        case 0:
          ret = DESLIGADO;
          break;
        case 1:
          ret = VERDE;
          break;
        case 2:
          ret = VERMELHO;
          break;
        case 3:
          ret = AMARELO_INTERMITENTE;
          break;
        case 4:
          ret = VERDE;
          break;
      }
      break;
  }
  dropTime(line,wit);
 
  return ret;
}

void run(int * colors) {
  for (int i = 0; i < 16; i++) {
      colors[i] = getColorAndDecrement(i);
  }
}

void onReceiveEstage(unsigned char *msg, int msgSize) {
  int groupsSize = msg[4] & 0x1F;
  int index = 5;

  for (int i = 0; i < groupsSize; i++) {
    bitset status;
    int group = msg[index + 1];
    status.intRepresentation = msg[index];

    times[group - 1][0] = status.intRepresentation;

    index += 2;
    
    for(int j = 1; j < 5; j++) {
      long primeiro = (long) msg[index++] << 16;
      long segundo  = (long) msg[index++] << 8;
      long terceiro = (long) msg[index++];      
      times[group - 1][j] = primeiro + segundo + terceiro;
    }
    
  }

}

 
byte byteRead;

void setup() {
   pinMode(LED_MANUAL,OUTPUT);
   pinMode(CHAVE_MANUAL,INPUT);

  for (int i = 22; i <= 50; i += 2) {
    pinMode(i, INPUT);
  }

  strip.begin();
  strip.show();
  //Define o brilho dos leds
  strip.setBrightness(10);
  Serial.begin(9600);
  Serial.setTimeout(30000);

  for(int i = 0; i < 16; i++) {
    for(int j = 0; j < 5; j++) {
      times[i][j] = -1;  
    }
  }
  
  ApplicationMonitor.EnableWatchdog(Watchdog::CApplicationMonitor::Timeout_4s);
}

int calculateLRC(unsigned char *msg, int size)
{
  byte LRC = 0;
  for (int i = 0; i < size; i++)
  {
    LRC ^= msg[i];
  }
  return LRC;
}

void printHex(int num, int precision) {
     char tmp[16];
     char format[128];

     sprintf(format, "%%.%dX", precision);

     sprintf(tmp, format, num);
     Serial.print(tmp);
}

void sendBytes(unsigned char *msg, int size) {
  Serial.print("<I>");
  for(int i =0; i< size; i++){
    printHex(msg[i],2);
  }
  Serial.print("<F>");
}

void response(unsigned char returnCode, byte seq1, byte seq2) {
  byte msg[6];
  msg[0] = 0x6;
  msg[1] = TM_RETORNO;
  msg[2] = seq1;
  msg[3] = seq2;
  msg[4] = returnCode;
  msg[5] = calculateLRC(msg, 4);
  sendBytes(msg, 6);
}

byte checkMsgType(unsigned char *msg, byte msgSize) {
  if (msgSize < 2) {
    return RET_INVALID_SIZE;
  }
  byte tm = msg[1];
  switch (tm) {
    case TM_INICIO:
      sendInfo();
      return RET_OK;
    case TM_RETORNO:
      return RET_OK;
    case TM_ESTAGIO:
      onReceiveEstage(msg, msgSize);
      sendState = true;
      return RET_OK;
    case TM_MODO_MANUAL_ATIVADO:
      digitalWrite(LED_MANUAL,true);
      estadoManual = true;
      return RET_OK;
      
    case TM_MODO_MANUAL_DESATIVADO:
      digitalWrite(LED_MANUAL,false);
      estadoManual = false;
      return RET_OK;

    default:
      return RET_INVALID_MSG_TYPE;
  }
}



void process(unsigned char *msg, byte msgSize) {
  if (calculateLRC(msg, msgSize - 1) == msg[msgSize - 1]) {
    response(checkMsgType(msg, msgSize), msg[2], msg[3]);
  } else {
    response(RET_INVALID_CHECKSUM, msg[2], msg[3]);
  }

}

void lights() {
  if (sendState) {
    run(colors);

    uint32_t newColor;

    for (int i = 0; i < 16; i++) {
      int color = colors[i];
      switch (color) {
        case VERDE:
          newColor = 0x00FF00;
          break;
        case VERMELHO:
        case VERMELHO_LIMPEZA:
          newColor = 0xFF0000;
          break;
        case AMARELO:
          newColor = 0xFFFF00;
          break;
        case AMARELO_INTERMITENTE:

          if (strip.getPixelColor(i) == 0x0) {
            newColor = 0xFFFF00;
          } else {
            newColor = 0x0;
          }

          break;
        case VERMELHO_INTERMITENTE:
          if (strip.getPixelColor(i) == 0x0) {
            newColor = 0xFF0000;
          } else {
            newColor = 0x0;
          }
          break;
        default:
          newColor = 0x0;
      }
      strip.setPixelColor(i, newColor);
    }
    strip.show();
    tempo += 100;
  }

}

TimedAction lightsAction = TimedAction(100, lights);

void sendInfo() {
  int size = welcome.length() + 6;
  
  byte msg[size];
  msg[0] = 0x6;
  msg[1] = TM_INFO;
  msg[2] = 0x0;
  msg[3] = 0x0;

  char * pointer  = (char *)&msg[4];
  welcome.toCharArray(pointer,welcome.length() + 1);
  msg[size - 1] = calculateLRC(msg, size);
  sendBytes(msg, size);

}

void sendDetector(bool pedestre, int codigo) {
  byte msg[6];
  msg[0] = 0x6;
  msg[1] = TM_DETECTOR;
  msg[2] = 0x0;
  msg[3] = 0x0;
  msg[4] = (pedestre << 5) | codigo ;
  msg[5] = calculateLRC(msg, 4);
  sendBytes(msg, 6);
}
void sendFalhaComParametro(unsigned char const tipo, int param, int codigo) {
  byte msg[7];
  msg[0] = 0x7;
  msg[1] = tipo;
  msg[2] = 0x0;
  msg[3] = 0x0;
  msg[4] = param;
  msg[5] = codigo;
  msg[6] = calculateLRC(msg, 4);
  sendBytes(msg, 7);
}

void sendManual(unsigned char const tipo) {
  byte msg[5];
  msg[0] = 0x5;
  msg[1] = tipo;
  msg[2] = 0x0;
  msg[3] = 0x0;
  msg[4] = calculateLRC(msg, 4);
  sendBytes(msg, 5);
}



void sendFalhaAnel(int anel, int codigo) {
  sendFalhaComParametro(TM_FALHA_ANEL, anel, codigo);
}
void sendFalhaGrupoSemaforico(int grupo, int codigo) {
  sendFalhaComParametro(TM_FALHA_GRUPO_SEMAFORICO, grupo, codigo);
}
void sendFalhaDetector(bool pedestre,int detector, int codigo) {
  int detectorComFlag = (pedestre ? 1 : 0) << 5;
  detectorComFlag |= detector;
  sendFalhaComParametro(TM_FALHA_DETECTOR, detectorComFlag, codigo);
}
void sendRemocaoFalha(int anel, int codigo) {
  sendFalhaComParametro(TM_REMOCAO_FALHA, anel, codigo);
}
void sendFalhaAlarme(int tipo, int codigo) {
  byte msg[6];
  msg[0] = 0x6;
  msg[1] = tipo;
  msg[2] = 0x0;
  msg[3] = 0x0;
  msg[4] = codigo ;
  msg[5] = calculateLRC(msg, 4);
  sendBytes(msg, 6);
}

void plug(int estado){
  if(estado){
    sendManual(TM_INSERCAO_PLUG);
  }else{
    sendManual(TM_REMOCAO_PLUG);
  }
}

void parseHex(unsigned char* out, String in, int size){
  for(int i =0, j=0; i < size; i+=2,j++){
    String hex = in.substring(i,i+2);
    out[j] = (unsigned char) strtol(&hex[0], NULL, 16);
  }
}

void serialEvent() {
  while (Serial.available()) {
    // get the new byte:
    char inChar = (char)Serial.read();
    // add it to the inputString:
    inputString += inChar;
    // if the incoming character is a newline, set a flag
    // so the main loop can do something about it:
    if(inputString.endsWith("<F>")){
      int inicio = inputString.lastIndexOf("<I>");
      if(inicio >= 0){
        queue.push (inputString.substring(inicio+3,inputString.length() -3));  
        inputString = "";
      }
    }
  }
  
}

void processaString(){
   while (!queue.isEmpty()){
    
     String incommingString = queue.pop();
     int size = incommingString.length() / 2;
     unsigned char msg[size];
     parseHex(msg, incommingString, incommingString.length());
     process(msg, msg[0]);
   }
}

void loop() {
  ApplicationMonitor.IAmAlive();
  ApplicationMonitor.SetData(g_nIterations++);

  processaString();   
  
  
  lightsAction.check();

  int estadoChaveManual = digitalRead(CHAVE_MANUAL);
  if(chaveManual != estadoChaveManual){
    if(chaveManual == -1){
      chaveManual = estadoChaveManual;
      if(chaveManual == 1){
        plug(chaveManual);
      }
    }else{
      chaveManual = estadoChaveManual;
      plug(chaveManual); 
    }
  }
  
  
  for (int i = 22; i <= 50; i += 2) {
    int buttonState =  digitalRead(i);
    if (buttonState) {
      switch (i) {
        case 22:
          sendDetector(true, 1);
          break;
        case 24:
          sendDetector(false, 1);
          break;
        case 26:
          sendFalhaAnel(1, 8);
          break;
        case 28:
          sendFalhaGrupoSemaforico(1, 6);
          break;
        case 30:
          sendRemocaoFalha(1, 3);
          break;
        case 32:
          sendFalhaDetector(true,1, 2);
          break;
        case 34:
          sendFalhaAlarme(TM_FALHA_GENERICA, 9);
          break;
        case 36:
          sendFalhaAlarme(TM_ALARME, 1);
          break;
        case 38:
          if(estadoManual){
            sendManual(TM_TROCA_ESTAGIO_MANUAL);
          }
          break;

      }
    }
 }

--g_nEndOfTheWorld;
}

