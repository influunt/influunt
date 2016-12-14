#include <Base64.h>



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
int clock = 0;
String status;

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


int chaveManual = -1;
bool estadoManual = false;

typedef union status {
  struct {
    unsigned int group: 5;
    unsigned int state : 2;
    unsigned int pedestrian : 1;
  };

  int intRepresentation;
} bitset;

int colors[16];


int times[16][5] = {0}; //Flag in the first byte

//void draw()
//{
////  u8g.setFont(u8g_font_fub20);
//  enum {BufSize=20}; // If a is short use a smaller number, eg 5 or 6
//  char buf[BufSize];
////  snprintf (buf, BufSize, "%s", status);
//status.toCharArray(buf,20);
//  u8g.drawStr( 10, 57, buf);
//
//}

int whereIsTime(int line) {
  if (times[line][1] >= 100) {
    return 1;
  } else if (times[line][2] >= 100) {
    return 2;
  } else if (times[line][3] >= 100) {
    return 3;
  }

  return 4;
}



void dropTime(int line) {
  times[line][whereIsTime(line)] -= 100;
}

int getColorAndDecrement(int line) {
  int wit = whereIsTime(line);
  int ret = DESLIGADO;
  bitset status;
  status.intRepresentation = times[line][0];
  switch (wit) {
    case 1: //Atraso de grupo ou vermelho
      ret = times[line][2] > 0 ? VERDE : VERMELHO;
      break;
    case 2:
      ret = status.pedestrian ? VERMELHO_INTERMITENTE : AMARELO;
      break;
    case 3:
      ret = VERMELHO_LIMPEZA;
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
      }
      break;
  }
  dropTime(line);


  return ret;
}

void run(int * colors) {
  for (int i = 0; i < 16; i++) {
    if (times[i][0] > 0) {
      colors[i] = getColorAndDecrement(i);
    } else {
      colors[i] = DESLIGADO;
    }
  }
}

void onReceiveEstage(unsigned char *msg, int msgSize) {
  int groupsSize = msg[4] & 0x1F;
  int index = 5;

  for (int i = 0; i < groupsSize; i++) {
    bitset status;
    status.intRepresentation = msg[index];
    int group = status.group;

    times[group - 1][0] = status.intRepresentation;
    times[group - 1][1] = (msg[index + 1] << 8) | msg[index + 2];
    times[group - 1][2] = (msg[index + 3] << 8) | msg[index + 4];
    times[group - 1][3] = (msg[index + 5] << 8) | msg[index + 6];
    times[group - 1][4] = (msg[index + 7] << 8) | msg[index + 8];
    index += 9;
  }
}

/**************************************************************************************

 * ************************************************************************************/

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
  strip.setBrightness(50);
  Serial.begin(115200);
  Serial.setTimeout(3000);


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

void sendBytes(unsigned char *msg, int size) {
  Serial.write(msg,size);
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
    case TM_RETORNO:
    case TM_INICIO:
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

    //    clock+=100;
    //    u8g.firstPage();
    //    do{
    //      draw();
    //    } while( u8g.nextPage() );
  }

}

TimedAction lightsAction = TimedAction(100, lights);
void serialEvent() {

  //  status = "Serial Event:" + tempo;
  //    u8g.firstPage();
  //    do{
  //      draw();
  //    } while( u8g.nextPage() );


  byte msgSize;
  if (Serial.available()) {
    msgSize = Serial.peek();
    unsigned char msg[msgSize];
    Serial.readBytes(msg, msgSize);
    //sendBytes(msg,msgSize);
    process(msg, msgSize);
    Serial.flush();
    clock = 0;
  }

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

void loop() {
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


}

