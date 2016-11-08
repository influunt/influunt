#include <stdio.h>
#include <string.h>
#include "device.h"

void print(int * times){
  printf("\n|Grupo|Pedestre|Ultimo Tempo|Atraso/Ver|Amarelo/Ver|Limpeza|VD/VE/AI|DE|\n");
  for(int linha = 0; linha < 16; linha++){
    bitset status;
    int * l = &times[linha];
    status.intRepresentation = l[0];
    printf("|%5d|%8d|%12d|%10d|%11d|%7d|%11d|\n",status.group,status.pedestrian,status.state,l[1],l[2],l[3],l[4]);
  }
}


void printColors(int * colors){
  for(int i = 0; i < 16; i++){
    printf("%3d|",i + 1);
  }
  printf("\n");
  for(int i = 0; i < 16; i++){
    printf("%3d|",colors[i]);
  }
  printf("\n");
}

int main(){
  unsigned char msg[51] = { 51,3,0,2,5,33,23,112,0,0,0,0,46,224,66,7,208,
                   11,184,11,184,39,16,67,31, 64,0,0,0,0,39,16,
                   196,0,0,19,136,11,184,39,16,37,31, 64,0,0,0,0,39,16,-96};

  onReceiveEstage(msg,51);
  // int count = 20000;
  // int colors[16];
  // do{
  //   printf("Tempo:%d\n",count);
  //   run(colors);
  //   print(getTimes());
  //   printColors(colors);
  //   count -= 100;
  //   if(count == 1000){
  //       onReceiveEstage(msg,51);
  //   }
  // }while(count > 0);
  //
  return 0;
}