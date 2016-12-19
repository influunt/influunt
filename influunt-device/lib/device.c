#include "device.h"

int times[16][5] = {0}; //Flag in the first byte

int whereIsTime(int line){
  if(times[line][1] >= 100){
    return 1;
  }else if(times[line][2] >= 100){
    return 2;
  }else if(times[line][3] >= 100){
    return 3;
  }
  
  return 4;
}

void dropTime(int line){
  times[line][whereIsTime(line)]-=100;
}

int getColorAndDecrement(line){
  int wit = whereIsTime(line);
  int ret = DESLIGADO;
  bitset status;
  status.intRepresentation = times[line][0];
  
  switch(wit){
    case 1: //Atraso de grupo ou vermelho
      ret = times[2] > 0 ? VERDE : VERMELHO;
      break;
    case 2:
      ret = status.pedestrian ? VERMELHO_INTERMITENTE : AMARELO;
      break;
    case 3:
      ret = VERMELHO_LIMPEZA;
      break;
    case 4:
      switch(status.state){
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

void run(int * colors){
  for(int i = 0; i < 16; i++){
    if(times[i][1] > 0 || times[i][2] > 0 || times[i][3] > 0 || times[i][4] > 0){
      colors[i] = getColorAndDecrement(i);
    }else{
      colors[i] = DESLIGADO;
    }
  }
}

void onReceiveEstage(unsigned char *msg, int msgSize){
  int groupsSize = msg[4] & 0x1F;
  int index = 5;
  
  for(int i = 0; i < groupsSize; i++){
    bitset status;
    status.intRepresentation = msg[index];
    int group = status.group;

    times[group - 1][0] = status.intRepresentation;
    times[group - 1][1] = (msg[index + 1] << 8) | msg[index + 2];
    times[group - 1][2] = (msg[index + 3] << 8) | msg[index + 4];
    times[group - 1][3] = (msg[index + 5] << 8) | msg[index + 6];
    times[group - 1][4] = (msg[index + 7] << 8) | msg[index + 8];
    index+=9;
  }
}


int * getTimes(){
  return times;
}



//
// byte byteRead;
//
//
//
// bitset bs;
// bs.intRepresentation = myInt;
//
// struct byte{
//    unsigned int state : 2;
//    unsigned int pedestrian : 1;
// };
//
// int times[16][5]; //Flag in the first byte
//
//
// void setup() {
//   // put your setup code here, to run once:
//   memset(times,0, 80 * sizeof(int));
//   Serial.begin(115200);
//
//
// }
// int calculateLRC(byte *msg,int size)
// {
//     byte LRC = 0;
//     for (int i = 0; i < size; i++)
//     {
//         LRC ^= msg[i];
//     }
//     return LRC;
// }
//
// void sendBytes(byte *msg,int size){
//     Serial.write(msg,size);
// }
//
// void onReceiveEstage(byte *msg,int msgSize){
//   int index = 5;
//   for(int x = ((int)msg & 0x1F); x > 0; x--){
//     int f1 = msg[index];
//     int group = f1 & 0x1F;
//     Status st;
//     st.pedestrian = f1 >> 7;
//     st.state = (f1 >> 5) & 0x4;
//
//     times[group][0] = (int) st;
//     times[group][1] = (msg[++index] << 8) | msg[++index];
//     times[group][2] = (msg[++index] << 8) | msg[++index];
//     times[group][3] = (msg[++index] << 8) | msg[++index];
//     times[group][4] = (msg[++index] << 8) | msg[++index];
//   }
// }
//
// void response(byte returnCode, byte seq1, byte seq2){
//
//       byte msg[6];
//       msg[0] = 0x6;
//       msg[1] = TM_RETORNO;
//       msg[2] = seq1;
//       msg[3] = seq2;
//       msg[4] = returnCode;
//       msg[5] = calculateLRC(msg,4);
//       sendBytes(msg,6);
// }
//
//
// byte checkMsgType(byte *msg,byte msgSize){
//   if(msgSize < 2){
//     return RET_INVALID_SIZE;
//   }
//   byte tm = msg[1];
//   switch(tm){
//     case TM_RETORNO:
//     case TM_INICIO:
//       return RET_OK;
//     case TM_ESTAGIO:
//
//     default:
//       return RET_INVALID_MSG_TYPE;
//   }
// }
//
// void process(byte *msg,byte msgSize){
//
//   if(calculateLRC(msg,msgSize - 1) == msg[msgSize - 1]){
//    response(checkMsgType(msg,msgSize),msg[2], msg[3]);
//   }else{
//    response(RET_INVALID_CHECKSUM, msg[2], msg[3]);
//   }
//
// }
//
// void loop() {
//
//  byte msgSize;
//  if (Serial.available()) {
//     /* read the most recent byte */
//     msgSize = Serial.peek();
//     byte msg[msgSize];
//     Serial.readBytes(msg,msgSize);
//     process(msg,msgSize);
//   }
// }
//
