unsigned char const TM_RETORNO = 0x0;
unsigned char const TM_INICIO  = 0x1;
unsigned char const TM_ESTAGIO = 0x2;

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

typedef union status {
    struct {
      unsigned int group: 5;
      unsigned int state : 2;
      unsigned int pedestrian : 1;
    };

    int intRepresentation;
} bitset;


void onReceiveEstage(unsigned char *msg, int msgSize);
void run(int * colors);
int * getTimes();